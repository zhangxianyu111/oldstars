package com.demo.controller;

import com.demo.controller.log4j.ResLogController;
import com.demo.util.LogBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    private final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping(value={"/",""},method= RequestMethod.GET)
    public String index(){
        LOGGER.info(LogBuilderUtil.getBuilder("index","跳转登录页面","")
                .build());
        return "index";

    }
    @ResponseBody
    @RequestMapping("/testError")
    public String errorTest(){
        try {
            int i = 3/0;
        }catch (Exception e) {
            LogBuilderUtil.recordErrorLogs(e,"resLog", ResLogController.class.getName(),"getAllLogs");
        }
        return "error-test";
    }

    @ResponseBody
    @RequestMapping("/testWarn")
    public String warnTest(){
        try {
            int i = 3/0;
        }catch (Exception e) {
            LogBuilderUtil.recordWarnLogs("warn测试报错信息","resLog", ResLogController.class.getName(),"getAllLogs");
        }
        return "warn-test";
    }
}
