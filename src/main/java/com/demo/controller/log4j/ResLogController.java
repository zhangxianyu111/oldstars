package com.demo.controller.log4j;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.constant.StatusConstant;
import com.demo.dto.request.log4j.ResLogReqDto;
import com.demo.dto.response.BaseRespDto;
import com.demo.dto.response.log4j.ResLogRespDto;
import com.demo.service.log4j.ResLogService;
import com.demo.util.LogBuilderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/log4j")
public class ResLogController {
    @Resource
    private ResLogService resLogService;
    /**
     * 查询
     */
    @ResponseBody
    @RequestMapping(value = "/getAllLogs",method = RequestMethod.POST)
    public String getAllLogs(@RequestBody ResLogReqDto reqDto){
        LogBuilderUtil.recordInfoLogs(LogBuilderUtil.getBuilder("getAllLogs","查询日志信息","开始").appendParam("参数",reqDto).build()
        ,"resLog",ResLogController.class.getName(),"getAllLogs");
        ResLogRespDto baseRespDto = new ResLogRespDto();

        try {
            baseRespDto = resLogService.selectAllByPage(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LogBuilderUtil.recordInfoLogs(
                    LogBuilderUtil.getBuilder("selectLogs","查询日志信息","结束").appendParam("响应结果",baseRespDto).build()
                    ,"resLog",ResLogController.class.getName(),"getAllLogs");
        }catch(Exception e){
            LogBuilderUtil.recordErrorLogs(e,"resLog",ResLogController.class.getName(),"getAllLogs");
            baseRespDto.setCode(StatusConstant.FAIL);
        }
        return JSONObject.toJSONString(baseRespDto);
    }
    @ResponseBody
    @RequestMapping(value = "/selectModule",method = RequestMethod.POST)
    public BaseRespDto selectModule(@RequestBody ResLogReqDto reqDto){
        LogBuilderUtil.recordInfoLogs(
                LogBuilderUtil.getBuilder("selectModule","查询模块信息","开始").appendParam("参数",reqDto).build()
                ,"resLog",ResLogController.class.getName(),"selectModule");
        ResLogRespDto baseRespDto = new ResLogRespDto();
        try {
            baseRespDto = resLogService.selectModule(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LogBuilderUtil.recordInfoLogs(
                    LogBuilderUtil.getBuilder("selectModule","查询模块信息","结束").appendParam("响应结果",baseRespDto).build()
                    ,"resLog",ResLogController.class.getName(),"selectModule");
        }catch (Exception e){
            LogBuilderUtil.recordErrorLogs(e,"resLog",ResLogController.class.getName(),"selectModule");
            baseRespDto.setCode(StatusConstant.FAIL);
        }
        return  baseRespDto;
    }

    /**
     * 批量下载
     */
    @RequestMapping(value = "/batchDownLoad",method = RequestMethod.GET)
    public void banthDownLoad(@RequestParam("ids") String ids, HttpServletResponse response){
        LogBuilderUtil.recordInfoLogs(
                LogBuilderUtil.getBuilder("banthDownLoad","批量日志下载","开始").appendParam("参数",ids).build()
                ,"resLog",ResLogController.class.getName(),"selectModule");
        if (StringUtils.isBlank(ids)|| StringUtils.isBlank(ids.trim())){
            LogBuilderUtil.recordWarnLogs("参数错误","resLog",ResLogController.class.getName(),"banthDownLoad");
            return;
        }
        try {
            resLogService.batchDownLoad(ids,response);
            LogBuilderUtil.recordInfoLogs(
                    LogBuilderUtil.getBuilder("batchDownLoad","批量日志下载","结束").build()
                    ,"resLog",ResLogController.class.getName(),"batchDownLoad");
        }catch (IOException e){
            LogBuilderUtil.recordErrorLogs(e,"resLog",ResLogController.class.getName(),"banthDownLoad");
        }
    }

    /**
     * 下载错误日志
     * @param id
     * @param response
     */
    @RequestMapping(value = "/downLoad",method = RequestMethod.GET)
    public void downLoad(@RequestParam("id") Long id,HttpServletResponse response){
        LogBuilderUtil.recordInfoLogs(
                LogBuilderUtil.getBuilder("downLoad","错误日志下载","开始").appendParam("参数",id).build()
                ,"resLog",ResLogController.class.getName(),"downLoad");
        try {
            resLogService.downLoad(id,response);
            LogBuilderUtil.recordInfoLogs(
                    LogBuilderUtil.getBuilder("downLoad","错误日志下载","结束").appendParam("参数",id).build()
                    ,"resLog",ResLogController.class.getName(),"downLoad");
        }catch (Exception e){
            LogBuilderUtil.recordErrorLogs(e,"resLog",ResLogController.class.getName(),"downLoad");
        }
    }

}
