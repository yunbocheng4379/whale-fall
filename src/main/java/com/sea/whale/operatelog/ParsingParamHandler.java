package com.sea.whale.operatelog;

import com.sea.whale.entity.vo.OperateLogVO;
import com.sea.whale.mapper.OperateLogMapper;
import com.sea.whale.utils.LogUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chengyunbo@gyyx.cn
 * @since 2024-02-23
 */
@Component
public class ParsingParamHandler implements LogInfoHandler{

    @Resource
    private OperateLogMapper operateLogMapper;

    @Override
    public void handler(OperateLogPojo operateLogPojo) {
        OperateLog opLog = operateLogPojo.getOpLog();
        String logInfo="";
        if(operateLogPojo.getParams()!=null && operateLogPojo.getParams().length!=0){
            logInfo = LogUtil.describeObject(operateLogPojo.getParams()[0]);
        }
        OperateLogVO operaLogVo = new OperateLogVO(opLog.logResource(), opLog.operateName(), logInfo, operateLogPojo.getUsername(), true, Byte.parseByte("0"));
        operateLogMapper.insert(operaLogVo);
    }

    @Override
    public void failHandler(OperateLogPojo operateLogPojo, String failCause) {
        OperateLog opLog = operateLogPojo.getOpLog();
        String logInfo="";
        if(operateLogPojo.getParams()!=null && operateLogPojo.getParams().length!=0){
            logInfo = LogUtil.describeObject(operateLogPojo.getParams()[0]);
        }
        OperateLogVO operateLogVO = new OperateLogVO(opLog.logResource(), opLog.operateName(), logInfo, operateLogPojo.getUsername(), false, failCause, Byte.parseByte("0"));
        operateLogMapper.insert(operateLogVO);
    }
}
