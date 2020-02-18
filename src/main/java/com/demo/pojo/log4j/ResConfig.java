package com.demo.pojo.log4j;

import java.util.Date;

public class ResConfig {
    private int cfgId;
    //日志级别 3:ERROR、2:WARN、1:INFO、0:DEBUG
    private int cfgLevel;
    //报错模块
    private String cfgClass;
    //警告错误类型 例如空指针等
    private String cfgErrType;
    //多长时间
    private int cfgLimit;
    //单位 0:分钟 1:小时  2:天 4:月 5:年
    private int cfgUnit;
    //告警次数
    private int cfgCount;
    //创建时间
    private Date createTime;

    public int getCfgId() {
        return cfgId;
    }

    public void setCfgId(int cfgId) {
        this.cfgId = cfgId;
    }

    public int getCfgLevel() {
        return cfgLevel;
    }

    public void setCfgLevel(int cfgLevel) {
        this.cfgLevel = cfgLevel;
    }

    public String getCfgClass() {
        return cfgClass;
    }

    public void setCfgClass(String cfgClass) {
        this.cfgClass = cfgClass;
    }

    public String getCfgErrType() {
        return cfgErrType;
    }

    public void setCfgErrType(String cfgErrType) {
        this.cfgErrType = cfgErrType;
    }

    public int getCfgLimit() {
        return cfgLimit;
    }

    public void setCfgLimit(int cfgLimit) {
        this.cfgLimit = cfgLimit;
    }

    public int getCfgUnit() {
        return cfgUnit;
    }

    public void setCfgUnit(int cfgUnit) {
        this.cfgUnit = cfgUnit;
    }

    public int getCfgCount() {
        return cfgCount;
    }

    public void setCfgCount(int cfgCount) {
        this.cfgCount = cfgCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ResConfig{" +
                "cfgId=" + cfgId +
                ", cfgLevel=" + cfgLevel +
                ", cfgClass='" + cfgClass + '\'' +
                ", cfgErrType='" + cfgErrType + '\'' +
                ", cfgLimit=" + cfgLimit +
                ", cfgUnit=" + cfgUnit +
                ", cfgCount=" + cfgCount +
                ", createTime=" + createTime +
                '}';
    }
}
