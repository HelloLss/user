package com.lss.user.service;

import com.lss.user.web.rest.request.RegisterRequest;
import com.lss.user.web.rest.response.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liushuaishuai
 * @date 2018/1/4 下午2:00
 */
@Service
@Slf4j
public class UserService {


    /**
     * 注册登陆
     *
     * 以用户手机号作为唯一区分，即一个手机号只能注册一个用户
     *
     * @param registerRequest
     * @return
     */
    public Token register(RegisterRequest registerRequest) {


        return null;
    }


}
