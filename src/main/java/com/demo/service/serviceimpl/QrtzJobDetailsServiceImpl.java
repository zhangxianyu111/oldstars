package com.demo.service.serviceimpl;

import com.demo.common.constant.StatusConstant;
import com.demo.dao.QrtzJobDetailsMapper;
import com.demo.dto.response.BaseRespDto;
import com.demo.exception.DynamicQuartzException;
import com.demo.pojo.QrtzJobDetails;
import com.demo.quarz.DynamicQuartzJob;
import com.demo.service.QrtzJobDetailsService;
import com.demo.util.QuartzUtil;
import com.demo.util.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zje
 *
 */
@Service("qrtzJobDetailsService")
@Transactional(rollbackFor=Exception.class)
public class QrtzJobDetailsServiceImpl implements QrtzJobDetailsService {
	
	/** jobName 前缀*/
	private static final String JOB_NAME_PREFIX = "jobName.";
	/** triggerName 前缀*/
	private static final String TRIGGER_NAME_PREFIX = "triggerName.";
	/** jobName/triggerName 默认组 */
	private static final String GROUP_DEFAULT = "DEFAULT";
	
	@Resource
	private QrtzJobDetailsMapper qrtzJobDetailsMapper;
	@Resource
	private Scheduler scheduler; 
	
	@Override
	public BaseRespDto createQrtzJobDetails(QrtzJobDetails qrtzJobDetails,BaseRespDto baseRespDto) throws Exception{
		
		// 非空校验
		if (qrtzJobDetails == null) {
			throw new Exception("qrtzJobDetails 为空");
		}
		if (StringUtils.isBlank(qrtzJobDetails.getJobName())) {
			throw new Exception("qrtzJobDetails serviceInfo 为空");
		}

		// 定时服务有效性校验 (校验是否存在对应的servcie.method )
		this.checkServiceAndMethod(qrtzJobDetails.getJobName());

		// 唯一性校验
		String jobName = JOB_NAME_PREFIX + qrtzJobDetails.getJobName();
		String triggerName = TRIGGER_NAME_PREFIX + qrtzJobDetails.getJobName();
		String jobGroup = StringUtils.isBlank(qrtzJobDetails.getJobGroup())? GROUP_DEFAULT : qrtzJobDetails.getJobGroup();
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		if (scheduler.checkExists(jobKey)) {
			throw new DynamicQuartzException(qrtzJobDetails.getJobName() + "服务方法对应定时任务已经存在!");
		}

		// 构建job信息
		JobDetail job = JobBuilder.newJob(DynamicQuartzJob.class).withIdentity(jobKey).withDescription(qrtzJobDetails.getDescription()).build();
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, jobGroup);

        // 构建job的触发规则 cronExpression
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow()  
        		.withSchedule(CronScheduleBuilder.cronSchedule(qrtzJobDetails.getCronExpression())).build();

		// 注册job和trigger信息
        scheduler.scheduleJob(job, trigger);
		baseRespDto.setCode(StatusConstant.SUCCESS);
		baseRespDto.setMsg("创建定时成功!");
		return baseRespDto;
	}

	
	@Override
	public Map<String, Object> updateQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws SchedulerException{
		Map<String, Object> resultMap = new HashMap<>();

		JobKey jobKey = JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup());
		TriggerKey triggerKey = null;
		List<? extends Trigger> list = scheduler.getTriggersOfJob(jobKey);
		if (list == null || list.size() != 1) {
			resultMap.put("success",false);
			resultMap.put("msg","未查到该任务");
			return resultMap;
		}
		for (Trigger trigger : list) {
			JobKey jobKey1 = trigger.getJobKey();
			TriggerKey key = trigger.getKey();
			String description = trigger.getDescription();
			JobDataMap jobDataMap = trigger.getJobDataMap();
			//暂停触发器
			scheduler.pauseTrigger(trigger.getKey());
			triggerKey = trigger.getKey();
		}
		Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow()
              .withSchedule(CronScheduleBuilder.cronSchedule(qrtzJobDetails.getCronExpression())).build();
		scheduler.rescheduleJob(newTrigger.getKey(), newTrigger);
		resultMap.put("success",true);
		resultMap.put("msg","更新成功！");
		return resultMap;
	}


	@Override
	public BaseRespDto deleteQrtzJobDetails(QrtzJobDetails qrtzJobDetails,BaseRespDto baseRespDto) throws Exception {
		JobKey jobKey = JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup());
		QuartzUtil.deleteJob(scheduler, jobKey);
		baseRespDto.setCode(StatusConstant.SUCCESS);
		baseRespDto.setMsg("删除成功！");
		return baseRespDto;
	}
	
	@Override
	public QrtzJobDetails findQrtzJobDetailsByPrimaryKey(String id) {
		return this.qrtzJobDetailsMapper.selectByPrimaryKey(id);
	}

	@Override
	public BaseRespDto findMapListByPage(QrtzJobDetails qrtzJobDetails,BaseRespDto baseRespDto,Integer page,Integer limit ) {
		Long count = this.qrtzJobDetailsMapper.selectMapListCount(qrtzJobDetails);
		baseRespDto.setCount(count);
		PageHelper.startPage(page, limit);
		List<Map<String, Object>> maps = this.qrtzJobDetailsMapper.selectMapList(qrtzJobDetails);
		baseRespDto.setData(maps);
		return baseRespDto;
	}

	@Override
	public BaseRespDto findListByPage(QrtzJobDetails qrtzJobDetails, BaseRespDto baseRespDto, Integer page, Integer limit) {
		Long count = this.qrtzJobDetailsMapper.count(qrtzJobDetails);
		baseRespDto.setCount(count);
		PageHelper.startPage(page, limit);
		baseRespDto.setData(this.qrtzJobDetailsMapper.selectList(qrtzJobDetails));
		return baseRespDto;

	}

	@Override
	public List<Map<String, Object>> findMapList(QrtzJobDetails qrtzJobDetails) {
		return this.qrtzJobDetailsMapper.selectMapList(qrtzJobDetails);
	}
	
	@Override
	public List<QrtzJobDetails> findList(QrtzJobDetails qrtzJobDetails){
		return this.qrtzJobDetailsMapper.selectList(qrtzJobDetails);
	}

	@Override
	public Map<String, Object> pauseJob(QrtzJobDetails qrtzJobDetails)
			throws SchedulerException {
		Map<String, Object> resultMap = new HashMap<>();
		scheduler.pauseJob(JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup()));
		resultMap.put("success",true);
		resultMap.put("msg","已暂停任务！");
		return resultMap;
	}

	@Override
	public Map<String, Object> resumeJob(QrtzJobDetails qrtzJobDetails)
			throws SchedulerException {
		Map<String, Object> resultMap = new HashMap<>();
		scheduler.resumeJob(JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup()));
		resultMap.put("success",true);
		resultMap.put("msg","已恢复暂停任务！");
		return resultMap;
	}


	/**
	 * <li>校验服务和方法是否存在</li>
	 * @param jobName
	 * @throws DynamicQuartzException
	 */
	private void checkServiceAndMethod(String jobName) throws DynamicQuartzException {
		String[] serviceInfo = jobName.split("\\.");
		String beanName = serviceInfo[0];
		String methodName = serviceInfo[1];
		if (! SpringContextHolder.existBean(beanName)) {
			throw new DynamicQuartzException("找不到对应服务");
		}
		if (! SpringContextHolder.existBeanAndMethod(beanName, methodName, null)) {
			throw new DynamicQuartzException("服务方法不存在");
		}
		

	}
	




}
