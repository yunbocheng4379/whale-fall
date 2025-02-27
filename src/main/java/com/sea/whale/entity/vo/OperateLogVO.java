package com.sea.whale.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperateLogVO implements Serializable {

    private static final long serialVersionUID = 201L;

    private Integer id;

    private String source;

    private String operateName;

    private String operateInfo;

    private String operator;

    private Boolean operateStatus;

    private String failCause;

    private Integer batchId;

    private Byte batchFlag;

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
