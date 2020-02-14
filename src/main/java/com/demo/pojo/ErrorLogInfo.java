package com.demo.pojo;

import java.util.Date;
/**
 *
 * @author zje
 *
 */
public class ErrorLogInfo {

    public Long id;
    //模块名称
    public String module;
    //日志标题
    public String logTitle;
    //日志详情
    public String logDesc;
    //日志报错时间
    public Date logDate;
    //创建时间
    public Date createDate;

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

    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ErrorLogInfo{" +
                "id=" + id +
                ", module='" + module + '\'' +
                ", logTitle='" + logTitle + '\'' +
                ", logDesc='" + logDesc + '\'' +
                ", logDate=" + logDate +
                ", createDate=" + createDate +
                '}';
    }
}
