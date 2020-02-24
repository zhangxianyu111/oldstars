package com.demo.service.log4j;

import com.demo.dto.response.log4j.ResWarnRespDto;

import java.util.Map;

public interface ResWarnService {


    ResWarnRespDto selectAllByPage(Map map, ResWarnRespDto baseRespDto);

    ResWarnRespDto handle(Map<String,Object> paramMap, ResWarnRespDto baseRespDto);

    ResWarnRespDto seeWarn(Long warnId, ResWarnRespDto baseRespDto);

    ResWarnRespDto selectModule(Map<String,Object> map,ResWarnRespDto baseRespDto);
}
