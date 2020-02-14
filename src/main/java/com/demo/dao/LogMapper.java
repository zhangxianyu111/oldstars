package com.demo.dao;

import com.demo.pojo.ErrorLogInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 *
 * @author zje
 *
 */
public interface LogMapper {

    //增
    int insert(ErrorLogInfo info);
    //删
    int delete(Map<String,Object> paramMap);
    //改
    int update(ErrorLogInfo info);
    //查
    ErrorLogInfo selectById(@Param("id") Long id);
    List<ErrorLogInfo> selectAll(Map<String,Object> paramMap);
    //删除过期日志信息（三个月之前的清除）
    void deleteExpire();

    Long selectAllCount(Map<String, Object> paramMap);
}
