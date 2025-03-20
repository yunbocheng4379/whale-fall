package com.sea.whale.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sea.whale.aop.EnumValue;
import com.sea.whale.operatelog.LogProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "用户对象", description = "存储用户对象信息")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 100L;

    @Schema(name = "主键id")
    @TableId(type = IdType.AUTO)
    @LogProperty("用户ID")
    private Integer id;

    @Schema(name = "用户名")
    @LogProperty("用户名")
    private String userName;

    @Schema(name = "用户密码")
    @LogProperty("用户密码")
    private String password;

    @Schema(name = "用户邮箱")
    @LogProperty("用户邮箱")
    private String email;

    @Schema(name = "用户状态")
    @LogProperty("用户状态")
    @EnumValue(intValues = {0,1}, message = "这个标志只能是0或者1")
    private Integer flag;

    @Schema(name = "用户角色")
    @LogProperty("用户角色")
    private Integer userRole;


    @Schema(name = "创建时间")
    @LogProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @Schema(name = "更新时间")
    @LogProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}
