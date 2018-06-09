package com.lss.user.service;

import com.jianzhou.sdk.BusinessService;
import com.lss.user.config.SmsConfig;
import com.lss.user.util.AssertUtil;
import com.xiaoleilu.hutool.lang.Validator;
import com.xiaoleilu.hutool.util.RandomUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liushuaishuai
 * @date 2018/1/8 下午2:11
 */
@Service
@Slf4j
public class MessageService {

    private static final String JIANZHOU_ENDPOINT = "http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService";
    private static final BusinessService service = new BusinessService(JIANZHOU_ENDPOINT);
    private static final String SMS_TEMPLATE = "您的验证码为: {} 十分钟内有效.";

    @Autowired
    private RedisService redisService;

    @Autowired
    private SmsConfig smsConfig;


    /**
     * 发送短信
     *
     * @param phoneNumber
     */
    public Boolean send(String phoneNumber) {
        AssertUtil.isFalse(Validator.isMobile(phoneNumber), "手机号格式不正确");
        Boolean check = redisService.checkSendSMSTimes(phoneNumber);
        AssertUtil.isFalse(check, "发送次数超过限制");
        String code = RandomUtil.randomNumbers(4);
        int result = 0;
        if (smsConfig.sendEnable) {
            result = service.sendMessage(smsConfig.account, smsConfig.password, phoneNumber, getContent(code));
        }
        Boolean isSuccess = false;
        if (result > 0) {//发验证码成功,才放入 redis
            isSuccess = redisService.putCode(phoneNumber, code);
        }
        return isSuccess;

    }

    /**
     * 获取发送内容
     *
     * @return
     */
    private String getContent(String code) {

        String content = StrUtil.format(SMS_TEMPLATE, code);
        if (!content.endsWith(smsConfig.signature)) {
            content += smsConfig.signature;
        }
        return content;
    }
}
