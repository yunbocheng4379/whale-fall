package com.sea.whale.operatelog;

import org.springframework.stereotype.Component;

/**
 * @author chengyunbo
 * @since 2024-02-23
 */
@Component
public class DefaultLogInfoHandler implements LogInfoHandler{

    @Override
    public void handler(OperateLogPojo operateLogPojo) {

    }

    @Override
    public void failHandler(OperateLogPojo operateLogPojo, String failCause) {

    }
}
