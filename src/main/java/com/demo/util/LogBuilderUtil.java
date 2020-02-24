package com.demo.util;

import com.demo.common.constant.StatusConstant;
import com.demo.controller.log4j.ResLogController;
import com.demo.dto.response.BaseRespDto;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zje
 *
 */
public class LogBuilderUtil {

    //key和value中间的分隔符
    private static final String DEFAULT_PARAM_VALUE_SPLITTER = "=";

    //不通key之间的分割符号
    private static final String DEFAULT_PARAM_SPLITTER = "|";

    //“日志分段”分隔符
    private static final String DEFAULT_LOG_SPLITTER = " - ";

    public static Builder getBuilder(String className, String methodName, String bussinessDesc, String stepDesc, String key, String value) {
        return new Builder(className, methodName, bussinessDesc, stepDesc, key, value);
    }

    public static Builder getBuilder(String className, String methodName, String bussinessDesc, String stepDesc) {
        return new Builder(className, methodName, bussinessDesc, stepDesc, null, null);
    }

    public static Builder getBuilder(String methodName, String bussinessDesc, String stepDesc) {
        return new Builder(null, methodName, bussinessDesc, stepDesc, null, null);
    }
    /**
     * debug信息方法
     * @param modelName 模块名称
     * @param className 类名
     * @param methodName 方法名
     */
    public static void recordDebuggerLogs(String msg,String modelName,String className,String methodName){
        Logger logger = getLogger(modelName, className, methodName);
        logger.debug(msg);
    }
    /**
     * INFO信息方法
     * @param modelName 模块名称
     * @param className 类名
     * @param methodName 方法名
     */
    public static void recordInfoLogs(String msg,String modelName,String className,String methodName){
        Logger logger = getLogger(modelName, className, methodName);
        logger.info(msg);
    }
    /**
     * 警告信息方法
     * @param modelName 模块名称
     * @param className 类名
     * @param methodName 方法名
     */
    public static void recordWarnLogs(String msg,String modelName,String className,String methodName){
        Logger logger = getLogger(modelName, className, methodName);
        logger.warn(msg);
    }
    private static Logger getLogger(String modelName, String className, String methodName) {
        PropertyConfigurator.configure(LogBuilderUtil.class.getResource("/").getPath()+"log4j.properties");
        Logger logger = LoggerFactory.getLogger(modelName.trim());
        MDC.put("exception", "");
        MDC.put("class", className);
        MDC.put("method", methodName);
        return logger;
    }
    /**
     * 错误信息方法
     * @param modelName 模块名称
     * @param className 类名
     * @param methodName 方法名
     */
    public static void recordErrorLogs(Throwable e,String modelName,String className,String methodName){
        PropertyConfigurator.configure(LogBuilderUtil.class.getResource("/").getPath()+"log4j.properties");
        Logger logger = LoggerFactory.getLogger(modelName.trim());
        MDC.put("exception", e.getClass().getName());
        MDC.put("class", className);
        MDC.put("method", methodName);
        String eStr = ExceptionUtil.getTrace(e);
        logger.error(eStr,e);
    }

    public static class Builder {
        private String className;//类名
        private String methodName;//方法名
        private String bussinessDesc;//业务描述
        private String stepDesc;//步骤/状态
        private String key;//关键字key
        private String value;//关键字value
        private StringBuilder params;//相关参数

        private Builder() {
        }

        public Builder(String className, String methodName, String bussinessDesc, String stepDesc, String key, String value) {
            this.setClassName(className);
            this.setMethodName(methodName);
            this.setBussinessDesc(bussinessDesc);
            this.setStepDesc(stepDesc);
            this.setKey(key);
            this.setValue(value);
            this.setParams(new StringBuilder());
        }

        public Builder appendParam(String paramName, String paramValue) {
            if (null == this.getParams()) {
                this.setParams(new StringBuilder());
            }
            if (this.getParams().length() > 0) {
                this.getParams().append(DEFAULT_PARAM_SPLITTER);
            }
            this.getParams().append(paramName).append(DEFAULT_PARAM_VALUE_SPLITTER).append(paramValue);
            return this;
        }

        public String build() {
            return this.toString();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (null != this.getClassName() && this.getClassName().length() > 0) {
                //判断类名是不是为空
                sb.append(this.getClassName()).append(DEFAULT_LOG_SPLITTER);
            }

            sb.append(this.getMethodName()).
                    append(DEFAULT_LOG_SPLITTER).append(this.getBussinessDesc()).
                    append(DEFAULT_LOG_SPLITTER).append(this.getStepDesc());

            if (null != this.getKey() && this.getKey().length() > 0) {
                sb.append(DEFAULT_LOG_SPLITTER).append(this.getKey()).
                        append(DEFAULT_LOG_SPLITTER).append(this.getValue());
            }

            if (null != this.getParams() && this.getParams().length() > 0) {
                sb.append(DEFAULT_PARAM_SPLITTER).append(this.getParams());
            }
            return sb.toString();
        }


        public Builder appendParam(String param, boolean value) {
            return appendParam(param, String.valueOf(value));
        }

        public Builder appendParam(String param, int value) {
            return appendParam(param, String.valueOf(value));
        }

        public Builder appendParam(String param, Integer value) {
            return appendParam(param, String.valueOf(value));
        }

        public Builder appendParam(String param, long value) {
            return appendParam(param, String.valueOf(value));
        }

        public Builder appendParam(String param, Long value) {
            return appendParam(param, String.valueOf(value));
        }

        public Builder appendParam(String param, float value) {
            return appendParam(param, String.valueOf(value));
        }

        public Builder appendParam(String param, double value) {
            return appendParam(param, String.valueOf(value));
        }

        public Builder appendParam(String param, Object o) {
            return appendParam(param, String.valueOf(o));
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getBussinessDesc() {
            return bussinessDesc;
        }

        public void setBussinessDesc(String bussinessDesc) {
            this.bussinessDesc = bussinessDesc;
        }

        public String getStepDesc() {
            return stepDesc;
        }

        public void setStepDesc(String stepDesc) {
            this.stepDesc = stepDesc;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public StringBuilder getParams() {
            return params;
        }

        public void setParams(StringBuilder params) {
            this.params = params;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }


    }

}
