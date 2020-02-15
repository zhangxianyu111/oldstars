package com.demo.controller;



import com.demo.dto.log.LogInfoVO;
import com.demo.dto.log.LogLevelChangeVO;
import com.demo.dto.log.ZtreeNodeVO;
import com.demo.dto.response.BaseRespDto;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.*;

@Controller
@RequestMapping("/logLevel")
public class LogLevelController {
    private final Logger LOGGER = Logger.getLogger(LoggerController.class);
    private final static String PARAM_CHECH_LOG_LEVEL_ERROR = "请输入正确的日志级别：OFF、ERROR、WARN、INFO、DEBUG、TRACE、ALL；您输入的级别为：";
    private final static String PARAM_CHECH_LOG_TARGET_ERROR = "目标类或包不能为空";
    /**日志logger不存在提示信息**/
    private final static String LOGGER_NOT_EXIST_CHECK  = "日志logger不存在";
    /** logback日志级别*/
    private final static Set<String> logLevelCheck = new HashSet<String>();
    static{
        logLevelCheck.add("OFF");
        logLevelCheck.add("ERROR");
        logLevelCheck.add("WARN");
        logLevelCheck.add("INFO");
        logLevelCheck.add("DEBUG");
        logLevelCheck.add("TRACE");
        logLevelCheck.add("ALL");
    }
    /**
     * 跳转到日志级别管理列表页面
     * @return
     */
    @RequestMapping(value = "/level/toList")
    public String goToLogLevelPage() {
        return "log/logTree";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<LogInfoVO> listLogger() {
        Enumeration enumeration = LogManager.getCurrentLoggers();
        List<LogInfoVO> allLogger = new ArrayList<LogInfoVO>();
        while (enumeration.hasMoreElements()){
            Logger logger = (Logger) enumeration.nextElement();
            LogInfoVO info = new LogInfoVO();
            info.setLevel(logger.getEffectiveLevel());
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
    public String getLoggerLevel(@RequestParam(value="target", required=true) String target) {
        if(target == null || "".equals(target)) {
            return BaseRespDto.getFailInstance(PARAM_CHECH_LOG_TARGET_ERROR);
        }
        Logger logger = Logger.getLogger(target);
        if(logger!=null) {
            LogInfoVO llc = new LogInfoVO();
            llc.setName(logger.getName());
            llc.setLevel(logger.getEffectiveLevel());
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
            info.setLevel(logger.getEffectiveLevel());
            info.setName(logger.getName());
            allLogger.add(info);
        }
        ZtreeNodeVO root = null;
        try {
            root =this.getTree(allLogger);
        }catch(Exception e) {
            e.printStackTrace();
        }
        List<ZtreeNodeVO> nodes =  root.getChildren();
        return BaseRespDto.getSuccessInstance(nodes);
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
                                       @RequestParam(value="level" , required=true) String level,
                                       @RequestParam(value="target", required=true) String target) {
        if(!checkLogLevel(level)) {
            return BaseRespDto.getFailInstance(PARAM_CHECH_LOG_LEVEL_ERROR+level);
        }
        if(target == null || "".equals(target)) {
            return BaseRespDto.getFailInstance(PARAM_CHECH_LOG_TARGET_ERROR);
        }
        LogLevelChangeVO llc =  this.setLevel(target, level);
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
            @RequestParam(value="level" , required=true) String level) {
        if(!checkLogLevel(level)) {
            return PARAM_CHECH_LOG_LEVEL_ERROR+level;
        }
        return this.setLevel(level);
    }


    /**
     * 修改日志级别
     * @param target
     * @param level
     * @return
     */
    private LogLevelChangeVO setLevel(String target,String level) {
        Logger logger = Logger.getLogger(target);
        if(logger!=null) {
            LogLevelChangeVO llc = new LogLevelChangeVO();
            llc.setFullName(logger.getName());
            llc.setBeforeLevel(logger.getEffectiveLevel());
            logger.setLevel(Level.toLevel(level));
            llc.setAfterLevel(logger.getEffectiveLevel());
            return llc;
        }
        return null;
    }

    /**
     * 设置整个应用的日志级别，慎用此功能
     * @param level
     * @return
     */
    private String setLevel(String level) {
        Enumeration enumeration = LogManager.getCurrentLoggers();
        List<LogInfoVO> allLogger = new ArrayList<LogInfoVO>();
        //返回设置结果
        StringBuilder sb = new StringBuilder();
        sb.append("修改前日志级别：\r\n");
        while (enumeration.hasMoreElements()){
            Logger logger = (Logger) enumeration.nextElement();
            //记录修改前的日志级别
            this.recordLogLevel(sb, logger);
            //设置日志级别
            logger.setLevel(Level.toLevel(level));
        }
        sb.append("修改后日志级别：\r\n");
        while (enumeration.hasMoreElements()){
            Logger logger = (Logger) enumeration.nextElement();
            //记录修改后的日志级别
            this.recordLogLevel(sb, logger);
        }
        return sb.toString();
    }

    /**
     * 检查日志级别是否正确
     * @return  true 验证通过  false 验证未通过
     * **/
    private boolean checkLogLevel(String level) {
        return level==null || !logLevelCheck.contains(level)?false:true;
    }

    /**
     * 构建树形结构
     * @param allLogger
     */
    private ZtreeNodeVO getTree(List<LogInfoVO> allLogger) {
        HashMap<String,ZtreeNodeVO> treeMap = new HashMap<String,ZtreeNodeVO>();
        String fullName;
        // 根节点
        ZtreeNodeVO root = new ZtreeNodeVO();
        for(LogInfoVO logger : allLogger) {
            fullName = logger.getName();
            //如果是一級節點
            if(!fullName.contains(".")) {
                // 如果節點已經存在，則跳過
                if(treeMap.containsKey(fullName)) {
                    continue;
                }
                // 如果節點不存在
                ZtreeNodeVO topNode = new ZtreeNodeVO();
                topNode.setName(fullName);
                topNode.setFullName(fullName);
                root.addChild(topNode);
                treeMap.put(fullName, topNode);
                continue;
            }

            // 如果非一级节点
            String[] names = fullName.split("\\.");
            // 父节点全名
            String parentFullName ="";
            ZtreeNodeVO paretnNode = null;
            // 构建节点树
            for(int i = 0 ,j = names.length ; i < j ; i++) {
                ZtreeNodeVO node = treeMap.get(i==0?names[i]:parentFullName+"."+names[i]);
                if(node == null) {
                    // 一级节点
                    if( i == 0 ) {
                        node = new ZtreeNodeVO();
                        node.setName(names[i]);
                        node.setFullName(names[i]);
                        // 添加到树中
                        root.addChild(node);
                        treeMap.put(names[i], node);
                    }else {
                        node = new ZtreeNodeVO();
                        node.setName(names[i]);
                        node.setFullName(parentFullName+"."+node.getName());
                        paretnNode.addChild(node);
                        treeMap.put(fullName, node);
                    }
                }
                // 供下次循环使用
                if(i == j-1) {
                    parentFullName = "";
                    paretnNode = null;
                }else {
                    parentFullName = node.getFullName();
                    paretnNode = node;
                }
            }
        }
        return root;
    }

    /**
     * 记录日志级别
     * @param sb
     * @param logger
     * @return
     */
    private StringBuilder recordLogLevel(StringBuilder sb,Logger logger) {
        sb.append(logger.getName());
        if(logger.getEffectiveLevel()!=null) {
            sb.append("，EffectiveLevel=").append(logger.getEffectiveLevel());
        }
        if(logger.getLevel()!=null) {
            sb.append("，level=").append(logger.getLevel());
        }
        sb.append("#\r\n");
        return sb;
    }


}
