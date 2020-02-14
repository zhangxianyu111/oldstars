package com.demo.pojo;
/**
 *
 * @author zje
 *
 */
public class LogPointer {

    private Long pointer;

    public Long getPointer() {
        return pointer;
    }

    public void setPointer(Long pointer) {
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return "LogPointer{" +
                "pointer=" + pointer +
                '}';
    }
}
