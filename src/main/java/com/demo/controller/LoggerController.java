package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.constant.StatusConstant;
import com.demo.dto.response.BaseRespDto;
import com.demo.quarz.LoggerDBTimer;
import com.demo.service.LogService;
import com.demo.util.DateUtil;
import com.demo.util.LogBuilderUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author zje
 *
 */
@Controller
@RequestMapping("/log")
public class LoggerController {

    private final Logger LOGGER = LoggerFactory.getLogger(LoggerController.class);

    @Resource
    private LogService logService;
    @Resource
    private LoggerDBTimer loggerDBTimer;

    @RequestMapping(value="/logs",method= RequestMethod.GET)
    public String logsPage(){
        LOGGER.info(LogBuilderUtil.getBuilder("logs","跳转日志类型页面","")
                .build());
        return "log";

    }

    @RequestMapping(value="/allLogs",method= RequestMethod.POST)
    @ResponseBody
    public String selectLogs( String module,String logDate, @RequestParam(value="page",defaultValue="1")Integer page, @RequestParam(value="limit",defaultValue="20")Integer limit) {
        LOGGER.info(LogBuilderUtil.getBuilder("selectLogs","查询日志信息","开始").appendParam("参数","").build());
        BaseRespDto baseRespDto = new BaseRespDto();
        try {
            Map<String,Object> map = new HashMap<>();
            if (StringUtils.isNotBlank(module) && StringUtils.isNotBlank(module.trim())){
                map.put("module",module);
            }
            if (StringUtils.isNotBlank(logDate) && StringUtils.isNotBlank(logDate.trim())){
                map.put("logDate",logDate) ;
            }else{
                //默认当天的日志
                map.put("logDate", DateUtil.getTodayStr());
            }
            baseRespDto = logService.selectAllByPage(map,baseRespDto, page, limit);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LOGGER.info(LogBuilderUtil.getBuilder("selectLogs","查询日志信息","结束").appendParam("响应结果",baseRespDto).build());
        }catch(Exception e){
            baseRespDto.setCode(StatusConstant.FAIL);
            LOGGER.error(LogBuilderUtil.getBuilder("selectLogs","查询日志信息","系统异常")
                    .build(),e);
        }

        return JSONObject.toJSONString(baseRespDto);
    }

    @RequestMapping(value="/downloadLog",method= RequestMethod.GET)
    public void downloadLog(@RequestParam("id") Long id, HttpServletResponse response){
        LOGGER.info(LogBuilderUtil.getBuilder("downloadLog","下载错误日志信息","开始")
                .appendParam("参数",id).build());
        try {
            logService.downloadLog(id,response);
            LOGGER.info(LogBuilderUtil.getBuilder("downloadLog","下载错误日志信息","结束").build());
        }catch (Exception e){
            LOGGER.error(LogBuilderUtil.getBuilder("downloadLog","下载错误日志信息","系统异常")
                    .build(),e);
        }

    }

    @RequestMapping(value="/onekeybod",method= RequestMethod.POST,produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public String onekeybod(){
        LOGGER.info(LogBuilderUtil.getBuilder("onekeybod","一键同步","开始")
                .build());
        BaseRespDto baseRespDto = new BaseRespDto();
        try {
            loggerDBTimer.Scheduled();
            LOGGER.info(LogBuilderUtil.getBuilder("onekeybod","一键同步","结束")
                    .build());
            baseRespDto.setMsg("同步成功");
            baseRespDto.setCode(StatusConstant.SUCCESS);
        }catch (Exception e){
            LOGGER.error(LogBuilderUtil.getBuilder("onekeybod","一键同步","系统异常")
                    .build(),e);
            baseRespDto.setMsg("系统错误");
            baseRespDto.setCode(StatusConstant.FAIL);
        }
        return JSONObject.toJSONString(baseRespDto);

    }
}
