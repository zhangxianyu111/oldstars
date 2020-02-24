package com.demo.controller.log4j;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.constant.StatusConstant;
import com.demo.dto.request.log4j.ResWarnReqDto;
import com.demo.dto.response.BaseRespDto;
import com.demo.dto.response.log4j.ResWarnRespDto;
import com.demo.service.log4j.ResWarnService;
import com.demo.util.LogBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/warn")
public class ResWarnController {

    @Resource
    private ResWarnService resWarnService;


    /**
     * 查看
     * @param reqDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllWarn",method = RequestMethod.POST)
    public String getAllWarn(@RequestBody ResWarnReqDto reqDto){
        LogBuilderUtil.recordInfoLogs(
                LogBuilderUtil.getBuilder("getAllWarn","查询告警信息","开始").appendParam("参数",reqDto).build()
                ,"resWarn",ResWarnController.class.getName(),"getAllWarn");
        ResWarnRespDto baseRespDto = new ResWarnRespDto();
        try {
            baseRespDto = resWarnService.selectAllByPage(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LogBuilderUtil.recordInfoLogs(
                    LogBuilderUtil.getBuilder("getAllWarn","查询告警信息","结束").appendParam("响应结果",baseRespDto).build()
                    ,"resWarn",ResWarnController.class.getName(),"getAllWarn");
        }catch(Exception e){
            LogBuilderUtil.recordErrorLogs(e,"resWarn",ResWarnController.class.getName(),"getAllWarn");
            baseRespDto.setCode(StatusConstant.FAIL);
        }
        return JSONObject.toJSONString(baseRespDto);
    }
    @ResponseBody
    @RequestMapping(value = "getModule",method = RequestMethod.POST)
    public BaseRespDto getModule(@RequestBody ResWarnReqDto reqDto){
        LogBuilderUtil.recordInfoLogs(
                LogBuilderUtil.getBuilder("getModule","查询模块信息","开始").appendParam("参数",reqDto).build()
                ,"resWarn",ResWarnController.class.getName(),"getModule");
        ResWarnRespDto baseRespDto = new ResWarnRespDto();
        try {
            baseRespDto = resWarnService.selectModule(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LogBuilderUtil.recordInfoLogs(
                    LogBuilderUtil.getBuilder("getModule","查询模块信息","结束").appendParam("响应结果",baseRespDto).build()
                    ,"resWarn",ResWarnController.class.getName(),"getModule");
        }catch(Exception e){
            LogBuilderUtil.recordErrorLogs(e,"resWarn",ResWarnController.class.getName(),"getModule");
            baseRespDto.setCode(StatusConstant.FAIL);
        }
        return baseRespDto;
    }
    /**
     * 处理 & 批量处理
     */
    @ResponseBody
    @RequestMapping(value = "/handleWarn",method = RequestMethod.POST)
    public BaseRespDto handleWarn(@RequestBody ResWarnReqDto reqDto){
        LogBuilderUtil.recordInfoLogs(
                LogBuilderUtil.getBuilder("handleWarn","处理或批量处理告警信息","开始").appendParam("参数",reqDto).build()
                ,"resWarn",ResWarnController.class.getName(),"handleWarn");
        ResWarnRespDto baseRespDto = new ResWarnRespDto();
        try {
            baseRespDto = resWarnService.handle(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LogBuilderUtil.recordInfoLogs(
                    LogBuilderUtil.getBuilder("handleWarn","处理或批量处理告警信息","结束").appendParam("响应结果",baseRespDto).build()
                    ,"resWarn",ResWarnController.class.getName(),"handleWarn");
        }catch(Exception e){
            LogBuilderUtil.recordErrorLogs(e,"resWarn",ResWarnController.class.getName(),"handleWarn");
            baseRespDto.setCode(StatusConstant.FAIL);
        }
        return baseRespDto;
    }

    /**
     * 查看
     */
    @ResponseBody
    @RequestMapping(value = "/seeWarn",method = RequestMethod.POST)
    public BaseRespDto seeWarn(@RequestParam("warnId") Long warnId){
        LogBuilderUtil.recordInfoLogs(
                LogBuilderUtil.getBuilder("seeWarn","查看告警信息","开始").appendParam("参数",warnId).build()
                ,"resWarn",ResWarnController.class.getName(),"seeWarn");
        ResWarnRespDto baseRespDto = new ResWarnRespDto();
        try {
            baseRespDto = resWarnService.seeWarn(warnId,baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LogBuilderUtil.recordInfoLogs(
                    LogBuilderUtil.getBuilder("seeWarn","查看告警信息","结束").appendParam("响应结果",baseRespDto).build()
                    ,"resWarn",ResWarnController.class.getName(),"seeWarn");
        }catch(Exception e){
            LogBuilderUtil.recordErrorLogs(e,"resWarn",ResWarnController.class.getName(),"seeWarn");
            baseRespDto.setCode(StatusConstant.FAIL);
        }
        return baseRespDto;
    }

}
