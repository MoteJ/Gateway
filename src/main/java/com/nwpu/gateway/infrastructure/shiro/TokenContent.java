package com.nwpu.gateway.infrastructure.shiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Mote
 * @Date: 2022/1/16 15:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenContent {
    private String uid;
    private String ccid;
    private String ip;
    private int loginState;
    private Long timeStamp;

}
