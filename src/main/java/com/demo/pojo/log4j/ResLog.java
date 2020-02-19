package com.demo.pojo.log4j;

import java.util.Date;

public class ResLog {
    /**
     * `LOGID` int(20) NOT NULL AUTO_INCREMENT,
     *   `CLASS` varchar(200) DEFAULT NULL,
     *   `METHOD` varchar(100) DEFAULT NULL,
     *   `CREATETIME` date DEFAULT NULL,
     *   `LOGLEVEL` varchar(50) DEFAULT NULL,
     *   `LOGLINE` varchar(255) DEFAULT NULL,
     *   `MSG` varchar(4000) DEFAULT NULL,
     */
    private Long logId;

    private String logClass;

    private String logMethod;

    private Date  createTime;

    private String logLevel;

    private String logLine;

    private String logMsg;

    //不与数据库关联(文件路径 例如：logcase-2020-02-07.log)
    private String path;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
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

    public String getLogLine() {
        return logLine;
    }

    public void setLogLine(String logLine) {
        this.logLine = logLine;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ResLog{" +
                "logId=" + logId +
                ", logClass='" + logClass + '\'' +
                ", logMethod='" + logMethod + '\'' +
                ", createTime=" + createTime +
                ", logLevel='" + logLevel + '\'' +
                ", logLine='" + logLine + '\'' +
                ", logMsg='" + logMsg + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}