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
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author chengyunbo@gyyx.cn
 * @since 2023-03-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "WhaleFall对象", description = "存储Whale实体对象信息")
public class WhaleFallDTO implements Serializable {

    /*
    * 这个字段用于指示实现 Serializable 接口类的版本号，可以在序列化和反序列化时用于判断类的版本是否相同
    * 以确保序列化和反序列化的正确性。
    * */
    @Serial
    private static final long serialVersionUID = 2L;

    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    @LogProperty("种类ID")
    private Integer id;


    @ApiModelProperty("姓名")
    @LogProperty("种类名")
    private String whaleName;

    @ApiModelProperty("密码")
    @LogProperty("种类密码")
    private String whalePwd;


    @ApiModelProperty("手机号")
    @LogProperty("种类手机号")
    private String whalePhone;

    @Email(message = "请输入正确的邮箱地址")
    @LogProperty("种类邮箱")
    private String whaleEmail;


    @ApiModelProperty("账户")
    @LogProperty("种类账户")
    private String whaleAccount;

    @ApiModelProperty("标志")
    @LogProperty("种类标志")
    @EnumValue(intValues = {0,1}, message = "这个标志只能是0或者1")
    private Integer flag;
}
