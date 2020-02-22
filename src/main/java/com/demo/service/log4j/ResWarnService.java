package com.demo.service.log4j;

import com.demo.dto.response.log4j.ResWarnRespDto;

import java.util.Map;

public interface ResWarnService {


    ResWarnRespDto selectAllByPage(Map map, ResWarnRespDto baseRespDto);

    ResWarnRespDto handle(Map map, ResWarnRespDto baseRespDto);

    ResWarnRespDto seeWarn(Map map, ResWarnRespDto baseRespDto);

    ResWarnRespDto selectModule(ResWarnRespDto baseRespDto);
}
