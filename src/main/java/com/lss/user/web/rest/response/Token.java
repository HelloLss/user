package com.lss.user.web.rest.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liushuaishuai
 * @date 2018/1/4 上午11:58
 */
@Data
public class Token {

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    @ApiModelProperty(value = "头像（如果有的话是一个url地址）")
    private String photo;

    @ApiModelProperty(value = "token令牌")
    private String JwtToken;

}
