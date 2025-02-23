package com.sea.whale.operatelog;

public interface LogInfoHandler {

    void handler(OperateLogPojo operateLogPojo);

    void failHandler(OperateLogPojo operateLogPojo,String failCause);

}
