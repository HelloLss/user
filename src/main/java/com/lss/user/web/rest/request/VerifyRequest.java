package com.lss.user.web.rest.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liushuaishuai
 * @date 2018/1/12 下午6:02
 */
@Data
public class VerifyRequest {

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty(value = "验证码")
    private String code;

}
