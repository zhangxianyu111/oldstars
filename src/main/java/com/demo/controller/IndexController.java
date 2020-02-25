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

    @ResponseBody
    @RequestMapping("/testWarn1")
    public String warnError1(){
        try {
            int i = 3/0;
        }catch (Exception e) {
            LogBuilderUtil.recordWarnLogs("resWarn测试报错信息","resWarn", ResLogController.class.getName(),"warnError1");
        }
        return "warn-test1";
    }

    @ResponseBody
    @RequestMapping("/testError1")
    public String warnTest1(){
        try {
            int i = 3/0;
        }catch (Exception e) {
            LogBuilderUtil.recordErrorLogs(e,"resWarn", ResLogController.class.getName(),"warnTest1");
        }
        return "error-test1";
    }


    @ResponseBody
    @RequestMapping("/testError3")
    public String errorTest3(){
        try {
            int i = 3/0;
        }catch (Exception e) {
            LogBuilderUtil.recordErrorLogs(e,"test", ResLogController.class.getName(),"errorTest3");
        }
        return "error-test3";
    }

    @ResponseBody
    @RequestMapping("/testWarn3")
    public String warnTest3(){
        try {
            int i = 3/0;
        }catch (Exception e) {
            LogBuilderUtil.recordWarnLogs("warn测试报错信息","test", ResLogController.class.getName(),"warnTest3");
        }
        return "warn-test3";
    }
}
