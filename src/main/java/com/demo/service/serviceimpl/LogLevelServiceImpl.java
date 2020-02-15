package com.demo.service.serviceimpl;

import com.demo.dto.log.LogInfoVO;
import com.demo.dto.log.LogLevelChangeVO;
import com.demo.dto.log.ZtreeNodeVO;
import com.demo.service.LogLevelService;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class LogLevelServiceImpl implements LogLevelService {

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

    @Override
    public LogLevelChangeVO setLevel(String target, String level) {
        Logger logger = Logger.getLogger(target);
        if(logger!=null) {
            LogLevelChangeVO llc = new LogLevelChangeVO();
            llc.setFullName(logger.getName());
            llc.setBeforeLevel(logger.getEffectiveLevel().toString());
            logger.setLevel(Level.toLevel(level));
            llc.setAfterLevel(logger.getEffectiveLevel().toString());
            return llc;
        }
        return null;
    }

    @Override
    public String setLevel(String level) {
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

    @Override
    public boolean checkLogLevel(String level) {
        return level==null || !logLevelCheck.contains(level)?false:true;
    }

    @Override
    public ZtreeNodeVO getTree(List<LogInfoVO> allLogger) {
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
    public StringBuilder recordLogLevel(StringBuilder sb, Logger logger) {
        sb.append(logger.getName());
        if(logger.getEffectiveLevel()!=null) {
            sb.append("，EffectiveLevel=").append(logger.getEffectiveLevel().toString());
        }
        if(logger.getLevel()!=null) {
            sb.append("，level=").append(logger.getLevel().toString());
        }
        sb.append("#\r\n");
        return sb;
    }
}
