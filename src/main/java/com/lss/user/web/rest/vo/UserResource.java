package com.lss.user.web.rest.vo;

import com.lss.user.exception.UserException;
import com.lss.user.service.UserService;
import com.lss.user.web.rest.vo.request.RegisterRequest;
import com.lss.user.web.rest.vo.response.Token;
import com.xiaoleilu.hutool.lang.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/register")
    @ApiOperation(value = "用户注册和登陆 @lss", response = Token.class)
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        if(Validator.isMobile(registerRequest.getPhoneNumber())){
            throw new UserException("手机格式不正确");
        }
        return ResponseEntity.ok(userService.register(registerRequest));
    }


}
