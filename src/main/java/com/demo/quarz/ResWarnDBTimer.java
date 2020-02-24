package com.demo.quarz;

import com.demo.common.Statistics;
import com.demo.dao.QrtzJobDetailsMapper;
import com.demo.dao.log4j.ResLogMapper;
import com.demo.dao.log4j.ResWarnMapper;
import com.demo.dto.response.BaseRespDto;
import com.demo.init.InitConfig;
import com.demo.pojo.QrtzJobDetails;
import com.demo.pojo.log4j.ResWarn;
import com.demo.service.QrtzJobDetailsService;
import com.demo.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    //@Scheduled(cron = "* * 3 * * ?")
    //@Scheduled(cron = "0/10 * * * * ?")
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
        String sTime = DateUtil.getSubOrAddTime(-DATELIMIT);
        String eTime = DateUtil.getNowStr();
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
            String warnMsgStr = msg.trim().replace("%moudlename%", moduleName)
                    .replace("%level%",(warnDBCount.longValue() >= warnCount.longValue())?"警告":"错误")
                    .replace("%stime%", sTime)
                    .replace("%etime%", eTime)
                    .replace("%errorcount%", String.valueOf(errorDBCount))
                    .replace("%warncount%",String.valueOf(warnDBCount));
            ResWarn resWarn = new ResWarn();
            resWarn.setWarnModule(moduleName);
            resWarn.setWarnStatus(0);
            resWarn.setStartTime(DateUtil.parseDate(sTime));
            resWarn.setWarnMsg(warnMsgStr);
            resWarnDao.insert(resWarn);
            logger.info(LogBuilderUtil.getBuilder("ConfigScheduleWarnLog","定时查询告警信息","新增告警信息成功").build());
        }
    }


    /**
     * 根据时间间隔，定时任务
     * 项目启动时执行
     */
    //@PostConstruct
    public void checkResWarn2(){
        logger.info("开始调用第三方接口查询告警信息");
        Statistics statistics = InitConfig.getStatistics();
        String warnMsg = statistics.getWarnMsg();
        Statistics.Moudle moudle = statistics.getMoudle();
        List<Statistics.Item> items = moudle.getItems();
        //全部
        List<QrtzJobDetails> qrtzJobDetails1 = new ArrayList<>();
        //WARN相同
        Set<QrtzJobDetails> set1=new HashSet<QrtzJobDetails>();
        //ERROR相同
        Set<QrtzJobDetails> set2=new HashSet<QrtzJobDetails>();
        try {
            for (int i=0;i<items.size();i++){

                String className = items.get(i).getName();
                int warnTime = items.get(i).getWarnTime();
                int errorTime = items.get(i).getErrorTime();
                //任务名称
                String jobName = "resWarnDBTimer.scanScheduleWarnLog";
                String jobGroup = className+"-WARN";
               //删除已经失效的
               qrtzJobDetails1 = qrtzJobDetailsMapper.selectList(new QrtzJobDetails());
                //获取警告WARN定时任务集合中相同的数据
                for (int j=0;j<qrtzJobDetails1.size();j++){
                    if(jobName.equals(qrtzJobDetails1.get(j).getJobName()) && jobGroup.equals(qrtzJobDetails1.get(j).getJobGroup())){
                        set1.add(qrtzJobDetails1.get(j));
                    }
                }
               String jobGroup2 = className+"-ERROR";
               //获取ERROR定时任务集合中相同的数据
               for (int j=0;j<qrtzJobDetails1.size();j++){
                   if(jobName.equals(qrtzJobDetails1.get(j).getJobName()) && jobGroup2.equals(qrtzJobDetails1.get(j).getJobGroup())){
                       set2.add(qrtzJobDetails1.get(j));
                   }
               }
               //新增或修改定时任务
                Long warnCount = items.get(0).getWarnCount();
                ConfigScheduleWarnLog2(warnTime, warnCount,className,"WARN");
                Long errorCount = items.get(0).getErrorCount();
                ConfigScheduleWarnLog2(errorTime,errorCount, className,"ERROR");
            }
           //取出不需要的任务后删除
            for (QrtzJobDetails qrtzJobDetails : set1) {
                qrtzJobDetails1.remove(qrtzJobDetails);
            }
            for (QrtzJobDetails qrtzJobDetails : set2){
                qrtzJobDetails1.remove(qrtzJobDetails);
            }
            for (QrtzJobDetails qrtzJobDetails : qrtzJobDetails1) {
                JobKey jobKey = JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup());
                QuartzUtil.deleteJob(scheduler, jobKey);

            }
        }catch (Exception e){
            MDC.put("exception", e.getClass().getName());
            String trace = ExceptionUtil.getTrace(e);
            logger.error(trace,e);
        }



    }

    private void ConfigScheduleWarnLog2( int time,Long count, String className,String level) throws Exception{
        //查看该任务是否已经存在
        String jobName = "resWarnDBTimer.scanScheduleWarnLog";
        String jobGroup = className+"-"+level;
        String cron = "0/"+time+" * * * * ?";
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        List<? extends Trigger> list = scheduler.getTriggersOfJob(jobKey);
        if (list == null || list.size() != 1) {
            //添加定时任务
            BaseRespDto baseRespDto = new BaseRespDto();
            QrtzJobDetails qrtzJobDetails = new QrtzJobDetails();
            qrtzJobDetails.setDescription(className+"-"+level +" 告警！！");
            qrtzJobDetails.setJobName("resWarnDBTimer.scanScheduleWarnLog");
            qrtzJobDetails.setJobGroup(className+"-"+level+"-"+time+"-"+count);
            qrtzJobDetails.setCronExpression("0/"+time+" * * * * ?");
            qrtzJobDetailsService.createQrtzJobDetails(qrtzJobDetails,  baseRespDto);
        }else {
            //如果该定时任务已经存在，判断是否有修改
            QrtzJobDetails qrtzJobDetails = new QrtzJobDetails();
            qrtzJobDetails.setJobName("resWarnDBTimer.scanScheduleWarnLog");
            qrtzJobDetails.setJobGroup(className+"-"+level);
            List<QrtzJobDetails> qrtzJobDetails1 = qrtzJobDetailsMapper.selectList(qrtzJobDetails);
            QrtzJobDetails qrtzJobDetails2 = qrtzJobDetails1.get(0);
            if (!StringUtils.equals(cron,qrtzJobDetails2.getCronExpression())){
                //暂停
                Trigger trigger = list.get(0);
                scheduler.pauseTrigger(trigger.getKey());
                //重启
                TriggerKey triggerKey = trigger.getKey();
                Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow()
                        .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
                scheduler.rescheduleJob(newTrigger.getKey(), newTrigger);

            }
        }
    }
    public void scanScheduleWarnLog() throws Exception{
        //获取当前任务
        List<JobExecutionContext> jobContexts = scheduler.getCurrentlyExecutingJobs();
        JobExecutionContext jobExecutionContext = jobContexts.get(0);
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobKey key = jobDetail.getKey();
        String group = key.getGroup();
        String[] strs = group.split("-");

        Map<String, Object> paramMap = new HashMap<>();
        String sTime = DateUtil.getSubOrAddTime(-Integer.valueOf(strs[2]));
        String eTime = DateUtil.getNowStr();
        paramMap.put("sTime", sTime);
        paramMap.put("eTime", eTime);
        paramMap.put("logLevel", strs[1]);
        paramMap.put("logClass", strs[0]);
        Long count = resLogDao.selectAllCount(paramMap);
        if (count != null && count.longValue() > Long.valueOf(strs[3]).longValue()) {
            Statistics statistics = InitConfig.getStatistics();
            String msg = statistics.getWarnMsg();
            //添加报警
            String warnMsgStr = msg.trim().replace("%moudlename%", strs[0])
                    .replace("%stime%", sTime)
                    .replace("%etime%", eTime)
                    .replace("%countsmg%", String.valueOf(count));
            ResWarn resWarn = new ResWarn();
            resWarn.setWarnModule(strs[0]);
            resWarn.setWarnStatus(0);
            resWarn.setWarnMsg(warnMsgStr);
            resWarnDao.insert(resWarn);
            logger.info(LogBuilderUtil.getBuilder("scanScheduleWarnLog","定时查询告警信息","新增告警信息成功").build());
        }
    }
}
