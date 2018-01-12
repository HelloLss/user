package com.lss.user.service;

import com.lss.user.util.RedisUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liushuaishuai
 * @date 2018/1/12 上午10:48
 */
@Service
public class RedisService {

    public static final int MAX_COUNT = 10; // 当日最多发送次数为10次
    private static final long CODE_EXPIRE = 600;//验证码十分钟内有效
    private static final long SEND_SPLIT = 60;//验证码十分钟内有效


    public static final String VERIFICATION_COUNT = "verification_count :";

    public static final String VERIFICATION_CODE = "verification_code :";

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 检验发送次数
     *
     * @param phoneNumber
     * @return
     */
    public boolean checkSendSMSTimes(String phoneNumber) {
        if (StrUtil.isEmpty(phoneNumber)) {
            return false;
        }
        int count;
        String key = VERIFICATION_COUNT + phoneNumber;
        if (!redisUtil.exists(key)) {
            count = 0;
        } else {
            count = (int) redisUtil.get(key);
        }
        return count < MAX_COUNT;
    }


    /**
     * 把验证码放到 redis 中
     *
     * @param phoneNumber
     * @param code
     * @return 放入是否成功
     */
    public boolean putCode(String phoneNumber, String code) {
        String key = VERIFICATION_CODE + code;
        increaseCount(phoneNumber);
        return redisUtil.put(key, code, CODE_EXPIRE);
    }

    /**
     * 自增发送次数
     * 次日零点重置发送次数
     *
     * @param phoneNumber
     */
    private void increaseCount(String phoneNumber) {
        String key = VERIFICATION_COUNT + phoneNumber;
        int count = 0;
        if (redisUtil.exists(key)) {
            count = (int) redisUtil.get(key);
        }
        count++;
        redisUtil.put(key, count, new DateTime().plusDays(1).withMillisOfDay(0).toDate());
    }



}
