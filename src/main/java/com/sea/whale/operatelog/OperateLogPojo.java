package com.sea.whale.operatelog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chengyunbo
 * @since 2024-02-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperateLogPojo {

    private OperateLog opLog;

    private String username;

    private Object[] params;

    public OperateLogPojo(OperateLog opLog, String username, Object[] args, String requestParams) {
    }
}
