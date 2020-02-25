package com.demo.service.log4j.impl;

import com.demo.common.constant.StatusConstant;
import com.demo.dao.log4j.ResWarnMapper;
import com.demo.dto.response.log4j.ResWarnRespDto;
import com.demo.pojo.log4j.ResWarn;
import com.demo.service.log4j.ResWarnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ResWarnServiceImpl implements ResWarnService {
    @Resource
    private ResWarnMapper resWarnDao;
    @Override
    public ResWarnRespDto selectAllByPage(Map paramMap, ResWarnRespDto respDto) {

        PageHelper.startPage(Integer.valueOf(paramMap.get("page").toString()),Integer.valueOf(paramMap.get("limit").toString()));
        List<ResWarn> logs = resWarnDao.selectAll(paramMap);
        PageInfo pageInfo = new PageInfo(logs);
        long total = pageInfo.getTotal();
        respDto.setCount(total);
        respDto.setData(logs);
        return respDto;
    }

    @Override
    public ResWarnRespDto handle(Map<String,Object> paramMap, ResWarnRespDto baseRespDto) {
        Object id = paramMap.get("warnId");
        Object ids = paramMap.get("warnIds");
        if (id == null && ids == null){
            baseRespDto.setCode(StatusConstant.FAIL);
            baseRespDto.setMsg("参数错误");
            return baseRespDto;
        }else{
            List<ResWarn> resWarns = new ArrayList<>();
            ResWarn resWarn;
            if (ids != null){
                String[] valueArr = String.valueOf(ids).split(",");
                for (int i = 0; i < valueArr.length; i++) {
                    resWarn = new ResWarn();
                    resWarn.setWarnMsg("批量处理");
                    resWarn.setWarnStatus(1);
                    resWarn.setHandleUser("");
                    resWarn.setWarnId(Long.valueOf(valueArr[i]));
                    resWarns.add(resWarn);
                }
            }else if (id != null){
                resWarn = new ResWarn();
                resWarn.setWarnStatus(1);
                resWarn.setWarnMsg(paramMap.get("warnMsg")==null?"":String.valueOf(paramMap.get("warnMsg")));
                resWarn.setWarnId(Long.valueOf(id.toString()));
                resWarns.add(resWarn);
            }
            resWarnDao.handle(resWarns);
        }

        baseRespDto.setCode(StatusConstant.SUCCESS);
        baseRespDto.setMsg("处理成功");
        return baseRespDto;
    }

    @Override
    public ResWarnRespDto seeWarn(Long warnId, ResWarnRespDto baseRespDto) {

        if (warnId == null){
            baseRespDto.setCode(StatusConstant.FAIL);
            baseRespDto.setMsg("参数错误");
        }
        Map<String,Object> parma = new HashMap<>();
        parma.put("warnId",warnId);
        ResWarn resWarn = resWarnDao.seeWarn(parma);
        if (resWarn == null){
            baseRespDto.setCode(StatusConstant.FAIL);
            baseRespDto.setMsg("未查到该信息");
        }
        baseRespDto.setCode(StatusConstant.SUCCESS);
        baseRespDto.setSingleData(resWarn);
        return baseRespDto;
    }

    @Override
    public ResWarnRespDto selectModule(Map<String,Object> paramMap,ResWarnRespDto respDto) {
        //查询处理 和 未处理 数量
        paramMap.put("warnStatus",0);//未处理
        Long untreatedCount = resWarnDao.selectAllCount(paramMap);
        paramMap.put("warnStatus",1);//已处理
        Long processedCount = resWarnDao.selectAllCount(paramMap);
        respDto.setUntreatedCount(untreatedCount == null?0L:untreatedCount);
        respDto.setProcessedCount(processedCount == null?0L:processedCount);
        return respDto;
    }
}
