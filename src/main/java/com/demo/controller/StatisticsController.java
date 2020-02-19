package com.demo.controller;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.alibaba.fastjson.JSONObject;
import com.demo.service.StatisticsService;
import com.demo.util.LogBuilderUtil;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins={"*"}, methods={RequestMethod.GET, RequestMethod.POST})
@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value="/toStatisticsIndex",method= RequestMethod.GET)
    public String toStatisticsIndex(){
        logger.info(LogBuilderUtil.getBuilder("statistics->toStatisticsIndex","跳转日志统计页面","")
                .build());
        return "jsp/statisticsIndex";
    }

    //异常类型饼图
    @RequestMapping(value="/getAllExceptionTypesByTime",method= RequestMethod.POST)
    @ResponseBody
//    public String getExceptionTypesByTime(@PathVariable String time1, @PathVariable() String time2){
    public String getAllExceptionTypesByTime(
            @RequestParam(name = "sTime", defaultValue = "") String sTime,
            @RequestParam(name = "eTime", defaultValue = "") String eTime){
        logger.info(LogBuilderUtil.getBuilder("statistics->getExceptionTypesByTime","根据时间获取所有异常类","begin").build());
        Map<String, String> map = new HashMap<>();
        map.put("sTime", sTime);
        map.put("eTime", eTime);

        List<Map> list = statisticsService.getExceptionTypesByTime(map);
        String s = JSONObject.toJSONString(list);
//        System.err.println(s);
        logger.info(LogBuilderUtil.getBuilder("statistics->getExceptionTypesByTime","根据时间获取所有异常类","end").build());
        return s;
    }

    @RequestMapping(value="/getTotalCountByTime",method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> getTotalCountByTime(
            @RequestParam(name = "sTime", defaultValue = "") String sTime,
            @RequestParam(name = "eTime", defaultValue = "") String eTime,
            @RequestParam("conditions[]") List<String> conditions){
        logger.info(LogBuilderUtil.getBuilder("statistics->getExceptionTypesByTime","根据时间获取所有异常类","begin").build());
        Map<String, Object> map = new HashMap<>();
        map.put("sTime", sTime);
        map.put("eTime", eTime);
        map.put("conditions", conditions);

        Map<String, Integer> mp = statisticsService.getTotalCountByTime(map);
        logger.info(LogBuilderUtil.getBuilder("statistics->getExceptionTypesByTime","根据时间获取所有异常类","end").build());
        return mp;
    }

}
