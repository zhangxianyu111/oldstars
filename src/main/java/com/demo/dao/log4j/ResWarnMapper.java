package com.demo.dao.log4j;

import com.demo.pojo.log4j.ResLog;
import com.demo.pojo.log4j.ResWarn;

import java.util.List;
import java.util.Map;

public interface ResWarnMapper {

    List<ResLog> selectAll(Map paramMap);

    Long selectAllCount(Map paramMap);

    void handle(Map map);

    ResWarn seeWarn(Map map);
}
