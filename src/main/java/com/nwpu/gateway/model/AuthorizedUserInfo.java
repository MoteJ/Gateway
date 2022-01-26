package com.nwpu.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

/**
 * @Author: Mote
 * @Date: 2022/1/16 13:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizedUserInfo {
    private String uid;
    private String ccid;
    private int loginState;
}
