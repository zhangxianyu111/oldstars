package com.demo.dao;

import java.util.List;
import java.util.Map;

public interface StatisticsMapper {
    List<String> selectAllLogTitlesByTime(Map<String, String> map);

    List<String> selectAllCountByTime(Map<String, Object> map);
}
