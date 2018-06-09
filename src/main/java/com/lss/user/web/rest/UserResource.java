package com.lss.user.web.rest;

import com.lss.user.exception.UserException;
import com.lss.user.service.MessageService;
import com.lss.user.service.UserService;
import com.lss.user.web.rest.request.VerifyRequest;
import com.lss.user.web.rest.response.Token;
import com.lss.user.web.rest.request.RegisterRequest;
import com.xiaoleilu.hutool.lang.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author liushuaishuai
 * @date 2018/1/4 下午2:00
 */
@RestController
@RequestMapping("user")
@Api(description = "user服务")
public class UserResource {


    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册和登陆 @lss", response = Token.class)
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (Validator.isMobile(registerRequest.getPhoneNumber())) {
            throw new UserException("手机格式不正确");
        }
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @GetMapping("/send")
    @ApiOperation(value = "发送验证码 @lss", response = Boolean.class)
    public ResponseEntity send(@Valid @RequestParam String phoneNumber) {
        if (Validator.isMobile(phoneNumber)) {
            throw new UserException("手机格式不正确");
        }
        return ResponseEntity.ok(messageService.send(phoneNumber));
    }

    @PostMapping("/check")
    @ApiOperation(value = "校验验证码 @lss", response = Boolean.class)
    public ResponseEntity verify(@Valid @RequestBody VerifyRequest verifyRequest) {
        if (Validator.isMobile(verifyRequest.getPhoneNumber())) {
            throw new UserException("手机格式不正确");
        }
        return ResponseEntity.ok(userService.verify(verifyRequest.getPhoneNumber(), verifyRequest.getCode()));
    }


}
