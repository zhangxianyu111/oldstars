package com.demo.dto.response.log4j;

import com.demo.dto.response.BaseRespDto;

public class ResWarnRespDto<T> extends BaseRespDto {

    //未处理数量
    private Long untreatedCount;
    //查询条件
    private T reqDto;

    public T getReqDto() {
        return reqDto;
    }

    public void setReqDto(T reqDto) {
        this.reqDto = reqDto;
    }

    public Long getUntreatedCount() {
        return untreatedCount;
    }

    public void setUntreatedCount(Long untreatedCount) {
        this.untreatedCount = untreatedCount;
    }

    @Override
    public String toString() {
        return "ResWarnRespDto{" +
                "untreatedCount=" + untreatedCount +
                ", reqDto=" + reqDto +
                '}';
    }
}
