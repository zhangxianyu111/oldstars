package com.demo.dto.request.log4j;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ResLogReqDto extends BaseReqDto {
    //模块
    private String logClass;
    //日志级别
    private String logLevel;
    //起始时间
    private String sTime;
    //结束时间
    private String eTime;

    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public Map getMap(){
        Map<String,Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(this.logClass) &&
        StringUtils.isNotBlank(this.logClass.trim())){
            paramMap.put("logClass",this.logClass);
        }
        if (StringUtils.isNotBlank(this.logLevel) &&
        StringUtils.isNotBlank(this.logLevel.trim())){
            paramMap.put("logClass",this.logLevel);
        }
        if (StringUtils.isNotBlank(this.sTime) &&
                StringUtils.isNotBlank(this.sTime.trim())){
            paramMap.put("createTime",this.sTime+" 00:00:00");
        }
        if (StringUtils.isNotBlank(this.eTime) &&
                StringUtils.isNotBlank(this.eTime.trim())){
            paramMap.put("createTime",this.eTime+" 23:59:59");
        }
        paramMap.put("page",this.getPage());
        paramMap.put("limit",this.getLimit());
        return paramMap;
    }

    @Override
    public String toString() {
        return "ResLogReqDto{" +
                "logClass='" + logClass + '\'' +
                ", logLevel='" + logLevel + '\'' +
                '}';
    }
}
