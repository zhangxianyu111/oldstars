package com.demo.dao;

import java.util.List;
import java.util.Map;

public interface StatisticsMapper {
    List<Map<String, Object>> selectAllExceptionTypesByTime(Map<String, Object> map);
//    List<String> selectAllExceptionTypesByTime(Map<String, Object> map);

    List<String> selectAllLogLevelCountByTime(Map<String, Object> map);

    List<Map<String, Object>> selectTendencyDataByTime(Map<String, Object> map);

    List<Map<String, Object>> getRankListDataByTime(Map<String, Object> map);
}
