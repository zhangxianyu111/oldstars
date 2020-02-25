package com.demo.pojo.log4j;

import java.util.Date;

public class ResLog {

    private Long logId;

    private String logModule;

    private String logMethod;

    private Date  createTime;

    private String logLevel;

    private String logException;

    private String logMsg;

    private String moduleName;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getLogModule() {
        return logModule;
    }

    public void setLogModule(String logModule) {
        this.logModule = logModule;
    }

    public String getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(String logMethod) {
        this.logMethod = logMethod;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogException() {
        return logException;
    }

    public void setLogException(String logException) {
        this.logException = logException;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String toString() {
        return "ResLog{" +
                "logId=" + logId +
                ", logModule='" + logModule + '\'' +
                ", logMethod='" + logMethod + '\'' +
                ", createTime=" + createTime +
                ", logLevel='" + logLevel + '\'' +
                ", logException='" + logException + '\'' +
                ", logMsg='" + logMsg + '\'' +
                '}';
    }
}
