package com.sea.whale.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("operate_log")
public class OperateLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

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

}
