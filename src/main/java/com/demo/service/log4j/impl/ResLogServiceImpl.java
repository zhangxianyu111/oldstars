package com.demo.service.log4j.impl;

import com.demo.dao.log4j.ResLogMapper;
import com.demo.dto.response.log4j.ResLogRespDto;
import com.demo.exception.ResLogException;
import com.demo.pojo.log4j.ResLog;
import com.demo.service.log4j.ResLogService;
import com.demo.util.DateUtil;
import com.demo.util.PropertiesUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ResLogServiceImpl implements ResLogService {

    public static final String rootPath;
    static {
        PropertiesUtil.loadProps("logpath.properties");
        rootPath = PropertiesUtil.getProperty("log4j.appender.DailyRollingFile.File","log4j.properties");
    }

    @Resource
    private ResLogMapper resLogDao;

    @Override
    public ResLogRespDto selectAllByPage(Map<String, Object> paramMap, ResLogRespDto respDto) {
        PageHelper.startPage(Integer.valueOf(paramMap.get("page").toString()),Integer.valueOf(paramMap.get("limit").toString()));
        List<ResLog> logs = resLogDao.selectAll(paramMap);
        //获取文件路径
        for (ResLog log : logs) {
            if (log.getCreateTime() != null && DateUtil.isToday(log.getCreateTime())){
                log.setPath(rootPath);
            }else{
                log.setPath(rootPath+"."+DateUtil.parseDateToStr(log.getCreateTime(),DateUtil.DATE_FORMAT_YYYY_MM_DD));
            }
        }
        PageInfo pageInfo = new PageInfo(logs);
        long total = pageInfo.getTotal();
        respDto.setCount(total);
        respDto.setSingleData(pageInfo);
        //查询error 和 warn 数量
        paramMap.put("logLevel","ERROR");
        Long errCount = resLogDao.selectAllCount(paramMap);
        paramMap.put("logLevel","WARN");
        Long warnCount = resLogDao.selectAllCount(paramMap);
        respDto.setErrCount(errCount == null?0L:errCount);
        respDto.setWarnCount(warnCount == null?0L:warnCount);
        return respDto;
    }

    @Override
    public void batchDownLoad(String ids,HttpServletResponse response) throws IOException{
        List<ResLog> resLogs = resLogDao.selectByIds(ids);
        String path = "";
        if (resLogs != null && resLogs.size()>0){
            for (ResLog resLog : resLogs) {
                if (resLog.getCreateTime() != null && DateUtil.isToday(resLog.getCreateTime())){
                    path = rootPath;
                }else{
                    path = rootPath+"."+DateUtil.parseDateToStr(resLog.getCreateTime(),DateUtil.DATE_FORMAT_YYYY_MM_DD);
                }
                download(path,response);
            }
        }
    }

    @Override
    public void downLoad(Long id,HttpServletResponse response) throws Exception {
        ResLog resLog = resLogDao.selectById(id);
        if (resLog != null){
            String desc = resLog.getLogMsg();
            charsetDownload(desc, response);
        }else{
            throw new ResLogException("查询日志失败！");
        }
    }

    @Override
    public ResLogRespDto rankingList(Map<String,Object> paramMap,ResLogRespDto respDto) {
        //查询错误日志数量
        paramMap.put("logLevel","ERROR");
        Long errCount = resLogDao.count(paramMap);
        List<ResLog> errList = resLogDao.rankingList(paramMap);
        paramMap.put("logLevel","WARN");
        Long warnCount = resLogDao.count(paramMap);
        List<ResLog> warnList = resLogDao.rankingList(paramMap);
        respDto.setErrCount(errCount);
        respDto.setWarnCount(warnCount);
        respDto.setErrList(errList);
        respDto.setWarnList(warnList);
        return respDto;
    }

    public void charsetDownload(String content, HttpServletResponse response) throws IOException {
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
            throw new RuntimeException("导出失败");
        }finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException ioEx) {
                throw new IOException("io导出失败");
            }
        }
    }

    public String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public HttpServletResponse download(String path, HttpServletResponse response) throws IOException {
        String filename = "";
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(filename+" is not found");
        } catch (IOException e) {
            throw new IOException(filename+" io is Error");
        }
        return response;
    }
}