package com.demo.controller.log4j;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.constant.StatusConstant;
import com.demo.dto.request.log4j.ResLogReqDto;
import com.demo.dto.response.BaseRespDto;
import com.demo.dto.response.log4j.ResLogRespDto;
import com.demo.service.log4j.ResLogService;
import com.demo.util.ExceptionUtil;
import com.demo.util.LogBuilderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/log4j")
public class ResLogController {
    private final Logger LOGGER = LoggerFactory.getLogger(ResLogController.class);
    @Resource
    private ResLogService resLogService;
    /**
     * 查询
     */
    @ResponseBody
    @RequestMapping(value = "/getAllLogs",method = RequestMethod.POST)
    public String getAllLogs(@RequestBody ResLogReqDto reqDto){
        LOGGER.info(LogBuilderUtil.getBuilder("getAllLogs","查询日志信息","开始").appendParam("参数",reqDto).build());
        ResLogRespDto baseRespDto = new ResLogRespDto();

        try {
            baseRespDto = resLogService.selectAllByPage(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            //baseRespDto.setResLogReqDto(reqDto);
            LOGGER.info(LogBuilderUtil.getBuilder("selectLogs","查询日志信息","结束").appendParam("响应结果",baseRespDto).build());
        }catch(Exception e){
            MDC.put("exception", e.getClass().getName());
            baseRespDto.setCode(StatusConstant.FAIL);
            String eStr = ExceptionUtil.getTrace(e);
            LOGGER.error(eStr,e);
        }
        return JSONObject.toJSONString(baseRespDto);
    }
    @ResponseBody
    @RequestMapping(value = "/selectModule",method = RequestMethod.POST)
    public BaseRespDto selectModule(@RequestBody ResLogReqDto reqDto){
        ResLogRespDto baseRespDto = new ResLogRespDto();
        try {
            baseRespDto = resLogService.selectModule(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
        }catch (Exception e){
            MDC.put("exception", e.getClass().getName());
            baseRespDto.setCode(StatusConstant.FAIL);
            String eStr = ExceptionUtil.getTrace(e);
            LOGGER.error(eStr,e);
        }
        return  baseRespDto;
    }

    /**
     * 批量下载
     */
    @RequestMapping(value = "/batchDownLoad",method = RequestMethod.GET)
    public void banthDownLoad(@RequestParam("ids") String ids, HttpServletResponse response){
        LOGGER.info(LogBuilderUtil.getBuilder("banthDownLoad","批量日志下载","开始").appendParam("参数",ids).build());
        if (StringUtils.isBlank(ids)|| StringUtils.isBlank(ids.trim())){
            LOGGER.info(LogBuilderUtil.getBuilder("banthDownLoad","批量日志下载","参数错误").build());
            return;
        }
        try {
            resLogService.batchDownLoad(ids,response);
            LOGGER.info(LogBuilderUtil.getBuilder("banthDownLoad","批量日志下载","结束").build());
        }catch (IOException e){
            MDC.put("exception", e.getClass().getName());
            String eStr = ExceptionUtil.getTrace(e);
            LOGGER.error(eStr,e);
        }
    }

    /**
     * 下载错误日志
     * @param id
     * @param response
     */
    @RequestMapping(value = "/downLoad",method = RequestMethod.GET)
    public void downLoad(@RequestParam("id") Long id,HttpServletResponse response){
        LOGGER.info(LogBuilderUtil.getBuilder("downLoad","错误日志下载","开始").appendParam("参数",id).build());
        try {
            resLogService.downLoad(id,response);
            LOGGER.info(LogBuilderUtil.getBuilder("downLoad","错误日志下载","结束").build());
        }catch (Exception e){
            MDC.put("exception", e.getClass().getName());
            String eStr = ExceptionUtil.getTrace(e);
            LOGGER.error(eStr,e);
        }
    }

}
