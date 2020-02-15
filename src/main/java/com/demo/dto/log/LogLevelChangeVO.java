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
    private Level beforeLevel;
    /**更改后的级别**/
    private Level afterLevel;
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Level getBeforeLevel() {
        return beforeLevel;
    }
    public void setBeforeLevel(Level beforeLevel) {
        this.beforeLevel = beforeLevel;
    }
    public Level getAfterLevel() {
        return afterLevel;
    }
    public void setAfterLevel(Level afterLevel) {
        this.afterLevel = afterLevel;
    }
}
