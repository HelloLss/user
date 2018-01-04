package com.lss.user.web.rest.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liushuaishuai
 * @date 2018/1/4 上午11:52
 */
@Data
public class RegisterRequest {

    // 可通过  -> 账号密码登陆(账号也是手机号)
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码")
    private String password;

    // 也可通过 -> 手机号和验证码登陆
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    @ApiModelProperty(value = "验证码")
    private String code;



}
