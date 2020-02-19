package com.demo.controller.log4j;

import com.demo.common.StatusConstant;
import com.demo.dto.request.log4j.ResWarnReqDto;
import com.demo.dto.response.BaseRespDto;
import com.demo.dto.response.log4j.ResLogRespDto;
import com.demo.dto.response.log4j.ResWarnRespDto;
import com.demo.service.log4j.ResWarnService;
import com.demo.util.ExceptionUtil;
import com.demo.util.LogBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/warn")
public class ResWarnController {

    private final Logger LOGGER = LoggerFactory.getLogger(ResLogController.class);
    @Resource
    private ResWarnService resWarnService;

    /**
     * 查看
     * @param reqDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllWarn",method = RequestMethod.POST)
    public BaseRespDto getAllWarn(@RequestParam ResWarnReqDto reqDto){
        LOGGER.info(LogBuilderUtil.getBuilder("getAllWarn","查询告警信息","开始").appendParam("参数",reqDto).build());
        ResWarnRespDto baseRespDto = new ResWarnRespDto();
        try {
            baseRespDto = resWarnService.selectAllByPage(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LOGGER.info(LogBuilderUtil.getBuilder("getAllWarn","查询日志信息","结束").appendParam("响应结果",baseRespDto).build());
        }catch(Exception e){
            LogBuilderUtil.failToLog(baseRespDto, e,LOGGER);
        }
        return baseRespDto;
    }
    /**
     * 处理 & 批量处理
     */
    @ResponseBody
    @RequestMapping(value = "/handleWarn",method = RequestMethod.POST)
    public BaseRespDto handleWarn(@RequestBody ResWarnReqDto reqDto){
        LOGGER.info(LogBuilderUtil.getBuilder("handleWarn","处理或批量处理告警信息","开始").appendParam("参数",reqDto).build());
        ResWarnRespDto baseRespDto = new ResWarnRespDto();
        try {
            baseRespDto = resWarnService.handle(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LOGGER.info(LogBuilderUtil.getBuilder("handleWarn","处理或批量处理告警信息","结束").appendParam("响应结果",baseRespDto).build());
        }catch(Exception e){
            LogBuilderUtil.failToLog(baseRespDto, e,LOGGER);
        }
        return baseRespDto;
    }

    /**
     * 查看
     */
    @ResponseBody
    @RequestMapping(value = "/seeWarn",method = RequestMethod.POST)
    public BaseRespDto seeWarn(@RequestBody ResWarnReqDto reqDto){
        LOGGER.info(LogBuilderUtil.getBuilder("seeWarn","查看告警信息","开始").appendParam("参数",reqDto).build());
        ResWarnRespDto baseRespDto = new ResWarnRespDto();
        try {
            baseRespDto = resWarnService.seeWarn(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LOGGER.info(LogBuilderUtil.getBuilder("seeWarn","查看告警信息","结束").appendParam("响应结果",baseRespDto).build());
        }catch(Exception e){
            LogBuilderUtil.failToLog(baseRespDto, e,LOGGER);
        }
        return baseRespDto;
    }

}
