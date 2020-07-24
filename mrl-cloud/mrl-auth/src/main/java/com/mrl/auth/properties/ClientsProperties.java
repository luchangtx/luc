package com.mrl.auth.properties;

import lombok.Data;

/**
 * @author luc
 * @date 2020/7/2019:10
 */
@Data
public class ClientsProperties {
    private String client;
    private String secret;
    private String grantType="password,authorization_code,refresh_token";
    private String scope="all";
}
