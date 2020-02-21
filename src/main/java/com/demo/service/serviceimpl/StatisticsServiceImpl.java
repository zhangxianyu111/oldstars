package com.demo.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.demo.controller.LoggerController;
import com.demo.dao.StatisticsMapper;
import com.demo.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("statisticsService")
@Transactional(rollbackFor=Exception.class)
public class StatisticsServiceImpl implements StatisticsService {
    private final Logger logger
            = LoggerFactory.getLogger(LoggerController.class);

    @Resource
    private StatisticsMapper statisticsDao;
    @Override
    public List<Map<String, Object>> getExceptionTypesByTime(Map<String, Object> map) {
//        List<String> list = statisticsDao.selectAllExceptionTypesByTime(map);
//        Map map1 = eleCount(list);
//
//        List<Map> mapList = new ArrayList<>();
//        Iterator iterator = map1.keySet().iterator();
//        while (iterator.hasNext()){
//            Object next = iterator.next();
//            Map m = new HashMap();
//            m.put("value", map1.get(next));
//            m.put("name", next);
//            mapList.add(m);
//        }
        List<Map<String, Object>> maps = statisticsDao.selectAllExceptionTypesByTime(map);

        return maps;
    }

    @Override
    public Map<String, Integer> getTotalCountByTime(Map<String, Object> map) {
        List<String> list = statisticsDao.selectAllLogLevelCountByTime(map);
        Map<String, Integer> map1 = eleCount(list);
        return map1;
    }

    @Override
    public List<Map<String, Object>> getTendencyDataByTime(Map<String, Object> map) {
        List<Map<String, Object>> resultListTmp = statisticsDao.selectTendencyDataByTime(map);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> mapTmp: resultListTmp){
            Map<String, Object> resultMapTmp = new HashMap<>();
            resultMapTmp.put("time", mapTmp.get("time"));
            resultMapTmp.put(mapTmp.get("level").toString(), mapTmp.get("count"));
            resultList.add(resultMapTmp);
        }

        for(int i =1;i<resultList.size();i++) {
            for(int j=0;j<resultList.size()-i;j++) {
                if(resultList.get(j).get("time").equals(resultList.get(j + 1).get("time"))) {
                    resultList.get(j).putAll(resultList.get(j+1));
                    resultList.remove(j+1);
                }
            }
        }

        return resultList;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getRankListDataByTime(Map<String, Object> map) {
        List<Map<String, Object>> resultListTmp = statisticsDao.getRankListDataByTime(map);
        List<Map<String, Object>> errorList = new ArrayList<>();
        List<Map<String, Object>> warnList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
        for (Map<String, Object> mapTmp: resultListTmp){
            if(mapTmp.get("level") != null && mapTmp.get("level").equals("ERROR")){
                errorList.add(mapTmp);
            }
            if(mapTmp.get("level") != null && mapTmp.get("level").equals("WARN")){
                warnList.add(mapTmp);
            }
        }
        resultMap.put("errorList",errorList);
        resultMap.put("warnList",warnList);
        return resultMap;
    }

    private Map<String, Integer> eleCount(List<String> list){
        Map<String, Integer> map1 = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            int freq = map1.get(list.get(i)) == null ? 0 : map1.get(list.get((i)));
            map1.put(list.get(i), freq == 0 ? 1 : freq + 1);
        }
        return map1;
    }
}
