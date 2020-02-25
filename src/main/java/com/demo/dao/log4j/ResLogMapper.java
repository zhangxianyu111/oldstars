package com.demo.dao.log4j;

import com.demo.pojo.log4j.ResLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface ResLogMapper {
    List<ResLog> selectAll(Map<String,Object> paramMap);
    Long selectAllCount(Map<String,Object> paramMap);
    ResLog selectById(@Param("logId") Long logId);
    void deleteExpire();
    List<ResLog> selectByIds(@Param("ids") int[] ids);
}
