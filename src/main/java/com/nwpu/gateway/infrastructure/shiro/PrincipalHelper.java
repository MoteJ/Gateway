package com.nwpu.gateway.infrastructure.shiro;

import com.nwpu.gateway.model.AuthorizedUserInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: Mote
 * @Date: 2022/1/16 15:30
 */
@Component
public class PrincipalHelper {
    public AuthorizedUserInfo getUserInfo() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return (AuthorizedUserInfo) principal;
    }

    public String userId() {
        return getUserInfo().getUid();
    }
}
