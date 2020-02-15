package com.demo.service;

import com.demo.dto.response.BaseRespDto;
import com.demo.pojo.ErrorLogInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
/**
 *
 * @author zje
 *
 */
@Service
public interface LogService {

    //查询日志
    BaseRespDto selectAllByPage(Map<String,Object> paramMap,BaseRespDto respDto, Integer pageNum, Integer pageSize);
    //查询单挑日志
    void downloadLog(Long id, HttpServletResponse response);


}
