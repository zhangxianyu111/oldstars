package com.demo.controller;

import com.demo.util.LogBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    private final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping(value={"/",""},method= RequestMethod.GET)
    public String index(){
        LOGGER.info(LogBuilderUtil.getBuilder("index","跳转登录页面","")
                .build());
        return "index";

    }
}
