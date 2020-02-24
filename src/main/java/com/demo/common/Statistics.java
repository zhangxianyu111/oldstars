package com.demo.common;

import com.demo.util.SpringContextHolder;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "config")
public class Statistics {

    private String warnMsg;

    private Moudle moudle;

    public Statistics() {

    }

    public Statistics(String warnMsg,  Moudle moudle) {
        this.warnMsg = warnMsg;
        this.moudle = moudle;
    }
    @XmlElement(name = "warnmsg")
    public String getWarnMsg() {
        return warnMsg;
    }

    public void setWarnMsg(String warnMsg) {
        this.warnMsg = warnMsg;
    }

    @XmlElement(name = "moudle")
    public Moudle getMoudle() {
        return moudle;
    }

    public void setMoudle(Moudle moudle) {
        this.moudle = moudle;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "warnMsg='" + warnMsg + '\'' +
                ", moudle=" + moudle +
                '}';
    }
    @XmlRootElement(name = "moudle")
    public static class Moudle{

        private List<Item> items;

        public Moudle(){}
        public Moudle(List<Item> items) {
            this.items = items;
        }

        @XmlElement(name="item")
        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "Module{" +
                    "items=" + items +
                    '}';
        }
    }
    @XmlRootElement(name = "item")
    public static class Item{

        private String name;

        private String desc;

        private int warnTime;

        private Long warnCount;

        private Long errorCount;

        private int errorTime;

        public Item() {

        }
        public Item(String name, String desc, int warnTime, Long warnCount, Long errorCount, int errorTime) {
            this.name = name;
            this.desc = desc;
            this.warnTime = warnTime;
            this.warnCount = warnCount;
            this.errorCount = errorCount;
            this.errorTime = errorTime;
        }
        @XmlAttribute(name = "name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;

        }
        @XmlAttribute(name = "desc")
        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
        @XmlAttribute(name = "warntime")
        public int getWarnTime() {
            return warnTime;
        }

        public void setWarnTime(int warnTime) {
            this.warnTime = warnTime;
        }
        @XmlAttribute(name = "warncount")
        public Long getWarnCount() {
            return warnCount;
        }

        public void setWarnCount(Long warnCount) {
            this.warnCount = warnCount;
        }
        @XmlAttribute(name = "errorcount")
        public Long getErrorCount() {
            return errorCount;
        }

        public void setErrorCount(Long errorCount) {
            this.errorCount = errorCount;
        }
        @XmlAttribute(name = "errortime")
        public int getErrorTime() {
            return errorTime;
        }

        public void setErrorTime(int errorTime) {
            this.errorTime = errorTime;
        }

        @Override
        public String toString() {
            return "Module{" +
                    "name='" + name + '\'' +
                    ", desc='" + desc + '\'' +
                    ", warnTime=" + warnTime +
                    ", warnCount=" + warnCount +
                    ", errorCount=" + errorCount +
                    ", errorTime=" + errorTime +
                    '}';
        }
    }
}
