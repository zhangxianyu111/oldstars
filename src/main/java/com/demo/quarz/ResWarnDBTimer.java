package com.demo.quarz;

import com.demo.common.Statistics;
import com.demo.dao.log4j.ResLogMapper;
import com.demo.dao.log4j.ResWarnMapper;
import com.demo.init.InitConfig;
import com.demo.pojo.log4j.ResWarn;
import com.demo.util.DateUtil;
import com.demo.util.LogBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(value = "resWarnDBTimer")
public class ResWarnDBTimer {

    private static final Logger logger = LoggerFactory.getLogger(LoggerDBTimer.class);

    private static int DATELIMIT = 24*60*60;
    //创建调度器Schedule
   // private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);
    @Resource
    private ResWarnMapper resWarnDao;
    @Resource
    private ResLogMapper resLogDao;
    //@Scheduled(cron = "* * 3 * * ?")
    //@Scheduled(cron = "0/10 * * * * ?")
    public void checkResWarn(){
        logger.info("开始调用第三方接口查询告警信息");
        Statistics statistics = InitConfig.getStatistics();
        String warnMsg = statistics.getWarnMsg();
        String errorMsg = statistics.getErrorMsg();
        Statistics.Moudle moudle = statistics.getMoudle();
        List<Statistics.Item> items = moudle.getItems();
        for (Statistics.Item item : items) {
            //int warnTime = item.getWarnTime();
            Long warnCount = item.getWarnCount();
            String className = item.getName();
            ConfigScheduleWarnLog(warnMsg, warnCount, className,"WARN");
            //int errorTime = item.getErrorTime();
            Long errorCount = item.getErrorCount();
            ConfigScheduleWarnLog(errorMsg, errorCount, className,"ERROR");
        }
        logger.info("调用第三方接口查询告警信息结束");

    }

    private void ConfigScheduleWarnLog(String msg, Long warnCount, String className,String level) {

        Map<String, Object> paramMap = new HashMap<>();
        //String sTime = DateUtil.getSubOrAddTime(-warnTime);
        //String eTime = DateUtil.getSubOrAddTime(warnTime);
        String sTime = DateUtil.getSubOrAddTime(-DATELIMIT);
        String eTime = DateUtil.getSubOrAddTime(DATELIMIT);
        paramMap.put("sTime", sTime);
        paramMap.put("eTime", eTime);
        paramMap.put("logLevel", level);
        paramMap.put("logClass", className);
        Long count = resLogDao.selectAllCount(paramMap);
        if (count != null && count.longValue() > warnCount.longValue()) {
            //添加报警
            String warnMsgStr = msg.trim().replace("%moudlename%", className)
                    .replace("%stime%", sTime)
                    .replace("%etime%", eTime)
                    .replace("%countsmg%", String.valueOf(count));
            ResWarn resWarn = new ResWarn();
            resWarn.setWarnClass(className);
            resWarn.setWarnStatus(0);
            resWarn.setWarnMsg(warnMsgStr);
            resWarnDao.insert(resWarn);
            logger.info(LogBuilderUtil.getBuilder("ConfigScheduleWarnLog","定时查询告警信息","新增告警信息成功").build());
        }
    }
}
