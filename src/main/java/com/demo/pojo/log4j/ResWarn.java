package com.demo.pojo.log4j;

import java.util.Date;

public class ResWarn {
    private Long warnId;
    //告警模块
    private String warnClass;
    //告警内容
    private String warnMsg;
    //处理状态0：未处理 1：已处理
    private int warnStatus;
    //告警时间
    private Date warnTime;

    public Long getWarnId() {
        return warnId;
    }

    public void setWarnId(Long warnId) {
        this.warnId = warnId;
    }

    public String getWarnClass() {
        return warnClass;
    }

    public void setWarnClass(String warnClass) {
        this.warnClass = warnClass;
    }

    public String getWarnMsg() {
        return warnMsg;
    }

    public void setWarnMsg(String warnMsg) {
        this.warnMsg = warnMsg;
    }

    public int getWarnStatus() {
        return warnStatus;
    }

    public void setWarnStatus(int warnStatus) {
        this.warnStatus = warnStatus;
    }

    public Date getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(Date warnTime) {
        this.warnTime = warnTime;
    }

    @Override
    public String toString() {
        return "ResWarn{" +
                "warnId=" + warnId +
                ", warnClass='" + warnClass + '\'' +
                ", warnMsg='" + warnMsg + '\'' +
                ", warnStatus=" + warnStatus +
                ", warnTime=" + warnTime +
                '}';
    }
}