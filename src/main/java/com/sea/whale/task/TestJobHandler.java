package com.sea.whale.task;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chengyunbo
 * @since 2023-04-24
 */
@Slf4j
@Component
public class TestJobHandler {

    @XxlJob("testXxlJob")
    public void productJobHandler() {
        XxlJobHelper.handleSuccess("执行结果：----> 任务执行成功"  );
    }

}
