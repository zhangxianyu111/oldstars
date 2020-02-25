package com.demo.dto.request.log4j;

import com.demo.dto.request.BaseReqDto;
import com.demo.util.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ResLogReqDto extends BaseReqDto {
    //模块
    private String moduleName;
    //日志级别
    private String logLevel;
    //起始时间
    private String sTime;
    //结束时间
    private String eTime;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
        if (StringUtils.isNotBlank(this.moduleName) &&
        StringUtils.isNotBlank(this.moduleName.trim())){
            paramMap.put("moduleName",this.moduleName.trim());
        }
        if (StringUtils.isNotBlank(this.logLevel) &&
        StringUtils.isNotBlank(this.logLevel.trim())){
            paramMap.put("logLevel",this.logLevel.trim());
        }
        if (StringUtils.isNotBlank(this.sTime) &&
                StringUtils.isNotBlank(this.sTime.trim())){
            paramMap.put("sTime",this.sTime.trim());
        }
        if (StringUtils.isNotBlank(this.eTime) &&
                StringUtils.isNotBlank(this.eTime.trim())){
            paramMap.put("eTime",this.eTime.trim());
        }
        paramMap.put("page",this.getPage());
        paramMap.put("limit",this.getLimit());
        return paramMap;
    }

    @Override
    public String toString() {
        return "ResLogReqDto{" +
                "moduleName='" + moduleName + '\'' +
                ", logLevel='" + logLevel + '\'' +
                '}';
    }
}
