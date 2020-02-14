package com.demo.dto.request;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author zje
 *
 */
public class LogReqDto {
    Long id;
    String module;
    String logDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public Map<String,Object> addResultMap(){
        Map<String,Object> resultMap = new HashMap<>();
        if (id != null){
            resultMap.put("id",id);
        }
        if (StringUtils.isNotBlank(module.trim())){
            resultMap.put("module",module);
        }
        if (StringUtils.isNotBlank(logDate.trim())){
            resultMap.put("logDate",logDate);
        }
        return resultMap;
    }

    @Override
    public String toString() {
        return "LogReqDto{" +
                "id=" + id +
                ", module='" + module + '\'' +
                ", logDate='" + logDate + '\'' +
                '}';
    }
}
