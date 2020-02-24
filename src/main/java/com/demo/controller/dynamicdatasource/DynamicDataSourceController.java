package com.demo.controller.dynamicdatasource;

import com.demo.common.constant.StatusConstant;
import com.demo.controller.IndexController;
import com.demo.dto.response.BaseRespDto;
import com.demo.dynamicdatasource.DynamicDataSource;
import com.demo.util.ExceptionUtil;
import com.demo.util.LogBuilderUtil;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/datasource")
public class DynamicDataSourceController {

    private final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceController.class);
    /**
     * 切换数据库
     * @param dataSource
     */
    @RequestMapping(value = "/switchDataSource" ,method = RequestMethod.POST)
    public BaseRespDto switchDataSource(@RequestParam("dataSoruce") String dataSource){
        LOGGER.info(LogBuilderUtil.getBuilder("switchDataSource","切换数据源","开始")
        .appendParam("参数",dataSource).build());
        BaseRespDto respDto = new BaseRespDto();
        try {
            DynamicDataSource.clearCustomerDBType();
            DynamicDataSource.setCustomerDBType(dataSource);
            respDto.setMsg("切换"+dataSource+"数据源成功");
            respDto.setCode(StatusConstant.SUCCESS);
        }catch (Exception e){
            MDC.put("exception", e.getClass().getName());
            respDto.setMsg(e.getClass().getName());
            respDto.setCode(StatusConstant.FAIL);
            String eStr = ExceptionUtil.getTrace(e);
            LOGGER.error(eStr,e);
        }
    return  respDto;
    }
}
