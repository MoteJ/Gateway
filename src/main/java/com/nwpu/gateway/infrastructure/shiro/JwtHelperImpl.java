package com.nwpu.gateway.infrastructure.shiro;

import com.nwpu.gateway.helper.StringHelper;
import com.nwpu.gateway.infrastructure.repository.TokenRepository;
import com.nwpu.gateway.model.AuthorizedUserInfo;
import com.nwpu.gateway.model.JwtWebToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Mote
 * @Date: 2022/1/16 16:29
 */
@Component
@AllArgsConstructor
public class JwtHelperImpl implements JwtHelper {
    private final static String UID = "uid";
    private final static String CCID = "ccid";
    private final static String TOKEN_ID = "token";
    private final static String IP = "ip";
    private final static String LOGIN_STATE = "loginState";
    private final static String TIME_STAMP = "timeStamp";
    private JwtProperties jwtProperties;
    private TokenRepository tokenRepository;

    @Override
    public String create(AuthorizedUserInfo userInfo) {
        Claims claims = new DefaultClaims();
        claims.put(UID, userInfo.getUid());
        claims.put(CCID, userInfo.getCcid());
        claims.put(LOGIN_STATE, userInfo.getLoginState());
        claims.put(TOKEN_ID, StringHelper.getTokenID(userInfo.getUid()));
        // IP应该由helper之类的获得
        claims.put(IP, "127.0.0.1");
        claims.put(TIME_STAMP,System.currentTimeMillis());

        Calendar accessExpireDate = Calendar.getInstance();
        accessExpireDate.add(Calendar.SECOND, jwtProperties.getAccessExpireSeconds());
        Calendar refreshExpireDate = Calendar.getInstance();
        refreshExpireDate.add(Calendar.SECOND, jwtProperties.getRefreshExpireSeconds());

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(refreshExpireDate.getTime())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getRefreshSecret().getBytes())
                .compact();
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(refreshExpireDate.getTime())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret().getBytes())
                .compact();
        JwtWebToken jwtWebToken = JwtWebToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
        tokenRepository.save(userInfo.getCcid(), jwtWebToken, jwtProperties.getRefreshExpireSeconds());
        return accessToken;
    }

    @Override
    public AuthorizedUserInfo verify(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();
        return AuthorizedUserInfo.builder()
                .ccid(claims.get(CCID, String.class))
                .uid(claims.get(UID, String.class))
                .loginState(claims.get(LOGIN_STATE, Integer.class))
                .build();
    }

    @Override
    public String refresh(TokenContent claims) {
        String ccid = claims.getCcid();
        String refreshToken = tokenRepository.getRefreshToken(ccid);
        if (refreshToken == null) {
            throw new NullPointerException("refreshToken Null");
        }
        Claims refreshClaims = Jwts.parser()
                .setSigningKey(jwtProperties.getRefreshSecret().getBytes())
                .parseClaimsJws(refreshToken)
                .getBody();
        String jwtIp = claims.getIp();
        String nowIp = "127.0.0.1";
        if (!nowIp.equals(jwtIp)) {
            throw new RuntimeException("ip not match");
        }
        if (refreshClaims.get(TIME_STAMP, Long.class).equals(claims.getTimeStamp())) {
            return create(new AuthorizedUserInfo());
        }
        if (System.currentTimeMillis() - refreshClaims.getIssuedAt().getTime() < 10 * 1000) {
            return tokenRepository.getAccessToken(ccid);
        }
        return null;
    }

    @Override
    public TokenContent getTokenContent(Claims claims) {
        return TokenContent.builder()
                .ccid(claims.get(CCID, String.class))
                .ip(claims.get(IP, String.class))
                .timeStamp(claims.get(TIME_STAMP, Long.class))
                .build();
    }
}
