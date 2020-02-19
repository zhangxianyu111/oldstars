package com.demo.service;

import java.util.List;
import java.util.Map;

/**
 * @author wxw
 */
public interface StatisticsService {

    List<Map> getExceptionTypesByTime(Map<String, String> map);

    Map<String, Integer> getTotalCountByTime(Map<String, Object> map);
}
