package com.demo.dto.log;

import org.apache.log4j.Level;

/**
 * 日志信息
 * @author zje
 */
public class LogInfoVO {
    /**logger 名称**/
    private String name;
    /**logger 級別**/
    private Level level;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Level getLevel() {
        return level;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
}
