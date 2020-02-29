package com.demo.quarz;

import com.demo.util.SpringContextHolder;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
public class Log4jDynamicQuartzJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        JobDataMap jobDM = arg0.getJobDetail().getJobDataMap();
        JobDataMap triDM = arg0.getTrigger().getJobDataMap();
        //传递四个参数 module errCount warnCount errTime warnTime
        String module = jobDM.getString("module");
        Long errCount =jobDM.getLong("errCount");
        Long warnCount =jobDM.getLong("errCount");
        int errTime = jobDM.getInt("errTime");
        int warnTime = jobDM.getInt("warnTime");
        int isWarnOrError = jobDM.getInt("isWarnOrError");
        ResWarnDBTimer resWarnDBTimer = (ResWarnDBTimer)SpringContextHolder.getBean("resWarnDBTimer");
        resWarnDBTimer.ConfigScheduleWarnLog(module,errCount,warnCount,warnTime,errTime,isWarnOrError);
    }
}
