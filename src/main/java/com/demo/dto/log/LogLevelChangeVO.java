package com.demo.dto.log;

import org.apache.log4j.Level;

/**
 * 更改日志级别
 * @author zje
 */
public class LogLevelChangeVO {
    /**全名**/
    private String fullName;
    /**更改前的级别**/
    private String beforeLevel;
    /**更改后的级别**/
    private String afterLevel;
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getBeforeLevel() {
        return beforeLevel;
    }
    public void setBeforeLevel(String beforeLevel) {
        this.beforeLevel = beforeLevel;
    }
    public String getAfterLevel() {
        return afterLevel;
    }
    public void setAfterLevel(String afterLevel) {
        this.afterLevel = afterLevel;
    }
}
