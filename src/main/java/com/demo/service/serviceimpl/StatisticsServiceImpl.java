package com.demo.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.demo.controller.LoggerController;
import com.demo.dao.StatisticsMapper;
import com.demo.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.ast.OpNE;
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
    public List<Map> getExceptionTypesByTime(Map<String, String> map) {
        List<String> list = statisticsDao.selectAllLogTitlesByTime(map);
        Map map1 = eleCount(list);

        List<Map> mapList = new ArrayList<>();
        Iterator iterator = map1.keySet().iterator();
        while (iterator.hasNext()){
            Object next = iterator.next();
            Map m = new HashMap();
            m.put("value", map1.get(next));
            m.put("name", next);
            mapList.add(m);
        }
        return mapList;
    }

    @Override
    public Map<String, Integer> getTotalCountByTime(Map<String, Object> map) {
        List<String> list = statisticsDao.selectAllCountByTime(map);
        Map<String, Integer> map1 = eleCount(list);
        return map1;
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
