package com.demo.init;

import com.demo.service.LogPointerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**jingEn*/
/*@Component
public class InitConfig implements ApplicationListener<ContextRefreshedEvent> {
    private LogPointerService logPointerService;
    @Autowired
    public void setLogPointerService(LogPointerService logPointerService) {
        this.logPointerService = logPointerService;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LoggerDBTimer.pointer = logPointerService.select();
    }
}*/

/**Byron*/
@Component
//public class InitConfig implements ApplicationListener<ContextRefreshedEvent> {
public class InitConfig {

    private LogPointerService logPointerService;
    @Autowired
    public void setLogPointerService(LogPointerService logPointerService) {
        this.logPointerService = logPointerService;
    }
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        LoggerDBTimer.pointer = logPointerService.select();
//    }
}
