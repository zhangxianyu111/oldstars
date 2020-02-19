package com.demo.dto.response;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.constant.StatusConstant;

import java.util.List;
/**
 *
 * @author zje
 *
 */
public class BaseRespDto<T,K> {

    private String code;

    private String msg;

    private Long count;

    private K singleData;

    private List<T> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public K getSingleData() {
        return singleData;
    }

    public void setSingleData(K singleData) {
        this.singleData = singleData;
    }

    public static String getFailInstance(String msg){
        BaseRespDto baseRespDto = new BaseRespDto();
        baseRespDto.setCode(StatusConstant.FAIL);
        baseRespDto.setMsg(msg);
        return JSONObject.toJSONString(baseRespDto);
    }
    public static <K> String getSuccessInstance(K data){
        BaseRespDto baseRespDto = new BaseRespDto();
        baseRespDto.setCode(StatusConstant.SUCCESS);
        baseRespDto.setSingleData(data);
        return JSONObject.toJSONString(baseRespDto);
    }
    public static <T> String getSuccessListInstance(List<T> data){
        BaseRespDto baseRespDto = new BaseRespDto();
        baseRespDto.setCode(StatusConstant.SUCCESS);
        baseRespDto.setData(data);
        return JSONObject.toJSONString(baseRespDto);
    }


}
