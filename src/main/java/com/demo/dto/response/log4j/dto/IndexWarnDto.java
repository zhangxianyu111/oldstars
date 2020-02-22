package com.demo.dto.response.log4j.dto;

public class IndexWarnDto {

    private Long warnId;

    private String warnClass;

    private String content;

    public Long getWarnId() {
        return warnId;
    }

    public void setWarnId(Long warnId) {
        this.warnId = warnId;
    }

    public String getWarnClass() {
        return warnClass;
    }

    public void setWarnClass(String warnClass) {
        this.warnClass = warnClass;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "IndexWarnDto{" +
                "warnId=" + warnId +
                ", warnClass='" + warnClass + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
