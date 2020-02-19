package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.constant.StatusConstant;
import com.demo.dto.response.BaseRespDto;
import com.demo.exception.DynamicQuartzException;
import com.demo.pojo.QrtzJobDetails;
import com.demo.service.QrtzJobDetailsService;
import com.demo.util.LogBuilderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author zje
 *
 */
@CrossOrigin(origins={"*"}, methods={RequestMethod.GET, RequestMethod.POST})
@Controller
@RequestMapping("/qrtzJobDetails")
public class QrtzJobDetailsController {
	private static final Logger logger = LoggerFactory.getLogger(QrtzJobDetailsController.class);
	
	@Autowired
	private QrtzJobDetailsService qrtzJobDetailsService;

	@RequestMapping(value="/qrtzIndex",method= RequestMethod.GET)
	public String logsPage(){
		logger.info(LogBuilderUtil.getBuilder("logs","跳转定时任务页面","")
				.build());
		return "quartz";

	}
	@RequestMapping(value="/addQrtz",method= RequestMethod.GET)
	public String addQrtz(){
		logger.info(LogBuilderUtil.getBuilder("logs","跳转添加定时任务页面","")
				.build());
		return "addquartz";

	}
	@RequestMapping("/listByPage")
	@ResponseBody
	public  String listByPage(String filter, QrtzJobDetails qrtzJobDetails, @RequestParam(value="page",defaultValue="1")Integer page, @RequestParam(value="limit",defaultValue="20")Integer limit) {
		logger.info(LogBuilderUtil.getBuilder("listByPage","查询定时任务","根据id获取用户信息开始")
				.appendParam("参数",qrtzJobDetails).build());
		BaseRespDto baseRespDto = new BaseRespDto();
		try {
			//设置默认排序属性
			//page.setDefaultSort("createTime", "desc");
			baseRespDto = this.qrtzJobDetailsService.findMapListByPage(qrtzJobDetails,baseRespDto, page,limit);
			//baseRespDto = this.qrtzJobDetailsService.findListByPage(qrtzJobDetails,baseRespDto, page,limit);
			baseRespDto.setCode(StatusConstant.SUCCESS);
			logger.info(LogBuilderUtil.getBuilder("listByPage","查询定时任务","根据id获取用户信息结束")
					.appendParam("响应信息",baseRespDto).build());
		}catch (Exception e){
			baseRespDto.setCode(StatusConstant.FAIL);
			logger.info(LogBuilderUtil.getBuilder("listByPage","查询定时任务","异常")
					.build(),e);
		}

		return JSON.toJSONString(baseRespDto);
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST,produces = {"text/html;charset=utf-8"})
	@ResponseBody
	public String addQrtzJobDetails(QrtzJobDetails qrtzJobDetails, HttpServletRequest request) {
		logger.info(LogBuilderUtil.getBuilder("addQrtzJobDetails","添加定时任务","动态添加定时任务开始")
				.appendParam("参数",qrtzJobDetails).build());
		BaseRespDto baseRespDto = new BaseRespDto();
		try {
			baseRespDto = this.qrtzJobDetailsService.createQrtzJobDetails(qrtzJobDetails,baseRespDto);
			logger.info(LogBuilderUtil.getBuilder("addQrtzJobDetails","添加定时任务","动态添加定时任务结束")
					.appendParam("响应信息",baseRespDto).build());
		}catch (DynamicQuartzException de){
			baseRespDto.setCode(StatusConstant.FAIL);
			baseRespDto.setMsg(de.getMessage());
			logger.info(LogBuilderUtil.getBuilder("addQrtzJobDetails","添加定时任务","异常")
					.build(),de);
		}catch (Exception e){
			baseRespDto.setCode(StatusConstant.FAIL);
			baseRespDto.setMsg("系统异常！");
			logger.info(LogBuilderUtil.getBuilder("addQrtzJobDetails","添加定时任务","系统异常")
					.build(),e);
		}
		return JSON.toJSONString(baseRespDto);
	}
	
	@RequestMapping(value = "/edit",method = RequestMethod.POST,produces = {"text/html;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> updateQrtzJobDetails(QrtzJobDetails qrtzJobDetails, HttpServletRequest request)  {
		logger.info(LogBuilderUtil.getBuilder("updateQrtzJobDetails","修改定时任务","动态修改定时任务开始")
				.appendParam("任务名称",qrtzJobDetails.getJobName()).build());
		Map<String, Object> map = new HashMap<>();
		try {
			map = this.qrtzJobDetailsService.updateQrtzJobDetails(qrtzJobDetails);
			logger.info(LogBuilderUtil.getBuilder("updateQrtzJobDetails","修改定时任务","动态修改定时任务结束")
					.appendParam("响应信息",map).build());
		}catch (Exception e){
			map.put("success", false);
			map.put("msg", "系统异常！");
			logger.info(LogBuilderUtil.getBuilder("addQrtzJobDetails","添加定时任务","异常")
					.build(),e);
		}

		return map;
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"text/html;charset=utf-8"})
	@ResponseBody
	public String deleteQrtzJobDetails(QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception{
		logger.info(LogBuilderUtil.getBuilder("deleteQrtzJobDetails","删除定时任务","动态删除定时任务,先暂停再删除开始")
				.appendParam("任务名称",qrtzJobDetails.getJobName()).build());
		Map<String, Object> map = new HashMap<>();
		BaseRespDto baseRespDto = new BaseRespDto();
		try {
			baseRespDto = this.qrtzJobDetailsService.deleteQrtzJobDetails(qrtzJobDetails,baseRespDto);
			logger.info(LogBuilderUtil.getBuilder("deleteQrtzJobDetails","删除定时任务","动态删除定时任务,先暂停再删除结束")
					.appendParam("响应信息",map).build());
		}catch (Exception e){
			baseRespDto.setCode(StatusConstant.FAIL);
			baseRespDto.setMsg("系统异常！");
			logger.info(LogBuilderUtil.getBuilder("deleteQrtzJobDetails","删除定时任务","异常")
					.build(),e);
		}

		return JSONObject.toJSONString(baseRespDto);
	}
	
	@RequestMapping(value = "/pause",method = RequestMethod.POST,produces = {"text/html;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> pauseJob(QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception{
		logger.info(LogBuilderUtil.getBuilder("pauseJob","暂停定时任务","暂停定时任务开始")
				.appendParam("任务名称",qrtzJobDetails.getJobName()).build());
		Map<String, Object> map = new HashMap<>();
		try {
			map = this.qrtzJobDetailsService.pauseJob(qrtzJobDetails);
			logger.info(LogBuilderUtil.getBuilder("pauseJob","暂停定时任务","暂停定时任务结束")
					.appendParam("响应信息",map).build());
		}catch (Exception e){
			map.put("success", false);
			map.put("msg", "系统异常！");
			logger.info(LogBuilderUtil.getBuilder("pauseJob","暂停定时任务","异常")
					.build(),e);
		}
		return map;
	}
	
	@RequestMapping(value = "/resume", method = RequestMethod.POST,produces = {"text/html;charset=utf-8"})
	@ResponseBody
	public Map<String, Object> resumeJob(QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception{
		logger.info(LogBuilderUtil.getBuilder("resumeJob","恢复暂停的定时任务","开始")
				.appendParam("任务名称",qrtzJobDetails.getJobName()).build());
		Map<String, Object> map = new HashMap<>();
		try {
			map = this.qrtzJobDetailsService.resumeJob(qrtzJobDetails);
			logger.info(LogBuilderUtil.getBuilder("resumeJob","恢复暂停的定时任务","结束")
					.appendParam("响应信息",map).build());
		}catch (Exception e){
			map.put("success", false);
			map.put("msg", "系统异常！");
			logger.info(LogBuilderUtil.getBuilder("pauseJob","恢复暂停的定时任务","异常")
					.build(),e);
		}

		return map;
	}
	
}
