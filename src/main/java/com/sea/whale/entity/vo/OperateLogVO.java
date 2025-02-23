package com.sea.whale.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunbo@gyyx.cn
 * @since 2024-02-23
 */
@TableName("operate_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "日志操作对象", description = "存储操作日志信息")
public class OperateLogVO implements Serializable {

    /*
     * 这个字段用于指示实现 Serializable 接口类的版本号，可以在序列化和反序列化时用于判断类的版本是否相同
     * 以确保序列化和反序列化的正确性。
     * */
    @Serial
    private static final long serialVersionUID = 2L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    /**
     * 日志来源
     */
    private String source;
    /**
     * 操作名称
     */
    private String operateName;
    /**
     * 操作信息
     */
    private String operateInfo;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作状态
     */
    private Boolean operateStatus;
    /**
     * 失败原因
     */
    private String failCause;
    /**
     * 批处理Id
     */
    private Integer batchId;

    /**
     * 是否为批处理(0：不是，1：是)
     */
    private Byte batchFlag;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operateTime;

    public OperateLogVO(String source, String operateName, String operateInfo, String operator, Boolean operateStatus, Byte batchFlag) {
        this.source = source;
        this.operateName = operateName;
        this.operateInfo = operateInfo;
        this.operator = operator;
        this.operateStatus = operateStatus;
        this.batchFlag = batchFlag;
        this.operateTime = new Date();
    }

    public OperateLogVO(String source, String operateName, String operateInfo, String operator, Boolean operateStatus, String failCause, Byte batchFlag) {
        this.source = source;
        this.operateName = operateName;
        this.operateInfo = operateInfo;
        this.operator = operator;
        this.operateStatus = operateStatus;
        this.failCause = failCause;
        this.batchFlag = batchFlag;
        this.operateTime = new Date();
    }

}
