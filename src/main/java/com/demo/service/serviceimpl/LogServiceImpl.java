package com.demo.service.serviceimpl;

import com.demo.controller.LoggerController;
import com.demo.dao.LogMapper;
import com.demo.dto.response.BaseRespDto;
import com.demo.pojo.ErrorLogInfo;
import com.demo.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 *
 * @author zje
 *
 */
@Service
public class LogServiceImpl implements LogService {
    private final Logger LOGGER = LoggerFactory.getLogger(LoggerController.class);

    @Resource
    private LogMapper logDao;
    @Override
    public BaseRespDto selectAllByPage(Map<String, Object> paramMap,BaseRespDto respDto, Integer pageNum, Integer pageSize) {
        Long count = logDao.selectAllCount(paramMap);
        respDto.setCount(count);
        PageHelper.startPage(pageNum,pageSize);
        List<ErrorLogInfo> logs = logDao.selectAll(paramMap);
        respDto.setData(logs);
        return respDto;
    }

    @Override
    public void downloadLog(Long id,HttpServletResponse response) {
        ErrorLogInfo logInfo = logDao.selectById(id);
        if (logInfo != null){
            String desc = logInfo.getLogDesc();
            charsetDownload(desc, response);
        }
    }

    public void charsetDownload(String content, HttpServletResponse response){
        String fileName = randomUUID()+".txt";
        OutputStream os = null;
        try {
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(),"ISO8859-1"));
            byte[] bytes = content.getBytes("GBK");
            os = new BufferedOutputStream(response.getOutputStream());
            // 将字节流传入到响应流里,响应到浏览器
            os.write(bytes);
            os.flush();
            os.close();
        } catch (Exception ex) {
            LOGGER.error("导出失败:", ex);
            throw new RuntimeException("导出失败");
        }finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException ioEx) {
                LOGGER.error("导出失败:", ioEx);
            }
        }
    }

    public String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
