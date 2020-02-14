package com.demo.init;

import com.demo.quarz.LoggerDBTimer;
import com.demo.service.LogPointerService;
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
