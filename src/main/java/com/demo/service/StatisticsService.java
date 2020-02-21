package com.demo.service;

import java.util.List;
import java.util.Map;

/**
 * @author wxw
 */
public interface StatisticsService {

    List<Map<String, Object>> getExceptionTypesByTime(Map<String, Object> map);
//    List<Map> getExceptionTypesByTime(Map<String, Object> map);

    Map<String, Integer> getTotalCountByTime(Map<String, Object> map);

    List<Map<String, Object>> getTendencyDataByTime(Map<String, Object> map);

    Map<String, List<Map<String, Object>>> getRankListDataByTime(Map<String, Object> map);
}
