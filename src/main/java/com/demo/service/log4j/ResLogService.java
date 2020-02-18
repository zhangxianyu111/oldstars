package com.demo.service.log4j;

import com.demo.dto.response.BaseRespDto;
import com.demo.exception.ResLogException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface ResLogService {

    BaseRespDto selectAllByPage(Map<String,Object> paramMap,BaseRespDto respDto);

    void batchDownLoad(String ids, HttpServletResponse response) throws IOException;

    void downLoad(Long id,HttpServletResponse response) throws Exception;
}
