package com.demo.controller.log4j;

import com.demo.common.StatusConstant;
import com.demo.dto.request.log4j.ResLogReqDto;
import com.demo.dto.response.BaseRespDto;
import com.demo.service.log4j.ResLogService;
import com.demo.util.LogBuilderUtil;
import org.apache.commons.lang3.StringUtils;
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
    public BaseRespDto getAllLogs(@RequestBody ResLogReqDto reqDto){
        LOGGER.info(LogBuilderUtil.getBuilder("getAllLogs","查询日志信息","开始").appendParam("参数",reqDto).build());
        BaseRespDto baseRespDto = new BaseRespDto();
        try {
            baseRespDto = resLogService.selectAllByPage(reqDto.getMap(),baseRespDto);
            baseRespDto.setCode(StatusConstant.SUCCESS);
            LOGGER.info(LogBuilderUtil.getBuilder("selectLogs","查询日志信息","结束").appendParam("响应结果",baseRespDto).build());
        }catch(Exception e){
            baseRespDto.setCode(StatusConstant.FAIL);
            LOGGER.error(LogBuilderUtil.getBuilder("selectLogs","查询日志信息","系统异常")
                    .build(),e);
        }
        return baseRespDto;
    }
    /**
     * 批量下载
     */
    @RequestMapping(value = "batchDownLoad",method = RequestMethod.GET)
    public void banthDownLoad(String ids, HttpServletResponse response){
        LOGGER.info(LogBuilderUtil.getBuilder("banthDownLoad","批量日志下载","开始").appendParam("参数",ids).build());
        if (StringUtils.isBlank(ids)|| StringUtils.isBlank(ids.trim())){
            LOGGER.info(LogBuilderUtil.getBuilder("banthDownLoad","批量日志下载","参数错误").build());
            return;
        }
        try {
            resLogService.batchDownLoad(ids,response);
            LOGGER.info(LogBuilderUtil.getBuilder("banthDownLoad","批量日志下载","结束").build());
        }catch (IOException e){
            LOGGER.error(LogBuilderUtil.getBuilder("banthDownLoad","批量日志下载",e.getMessage())
                    .build(),e);
        }
    }

    /**
     * 下载错误日志
     * @param id
     * @param response
     */
    public void downLoad(@RequestParam("id") Long id,HttpServletResponse response){
        LOGGER.info(LogBuilderUtil.getBuilder("downLoad","错误日志下载","开始").appendParam("参数",id).build());
        try {
            resLogService.downLoad(id,response);
            LOGGER.info(LogBuilderUtil.getBuilder("downLoad","错误日志下载","结束").build());
        }catch (Exception e){
            LOGGER.error(LogBuilderUtil.getBuilder("downLoad","错误日志下载",e.getMessage())
                    .build(),e);
        }
    }

}
