package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.service.StatisticsService;
import com.demo.util.LogBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins={"*"}, methods={RequestMethod.GET, RequestMethod.POST})
@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private StatisticsService statisticsService;

    private Map<String, Object> storeParam(String sTime, String eTime, List<String> conditions){
        logger.info(LogBuilderUtil.getBuilder("statistics->storeParam","封装参数","开始...")
                .build());

        Map<String, Object> map = new HashMap<>();

        if(sTime == null || ("").equals(sTime)){
            String sTimeTmp = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date());
            map.put("sTime", sTimeTmp);
        }else{
            map.put("sTime", sTime);
        }

        if(eTime == null || ("").equals(eTime)){
            String eTimeTmp = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(new Date());
            map.put("sTime", eTimeTmp);
        }else{
            map.put("eTime", eTime);
        }

        if(conditions != null){
            map.put("conditions", conditions);
        }else{
            List<String> list = new ArrayList<>();
            list.add("ERROR");
            list.add("WARN");
            map.put("conditions", list);
        }
        logger.info(LogBuilderUtil.getBuilder("statistics->storeParam","封装参数","结束...")
                .build());
        return map;
    }
    @RequestMapping(value="/toStatisticsIndex",method= RequestMethod.GET)
    public String toStatisticsIndex(){
        logger.info(LogBuilderUtil.getBuilder("statistics->toStatisticsIndex","跳转日志统计页面","")
                .build());
        return "jsp/statisticsIndex";
    }

    //异常类型饼图
    @ResponseBody
    @RequestMapping(value="/getAllExceptionTypesByTime",
            produces = "text/html;charset=UTF-8",
            method= RequestMethod.POST)
    public String getAllExceptionTypesByTime(
            @RequestParam(name = "sTime", defaultValue = "") String sTime,
            @RequestParam(name = "eTime", defaultValue = "") String eTime,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit){
        logger.info(LogBuilderUtil.getBuilder("statistics->getExceptionTypesByTime","根据时间获取所有异常类","begin").build());

        Map<String, Object> map = storeParam(sTime, eTime, null);
        map.put("limit", limit);

        List<Map<String, Object>> list = statisticsService.getExceptionTypesByTime(map);

        String s = JSONObject.toJSONString(list);

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

        Map<String, Object> map = storeParam(sTime, eTime, conditions);
        Map<String, Integer> resultMap = statisticsService.getTotalCountByTime(map);

        logger.info(LogBuilderUtil.getBuilder("statistics->getExceptionTypesByTime","根据时间获取所有异常类","end").build());
        return resultMap;
    }

    //根据时间获取异常趋势柱图数据
    @RequestMapping(value="/getTendencyDataByTime",method= RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getTendencyDataByTime(
            @RequestParam(name = "sTime", defaultValue = "") String sTime,
            @RequestParam(name = "eTime", defaultValue = "") String eTime,
            @RequestParam("conditions[]") List<String> conditions){
        logger.info(LogBuilderUtil.getBuilder("statistics->getTendencyDataByTime","根据时间获取异常趋势图数据","begin").build());

        Map<String, Object> map = storeParam(sTime, eTime, conditions);
        //按天和自定义查找都是10
        map.put("gist", 10);

        //按月查找是7
//        map.put("gist", 7);

        List<Map<String, Object>> resultList = statisticsService.getTendencyDataByTime(map);

        logger.info(LogBuilderUtil.getBuilder("statistics->getTendencyDataByTime","根据时间获取异常趋势图数据","end").build());
        return resultList;
    }

    //根据时间获取异常趋势柱图数据
    @RequestMapping(value="/getRankListDataByTime",produces = "text/html;charset=UTF-8",method= RequestMethod.POST)
    @ResponseBody
    public Map<String, List<Map<String, Object>>> getRankListDataByTime(
            @RequestParam(name = "sTime", defaultValue = "") String sTime,
            @RequestParam(name = "eTime", defaultValue = "") String eTime,
            @RequestParam(name = "limit", defaultValue = "") Integer limit,
            @RequestParam("conditions[]") List<String> conditions){
        logger.info(LogBuilderUtil.getBuilder("statistics->getTendencyDataByTime","根据时间获取异常趋势图数据","begin").build());

        Map<String, Object> map = storeParam(sTime, eTime, conditions);
        map.put("limit",limit);

        Map<String, List<Map<String, Object>>> resultMap = statisticsService.getRankListDataByTime(map);

        logger.info(LogBuilderUtil.getBuilder("statistics->getTendencyDataByTime","根据时间获取异常趋势图数据","end").build());
        return resultMap;
    }

}
