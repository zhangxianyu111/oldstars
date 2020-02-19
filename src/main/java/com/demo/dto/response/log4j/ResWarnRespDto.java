package com.demo.dto.response.log4j;

import com.demo.dto.response.BaseRespDto;

public class ResWarnRespDto extends BaseRespDto {

    //未处理数量
    private Long untreatedCount;
    //已处理数量
    private Long processedCount;

    public Long getUntreatedCount() {
        return untreatedCount;
    }

    public void setUntreatedCount(Long untreatedCount) {
        this.untreatedCount = untreatedCount;
    }

    public Long getProcessedCount() {
        return processedCount;
    }

    public void setProcessedCount(Long processedCount) {
        this.processedCount = processedCount;
    }

    @Override
    public String toString() {
        return "ResWarnRespDto{" +
                "untreatedCount=" + untreatedCount +
                ", processedCount=" + processedCount +
                '}';
    }
}
