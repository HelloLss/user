package com.lss.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author liushuaishuai
 * @date 2018/1/8 下午2:17
 */
@Component
@Data
public class JwtConfig {


    @Value("${jwt.clientId}")
    private String clientId;
    @Value("${jwt.base64Secret}")
    private String base64Secret;
    @Value("${jwt.name}")
    private String name;
    @Value("${jwt.expiresMillis}")
    private Long expiresMillis;

}
