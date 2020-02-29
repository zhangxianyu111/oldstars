package com.demo.quarz;

import com.demo.common.Statistics;
import com.demo.common.constant.ErrorOrWarnConstant;
import com.demo.dao.QrtzJobDetailsMapper;
import com.demo.dao.log4j.ResLogMapper;
import com.demo.dao.log4j.ResWarnMapper;
import com.demo.dto.response.BaseRespDto;
import com.demo.init.InitConfig;
import com.demo.pojo.QrtzJobDetails;
import com.demo.pojo.log4j.ResWarn;
import com.demo.service.QrtzJobDetailsService;
import com.demo.service.log4j.ResLogService;
import com.demo.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component(value = "resWarnDBTimer")
public class ResWarnDBTimer {

    private static final Logger logger = LoggerFactory.getLogger(LoggerDBTimer.class);

    private static int DATELIMIT = 24*60*60;
    @Resource
    private ResWarnMapper resWarnDao;
    @Resource
    private ResLogMapper resLogDao;

    @Resource
    private QrtzJobDetailsService qrtzJobDetailsService;
    @Resource
    QrtzJobDetailsMapper qrtzJobDetailsMapper;

    @Resource
    private Scheduler scheduler;

    /**
     * 每天定时任务
     */
    public void checkResWarn() throws Exception{
        logger.info("开始调用第三方接口查询告警信息");
        Statistics statistics = InitConfig.getStatistics();
        String warnMsg = statistics.getWarnMsg();
        Statistics.Moudle moudle = statistics.getMoudle();
        List<Statistics.Item> items = moudle.getItems();
        for (Statistics.Item item : items) {
            Long warnCount = item.getWarnCount();
            String moduleName = item.getName();
            Long errorCount = item.getErrorCount();
            ConfigScheduleWarnLog(warnMsg, warnCount,errorCount, moduleName);
        }
        logger.info("调用第三方接口查询告警信息结束");

    }

    public void ScheduledDelete() throws Exception{
        logger.info("开始调用第三方接口删除数据库过期日志信息任务");
        resLogDao.deleteExpire();
        logger.info("开始调用第三方接口删除数据库过期日志信息任务");
    }



    public void ConfigScheduleWarnLog(String msg, Long warnCount,Long errorCount, String moduleName){
        Map<String, Object> paramMap = new HashMap<>();
        Date now = new Date();
        String sTime = DateUtil.getSubOrAddTime(now,-DATELIMIT);
        String eTime = DateUtil.getNowStr(now);
        paramMap.put("sTime", sTime);
        paramMap.put("eTime", eTime);
        paramMap.put("logLevel", "WARN");
        paramMap.put("moduleName", moduleName);
        Long warnDBCount = resLogDao.selectAllCount(paramMap);

        paramMap.put("logLevel", "ERROR");
        Long errorDBCount = resLogDao.selectAllCount(paramMap);
        if (warnDBCount != null && errorDBCount != null && (warnDBCount.longValue() >= warnCount.longValue()
                || errorDBCount.longValue()>= errorCount.longValue() )) {
            //添加报警 %moudlename%模块%level%日志过多，在%stime%-%etime%内,(错误日志%errorcount%次，警告日志%warncount%次)
            insertWarnLog(moduleName, warnCount, msg, sTime, eTime, warnDBCount, errorDBCount);
        }
    }


    /**
     * 根据warn.xml定时任务
     * @zje
     */
    @PostConstruct
    public void createTimingTaskByXml(){
        logger.info("项目初始化开始");
        //删除数据库所有的定时任务重新生成
        try {
            Statistics statistics = InitConfig.getStatistics();
            String warnMsg = statistics.getWarnMsg();
            Statistics.Moudle moudle = statistics.getMoudle();
            List<Statistics.Item> items = moudle.getItems();
            logger.info("定时任务重新创建开始");
            for (Statistics.Item item : items) {
                qrtzJobDetailsService.createDynamicQrtzJobsByWebStart(item);
            }
            logger.info("定时任务重新创建完成");
            logger.info("项目初始化完成");
        }catch (SchedulerException e){
            logger.error("定时任务创建失败",e);
        }catch (Exception e){
            logger.error("项目初始化失败",e);
        }

    }
    public void ConfigScheduleWarnLog(String moduleName, Long warnCount,Long errorCount, int errTime,int warnTime,int isWarnOrError) {
        Statistics statistics = InitConfig.getStatistics();
        String msg = statistics.getWarnMsg();
        Map<String, Object> paramMap = new HashMap<>();
        Date now = new Date();
        String sTime = DateUtil.getSubOrAddTime(now, -(isWarnOrError == ErrorOrWarnConstant.ERROR?errTime:warnTime));
        String eTime = DateUtil.getNowStr(now);
        paramMap.put("sTime", sTime);
        paramMap.put("eTime", eTime);
        paramMap.put("logLevel", "WARN");
        paramMap.put("moduleName", moduleName);
        ResLogMapper resLogMapper = (ResLogMapper)SpringContextHolder.getBean("resLogMapper");
        Long warnDBCount = resLogMapper.selectAllCount(paramMap);

        paramMap.put("logLevel", "ERROR");
        Long errorDBCount = resLogMapper.selectAllCount(paramMap);
        if (isWarnOrError == ErrorOrWarnConstant.WARN){
            if (warnDBCount != null  && warnDBCount.longValue() >= warnCount.longValue() ) {
                insertWarnLog(moduleName, warnCount, msg, sTime, eTime, warnDBCount, errorDBCount);
            }
        }else if (isWarnOrError == ErrorOrWarnConstant.ERROR){
            if (errorDBCount != null  && errorDBCount.longValue() >= errorCount.longValue() ) {
                insertWarnLog(moduleName, warnCount, msg, sTime, eTime, warnDBCount, errorDBCount);
            }
        }
    }

    private void insertWarnLog(String moduleName, Long warnCount, String msg, String sTime, String eTime, Long warnDBCount, Long errorDBCount) {
        //添加报警 %moudlename%模块%level%日志过多，在%stime%-%etime%内,(错误日志%errorcount%次，警告日志%warncount%次)
        String warnMsgStr = msg.trim().replace("%moudlename%", moduleName)
                .replace("%level%", (warnDBCount.longValue() >= warnCount.longValue()) ? "警告" : "错误")
                .replace("%stime%", sTime)
                .replace("%etime%", eTime)
                .replace("%errorcount%", String.valueOf(errorDBCount))
                .replace("%warncount%", String.valueOf(warnDBCount));
        ResWarn resWarn = new ResWarn();
        resWarn.setWarnModule(moduleName);
        resWarn.setWarnStatus(0);
        resWarn.setStartTime(DateUtil.parseDate(sTime));
        resWarn.setWarnMsg(warnMsgStr);
        resWarn.setWarnTime(DateUtil.parseDate(eTime));
        ResWarnMapper resWarnDao = (ResWarnMapper)SpringContextHolder.getBean("ResWarnMapper");
        resWarnDao.insert(resWarn);
        logger.info(LogBuilderUtil.getBuilder("ConfigScheduleWarnLog", "定时查询告警信息", "新增告警信息成功").build());
    }
}
