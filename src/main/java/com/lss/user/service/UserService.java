package com.lss.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lss.user.mapper.UserMapper;
import com.lss.user.model.User;
import com.lss.user.util.AssertUtil;
import com.lss.user.util.JwtUtil;
import com.lss.user.web.rest.request.RegisterRequest;
import com.lss.user.web.rest.response.Token;
import com.xiaoleilu.hutool.bean.BeanUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author liushuaishuai
 * @date 2018/1/4 下午2:00
 */
@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {


    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 注册登陆
     * <p>
     * 以用户手机号作为唯一区分，即一个手机号只能注册一个用户
     *
     * @param registerRequest
     * @return
     */
    public Token register(RegisterRequest registerRequest) {
        AssertUtil.isFalse(verify(registerRequest.getPhoneNumber(), registerRequest.getCode()), "验证码不正确");
        User user = getUserByPhone(registerRequest.getPhoneNumber());
        Optional<Token> token = null;
        if (user == null) {
            user = new User();
            BeanUtil.copyProperties(registerRequest, user);
            token = insertUser(user);
            return token.get();
        } else {
            return createToken(user).get();
        }
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    private Optional<Token> insertUser(User user) {
        if (user == null) {
            return null;
        }
        Integer insert = baseMapper.insert(user);
        if (insert > 0) {
            return createToken(user);
        }
        return null;
    }

    /**
     * 创建token
     *
     * @param user
     * @return
     */
    private Optional<Token> createToken(User user) {
        if (user == null || user.getId() == null) {
            return null;
        }
        String jwtToken = jwtUtil.createToken(user);
        Token token = new Token();
        BeanUtil.copyProperties(user, token);
        token.setJwtToken(jwtToken);
        redisService.putUserToken(user.getId(), jwtToken, jwtUtil.jwtConfig.getExpiresMillis());
        return Optional.of(token);
    }


    /**
     * 校验验证码
     *
     * @param phoneNumber
     * @param code
     * @return
     */
    public Boolean verify(String phoneNumber, String code) {
        boolean success = redisService.verifyCode(phoneNumber, code);
        if (success) {
            redisService.removeCode(phoneNumber);
        }
        return success;
    }


    /**
     * 根据手机号查询用户
     *
     * @param phoneNumber
     * @return
     */
    private User getUserByPhone(String phoneNumber) {
        Wrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("phoneNumber", phoneNumber);
        List<User> userList = selectList(wrapper);
        if (CollectionUtil.isNotEmpty(userList)) {
            return userList.get(0);
        } else {
            return null;
        }
    }
}
