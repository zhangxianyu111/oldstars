package com.demo.service;

import com.demo.dto.log.LogInfoVO;
import com.demo.dto.log.LogLevelChangeVO;
import com.demo.dto.log.ZtreeNodeVO;
import org.apache.log4j.Logger;

import java.util.List;

public interface LogLevelService {
    /**
     * 修改日志级别
     * @param target
     * @param level
     * @return
     */
    LogLevelChangeVO setLevel(String target, String level);
    /**
     * 设置整个应用的日志级别，慎用此功能
     * @param level
     * @return
     */
    String setLevel(String level) ;
    /**
     * 检查日志级别是否正确
     * @return  true 验证通过  false 验证未通过
     * **/
    boolean checkLogLevel(String level);
    /**
     * 构建树形结构
     * @param allLogger
     */
    ZtreeNodeVO getTree(List<LogInfoVO> allLogger);
}
