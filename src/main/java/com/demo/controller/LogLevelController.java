package com.demo.controller;


import com.demo.dto.log.LogInfoVO;
import com.demo.dto.log.LogLevelChangeVO;
import com.demo.dto.log.ZtreeNodeVO;
import com.demo.dto.response.BaseRespDto;
import com.demo.service.serviceimpl.LogLevelServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/logLevel")
public class LogLevelController {
    private final Logger LOGGER = Logger.getLogger(LoggerController.class);
    private final static String PARAM_CHECH_LOG_LEVEL_ERROR = "请输入正确的日志级别：OFF、ERROR、WARN、INFO、DEBUG、TRACE、ALL；您输入的级别为：";
    private final static String PARAM_CHECH_LOG_TARGET_ERROR = "目标类或包不能为空";
    /**日志logger不存在提示信息**/
    private final static String LOGGER_NOT_EXIST_CHECK  = "日志logger不存在";

    @Resource
    private LogLevelServiceImpl logLevelService;

    /**
     * 跳转到日志级别管理列表页面
     * @return
     */
    @RequestMapping(value = "/level/toList")
    public String goToLogLevelPage() {
        return "logTree";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<LogInfoVO> listLogger() {
        Enumeration enumeration = LogManager.getCurrentLoggers();
        List<LogInfoVO> allLogger = new ArrayList<LogInfoVO>();
        while (enumeration.hasMoreElements()){
            Logger logger = (Logger) enumeration.nextElement();
            LogInfoVO info = new LogInfoVO();
            info.setLevel(logger.getEffectiveLevel().toString());
            info.setName(logger.getName());
            allLogger.add(info);
        }
        return allLogger;
    }

    /**
     * 获取某个类或包的日志级别
     * @return
     */
    @RequestMapping(value = "/get/level")
    @ResponseBody
    public String getLoggerLevel(@RequestParam(value="target") String target) {
        if(target == null || "".equals(target)) {
            return BaseRespDto.getFailInstance(PARAM_CHECH_LOG_TARGET_ERROR);
        }
        Logger logger = Logger.getLogger(target);
        if(logger!=null) {
            LogInfoVO llc = new LogInfoVO();
            llc.setName(logger.getName());
            llc.setLevel(logger.getEffectiveLevel().toString());
            return BaseRespDto.getSuccessInstance(llc);
        }
        return BaseRespDto.getFailInstance(LOGGER_NOT_EXIST_CHECK);
    }

    /**
     * 获取日志tree列表
     * @return
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public String listLoggerTree() {
        Enumeration enumeration = LogManager.getCurrentLoggers();
        List<LogInfoVO> allLogger = new ArrayList<LogInfoVO>();
        while (enumeration.hasMoreElements()){
            Logger logger = (Logger) enumeration.nextElement();
            LogInfoVO info = new LogInfoVO();
            info.setLevel(logger.getEffectiveLevel().toString());
            info.setName(logger.getName());
            allLogger.add(info);
        }
        ZtreeNodeVO root = null;
        try {
            root =logLevelService.getTree(allLogger);
        }catch(Exception e) {
            e.printStackTrace();
        }
        List<ZtreeNodeVO> nodes =  root.getChildren();
        return BaseRespDto.getSuccessListInstance(nodes);
    }

    /**
     * 设置某个类或某个包的日志输出级别
     * @param level    日志级别     只能取值：OFF、ERROR、WARN、INFO、DEBUG、TRACE、ALL
     * @param target   类或包名
     * @return
     */
    @RequestMapping(value = "/level")
    @ResponseBody
    public String changeLogLevel(
                                       @RequestParam(value="level") String level,
                                       @RequestParam(value="target") String target) {
        if(!logLevelService.checkLogLevel(level)) {
            return BaseRespDto.getFailInstance(PARAM_CHECH_LOG_LEVEL_ERROR+level);
        }
        if(target == null || "".equals(target)) {
            return BaseRespDto.getFailInstance(PARAM_CHECH_LOG_TARGET_ERROR);
        }
        LogLevelChangeVO llc =  logLevelService.setLevel(target, level);
        return llc == null ? BaseRespDto.getFailInstance(LOGGER_NOT_EXIST_CHECK) : BaseRespDto.getSuccessInstance(llc);
    }

    /**
     * 设置整个系统的日志级别，慎用
     * @param level
     * @return
     */
    @RequestMapping(value = "/level/all")
    @ResponseBody
    public String changeAllLevel(
            @RequestParam(value="level") String level) {
        if(!logLevelService.checkLogLevel(level)) {
            return PARAM_CHECH_LOG_LEVEL_ERROR+level;
        }
        return logLevelService.setLevel(level);
    }


}
