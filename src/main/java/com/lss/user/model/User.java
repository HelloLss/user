package com.lss.user.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entity User. 
 *
 * @author duiker(generated)
 */
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ApiModelProperty(value = "手机号")
    @Column(name = "phone_number")
    private String phoneNumber;

    @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name")
    private String nickName;

    @ApiModelProperty(value = "密码（MD5加密字符串）")
    @Column(name = "password")
    private String password;

    @ApiModelProperty(value = "头像")
    @Column(name = "photo")
    private String photo;

    @ApiModelProperty(value = "备注")
    @Column(name = "remark")
    private String remark;

    @NotNull
    @ApiParam(required = true)
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time")
    private Date updateTime;

}
