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
    private String level;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
}
