package com.sea.whale.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sea.whale.operatelog.LogProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunbo
 * @since 2024-02-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "日志操作对象", description = "存储操作日志信息")
public class OperateLogDTO implements Serializable {

    private static final long serialVersionUID = 101L;

    @Schema(name = "主键id")
    @TableId(type = IdType.AUTO)
    @LogProperty("日志ID")
    private Integer id;

    /**
     * 日志来源
     */
    @Schema(name = "日志来源")
    @LogProperty("日志来源")
    private String source;
    /**
     * 操作名称
     */
    @Schema(name = "操作名称")
    @LogProperty("操作名称")
    private String operateName;
    /**
     * 操作信息
     */
    @Schema(name = "操作信息")
    @LogProperty("操作信息")
    private String operateInfo;
    /**
     * 操作人
     */
    @Schema(name = "操作人")
    @LogProperty("操作人")
    private String operator;
    /**
     * 操作状态
     */
    @Schema(name = "操作状态")
    @LogProperty("操作状态")
    private Boolean operateStatus;
    /**
     * 失败原因
     */
    @Schema(name = "失败原因")
    @LogProperty("失败原因")
    private String failCause;
    /**
     * 批处理Id
     */
    @Schema(name = "批量处理ID")
    @LogProperty("批量处理ID")
    private Integer batchId;

    /**
     * 是否为批处理(0：不是，1：是)
     */
    @Schema(name = "是否为批量处理(0:不是，1:是)")
    @LogProperty("是否为批量处理(0:不是，1:是)")
    private Byte batchFlag;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Schema(name = "操作时间")
    @LogProperty("操作时间")
    private Date operateTime;

}
