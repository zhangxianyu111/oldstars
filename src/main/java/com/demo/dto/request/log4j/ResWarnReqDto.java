package com.demo.dto.request.log4j;

import com.demo.dto.request.BaseReqDto;
import com.demo.util.SpringContextHolder;
import com.mchange.lang.LongUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

public class ResWarnReqDto extends BaseReqDto {

    //warnId
    private Long warnId;
    //warnIds
    private String warnIds;
    //模块
    private String warnModule;
    //处理状态 0：未处理 1：已处理
    private String status;
    //起始时间
    private String sTime;
    //结束时间
    private String eTime;
    //处理提交内容
    private String content;

    public String getWarnModule() {
        return warnModule;
    }

    public void setWarnModule(String warnModule) {
        this.warnModule = warnModule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getWarnId() {
        return warnId;
    }

    public void setWarnId(Long warnId) {
        this.warnId = warnId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWarnIds() {
        return warnIds;
    }

    public void setWarnIds(String warnIds) {
        this.warnIds = warnIds;
    }

    public Map getMap(){
        Map<String,Object> paramMap = new HashMap<>();
        if (warnId != null){
            paramMap.put("warnId",this.warnId);
        }
        if (StringUtils.isNotBlank(this.warnIds) &&
                StringUtils.isNotBlank(this.warnIds.trim())){
            paramMap.put("warnIds",this.warnIds.trim());
        }
        if (StringUtils.isNotBlank(this.warnModule) &&
                StringUtils.isNotBlank(this.warnModule.trim())){
            paramMap.put("warnModule",this.warnModule.trim());
        }
        if (StringUtils.isNotBlank(this.status) &&
                StringUtils.isNotBlank(this.status.trim())){
            paramMap.put("warnStatus",Integer.valueOf(this.status.trim()));
        }
        if (StringUtils.isNotBlank(this.sTime) &&
                StringUtils.isNotBlank(this.sTime.trim())){
            paramMap.put("sTime",this.sTime.trim());
        }
        if (StringUtils.isNotBlank(this.eTime) &&
                StringUtils.isNotBlank(this.eTime.trim())){
            paramMap.put("eTime",this.eTime.trim());
        }
        if (StringUtils.isNotBlank(this.content) &&
                StringUtils.isNotBlank(this.content.trim())){
            paramMap.put("warnMsg",this.content);
        }

        paramMap.put("page",this.getPage());
        paramMap.put("limit",this.getLimit());
        return paramMap;
    }

    @Override
    public String toString() {
        return "ResWarnReqDto{" +
                "warnId=" + warnId +
                ", warnIds='" + warnIds + '\'' +
                ", warnModule='" + warnModule + '\'' +
                ", status='" + status + '\'' +
                ", sTime='" + sTime + '\'' +
                ", eTime='" + eTime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
