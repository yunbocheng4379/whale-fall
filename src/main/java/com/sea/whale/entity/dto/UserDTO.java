package com.sea.whale.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sea.whale.aop.EnumValue;
import com.sea.whale.operatelog.LogProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户对象", description = "存储用户对象信息")
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    @LogProperty("用户ID")
    private Integer id;

    @ApiModelProperty("用户名")
    @LogProperty("用户名")
    private String userName;

    @ApiModelProperty("用户密码")
    @LogProperty("用户密码")
    private String password;

    @ApiModelProperty("用户状态")
    @LogProperty("用户状态")
    @EnumValue(intValues = {0,1}, message = "这个标志只能是0或者1")
    private Integer flag;
}
