package com.lss.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author liushuaishuai
 * @date 2018/1/12 上午11:16
 */
@Component
@Data
public class SmsConfig {

    @Value("${sms.account}")
    public String account;
    @Value("${sms.password}")
    public String password;
    @Value("${sms.signature}")
    public String signature;
    @Value("${sms.send}")
    public boolean sendEnable;

}
