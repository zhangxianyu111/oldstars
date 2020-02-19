package com.demo.init;

import com.demo.common.Statistics;
import com.demo.controller.log4j.ResLogController;
import com.demo.quarz.LoggerDBTimer;
import com.demo.service.LogPointerService;
import com.demo.util.ExceptionUtil;
import com.demo.util.PropertiesUtil;
import com.demo.util.XmlUtil;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
/**
 *
 * @author zje
 *
 */
@Component
public class InitConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger LOGGER = LoggerFactory.getLogger(ResLogController.class);

    private volatile static Statistics statistics;

    public static Statistics getStatistics(){
        if (statistics == null) {
            synchronized (InitConfig.class) {
                if (statistics == null) {
                    try {
                        String warnPath = PropertiesUtil.getProperty("warnPath","logpath.properties");
                        statistics = XmlUtil.getStatistics(warnPath);
                    }catch (Exception e){
                        MDC.put("exception",e.getClass().getName());
                        LOGGER.error(ExceptionUtil.getTrace(e),e);
                    }

                }
            }
        }
        return statistics;
    }

    private LogPointerService logPointerService;
    @Autowired
    public void setLogPointerService(LogPointerService logPointerService) {
        this.logPointerService = logPointerService;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LoggerDBTimer.pointer = logPointerService.select();
    }
}
