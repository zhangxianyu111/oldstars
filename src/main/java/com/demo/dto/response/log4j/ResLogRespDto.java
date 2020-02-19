package com.demo.dto.response.log4j;

import com.demo.dto.response.BaseRespDto;
import com.demo.pojo.log4j.ResWarn;

import java.util.List;

public class ResLogRespDto extends BaseRespDto {
    //错误日志数量
    private Long errCount;
    //警告日志数量
    private Long warnCount;
    //警告日志
    private List<ResWarn> warnList;

    public Long getErrCount() {
        return errCount;
    }

    public void setErrCount(Long errCount) {
        this.errCount = errCount;
    }

    public Long getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(Long warnCount) {
        this.warnCount = warnCount;
    }

    public List<ResWarn> getWarnList() {
        return warnList;
    }

    public void setWarnList(List<ResWarn> warnList) {
        this.warnList = warnList;
    }

    @Override
    public String toString() {
        return "ResLogRespDto{" +
                "errCount=" + errCount +
                ", warnCount=" + warnCount +
                ", warnList=" + warnList +
                '}';
    }
}
