package com.demo.service.log4j;

import com.demo.dto.response.log4j.ResLogRespDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface ResLogService {

    ResLogRespDto selectAllByPage(Map<String,Object> paramMap,ResLogRespDto respDto);

    void batchDownLoad(String ids, HttpServletResponse response) throws IOException;

    void downLoad(Long id,HttpServletResponse response) throws Exception;

    ResLogRespDto selectModule(Map<String,Object> paramMap,ResLogRespDto baseRespDto);
}
