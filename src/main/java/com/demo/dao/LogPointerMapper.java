package com.demo.dao;

import com.demo.pojo.LogPointer;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author zje
 *
 */
public interface LogPointerMapper {

    //改
    int update(@Param("pointer") Long pointer);
    //查
    LogPointer select();
}
