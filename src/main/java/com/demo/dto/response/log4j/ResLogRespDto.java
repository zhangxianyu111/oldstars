package com.demo.dto.response.log4j;

import com.demo.dto.response.BaseRespDto;
import com.demo.pojo.log4j.ResLog;

import java.util.List;

public class ResLogRespDto extends BaseRespDto {
    //错误日志数量
    private Long errCount;
    //警告日志数量
    private Long warnCount;
    //错误日志排行榜
    private List<ResLog> errList;
    //警告日志排行榜
    private List<ResLog> warnList;

    public Long getErrCount() {
        return errCount;
    }

    public void setErrCount(Long errCount) {
        this.errCount = errCount;
    }

    public Long getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(Long warnCount) {
        this.warnCount = warnCount;
    }

    public List<ResLog> getErrList() {
        return errList;
    }

    public void setErrList(List<ResLog> errList) {
        this.errList = errList;
    }

    public List<ResLog> getWarnList() {
        return warnList;
    }

    public void setWarnList(List<ResLog> warnList) {
        this.warnList = warnList;
    }

    @Override
    public String toString() {
        return "ResLogRespDto{" +
                "errCount=" + errCount +
                ", warnCount=" + warnCount +
                ", errList=" + errList +
                ", warnList=" + warnList +
                '}';
    }
}
