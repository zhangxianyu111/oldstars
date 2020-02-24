package com.demo.dto.response.log4j;

import com.demo.common.Statistics;
import com.demo.dto.request.log4j.ResLogReqDto;
import com.demo.dto.response.BaseRespDto;
import com.demo.pojo.log4j.ResWarn;

import java.util.List;
import java.util.Map;

public class ResLogRespDto extends BaseRespDto {
    //错误日志数量
    private Long errCount;
    //警告日志数量
    private Long warnCount;
    //警告日志
    private List<ResWarn> warnList;
    //查询条件
    private ResLogReqDto resLogReqDto;
    //配置的模块
    private List<Statistics.Item> logModules;

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

    public ResLogReqDto getResLogReqDto() {
        return resLogReqDto;
    }

    public void setResLogReqDto(ResLogReqDto resLogReqDto) {
        this.resLogReqDto = resLogReqDto;
    }

    public List<Statistics.Item> getLogModules() {
        return logModules;
    }

    public void setLogModules(List<Statistics.Item> logModules) {
        this.logModules = logModules;
    }

    @Override
    public String toString() {
        return "ResLogRespDto{" +
                "errCount=" + errCount +
                ", warnCount=" + warnCount +
                ", warnList=" + warnList +
                ", resLogReqDto=" + resLogReqDto +
                ", logModules=" + logModules +
                '}';
    }
}
