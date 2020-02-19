package com.demo.service.log4j.impl;

import com.demo.common.constant.StatusConstant;
import com.demo.dao.log4j.ResWarnMapper;
import com.demo.dto.response.log4j.ResWarnRespDto;
import com.demo.pojo.log4j.ResLog;
import com.demo.pojo.log4j.ResWarn;
import com.demo.service.log4j.ResWarnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("resWarnService")
public class ResWarnServiceImpl implements ResWarnService {
    @Resource
    private ResWarnMapper resWarnDao;
    @Override
    public ResWarnRespDto selectAllByPage(Map paramMap, ResWarnRespDto respDto) {

        PageHelper.startPage(Integer.valueOf(paramMap.get("page").toString()),Integer.valueOf(paramMap.get("limit").toString()));
        List<ResLog> logs = resWarnDao.selectAll(paramMap);
        PageInfo pageInfo = new PageInfo(logs);
        long total = pageInfo.getTotal();
        respDto.setCount(total);
        respDto.setSingleData(pageInfo);
        //查询处理 和 未处理 数量
        paramMap.put("warnStatus",0);//未处理
        Long untreatedCount = resWarnDao.selectAllCount(paramMap);
        paramMap.put("warnStatus",1);//已处理
        Long processedCount = resWarnDao.selectAllCount(paramMap);
        respDto.setUntreatedCount(untreatedCount == null?0L:untreatedCount);
        respDto.setProcessedCount(processedCount == null?0L:processedCount);
        return respDto;
    }

    @Override
    public ResWarnRespDto handle(Map map, ResWarnRespDto baseRespDto) {
        Object id = map.get("id");
        Object ids = map.get("ids");
        if (id == null && ids == null){
            baseRespDto.setCode(StatusConstant.FAIL);
            baseRespDto.setMsg("参数错误");
        }
        map.put("handleUser","");//待新增
        resWarnDao.handle(map);
        baseRespDto.setCode(StatusConstant.SUCCESS);
        baseRespDto.setMsg("处理成功");
        return baseRespDto;
    }

    @Override
    public ResWarnRespDto seeWarn(Map map, ResWarnRespDto baseRespDto) {
        Object id = map.get("id");
        if (id == null){
            baseRespDto.setCode(StatusConstant.FAIL);
            baseRespDto.setMsg("参数错误");
        }
        ResWarn resWarn = resWarnDao.seeWarn(map);
        if (resWarn == null){
            baseRespDto.setCode(StatusConstant.FAIL);
            baseRespDto.setMsg("未查到该信息");
        }
        baseRespDto.setCode(StatusConstant.SUCCESS);
        baseRespDto.setSingleData(resWarn);
        return baseRespDto;
    }
}