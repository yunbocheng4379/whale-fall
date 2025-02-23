package com.sea.whale.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Whale
 * @since 2022-08-24 21:20:00
 */
@TableName("whale_fall")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "WhaleFall对象", description = "存储Whale实体对象信息")
public class WhaleFallVO implements Serializable {


    /*
     * 这个字段用于指示实现 Serializable 接口类的版本号，可以在序列化和反序列化时用于判断类的版本是否相同
     * 以确保序列化和反序列化的正确性。
     * */
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;


    @ApiModelProperty("姓名")
    private String whaleName;

    @ApiModelProperty("标志")
    private Integer flag;

    @ApiModelProperty("密码")
    private String whalePwd;


    @ApiModelProperty("手机号")
    private String whalePhone;

    @ApiModelProperty("邮箱")
    private String whaleEmail;

    @ApiModelProperty("账户")
    private String whaleAccount;


}
