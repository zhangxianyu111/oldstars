package com.demo.init;

import com.demo.common.Statistics;
import com.demo.quarz.LoggerDBTimer;
import com.demo.service.LogPointerService;
import com.demo.util.LogBuilderUtil;
import com.demo.util.PropertiesUtil;
import com.demo.util.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zje
 *
 */
@Component
public class InitConfig implements ApplicationListener<ContextRefreshedEvent> {

    public static final Map<String,Object> MODULEMAP = new HashMap<>();

    private volatile static Statistics statistics;

    public InitConfig(){
        getStatistics();
    }

    public static Statistics getStatistics(){
        if (statistics == null) {
            synchronized (InitConfig.class) {
                if (statistics == null) {
                    try {
                        String warnPath = PropertiesUtil.getProperty("warnPath", "module-xml.properties");
                        statistics = XmlUtil.getStatistics(warnPath);
                        if (statistics != null){
                            Statistics.Moudle moudle = statistics.getMoudle();
                            List<Statistics.Item> items = moudle.getItems();
                            for (Statistics.Item item : items) {
                                MODULEMAP.put(item.getName(),item.getDesc());
                            }
                        }
                    }catch (Exception e){
                        LogBuilderUtil.recordErrorLogs(e,"resLog",InitConfig.class.getName(),"getStatistics");
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
