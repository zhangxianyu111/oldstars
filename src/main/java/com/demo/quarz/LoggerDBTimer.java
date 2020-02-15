package com.demo.quarz;

import com.demo.dao.LogMapper;
import com.demo.dao.LogPointerMapper;
import com.demo.pojo.ErrorLogInfo;
import com.demo.util.DateUtil;
import com.demo.util.PropertiesUtil;
import com.demo.util.StringUtil;
import com.demo.util.TxtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class LoggerDBTimer {
//    public static Long pointer; //上次文件大小
    private static final Logger logger = LoggerFactory.getLogger(LoggerDBTimer.class);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    @Resource
    private LogMapper logMapper;
    @Resource
    private LogPointerMapper logPointerMapper;

    static{
//        PropertiesUtil.loadProps("logpath.properties");
        //pointer = Long.valueOf(XmlUtil.XMLReader2JDOM("logpath.properties"));
    }
    ExecutorService exec = Executors.newFixedThreadPool(5);


    @Scheduled(cron = "0/10 * * * * ?")
    public void Scheduled() throws Exception{
        logger.info("每5分钟开始调用第三方接口查询读取日志任务咯");

        /*//JingEn begin
        final File tmpLogFile = new File(PropertiesUtil.getProperty("logpath","logpath.properties"));
        realtimeShowLog(tmpLogFile);
        //JingEn end*/

        /**Byron start*/
        String substring = LoggerDBTimer.class.getClassLoader().getResource("logpath.txt").toString().substring(6);
        //可以配置多个日志的路径
        List<String> projectFileList = TxtUtil.getProjectFileList(substring);
        //每个路径下有可能有多个文件
//        List<String> logFileList = null;
        List<Map<String, String>> localFileDetail = null;

        if(projectFileList != null){
            for (String path: projectFileList){
//                logFileList = TxtUtil.getFileList(path);
                localFileDetail = TxtUtil.getLocalFileDetail(path);
            }
        }

        if(localFileDetail != null){
            for (Map<String, String> map: localFileDetail){
                realtimeShowLog(map);
            }
        }
        /**Byron end*/
        logger.info(dateFormat.format(new Date())+"--调用第三方接口查询读取日志结束");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void ScheduledDelete() throws Exception{
        logger.info("每天凌晨1点开始调用第三方接口删除数据库过期日志信息任务");
        logMapper.deleteExpire();
        logger.info("每天凌晨1点开始调用第三方接口删除数据库过期日志信息任务");
    }

    public void realtimeShowLog(Map<String, String> map) throws Exception {
        /*//jingEn begin
        if (logFile == null) {
            throw new IllegalStateException("logFile can not be null");
        }
        //jingEn end*/

        //启动一个线程读取新增的日志信息
        exec.execute(new Runnable() {

            @Override
            public void run() {
                /**Byron start*/
                if(map == null ){
                    logger.error("realtimeShowLog Parameter 'map' is Null...");
                    return ;
                }
                String configFilePath = map.get("configFilePath");
                String file = map.get("logFile");
                long pointer = Long.parseLong(map.get("pointer"));
                if (file == null) {
                    System.err.println("----------日志配置文件出错了----------");
                    logger.error("-----------------日志配置文件出错了-------------------");
                    return;
                }
                File logFile = new File(file);
                /**Byron end*/
                //获得变化部分
                try {
                    long len = logFile.length();
                    if (len < pointer) {
                        logger.info("Log file was reset. Restarting logging from start of file.");
                        pointer = 0L;
                    } else {

                        //指定文件可读可写
                        RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");

                        //获取RandomAccessFile对象文件指针的位置，初始位置是0
                        System.out.println("RandomAccessFile文件指针的初始位置:" + pointer);

                        randomFile.seek(pointer);//移动文件指针位置

                        String tmp = "";
                        boolean flag = false;
                        boolean flag2 = false;
                        StringBuffer errorDesc = new StringBuffer();
                        ErrorLogInfo logInfo = null;
                        while ((tmp = randomFile.readLine()) != null) {

                            /*//jingEn begin
                            String str = new String(tmp.getBytes("utf-8"));
                            //jingEn end*/

                            /**Byron start*/
                            String str = new String(tmp.getBytes("ISO-8859-1"),"utf-8");
                            /**Byron end*/
                            if (flag2){
                                if (str.trim().length()<24){
                                    errorDesc.append(str).append("\n");
                                    continue;
                                }
                                if (DateUtil.isDate(str.trim().substring(0,23))){
                                    flag2 = false;
                                    logInfo.setLogDesc(errorDesc.toString());
                                    int result = logMapper.insert(logInfo);
                                    if (result == 0){
                                        logger.warn("新增日志失败，请检查日志信息！！");
                                    }
                                    errorDesc.setLength(0);
                                    continue;
                                }else{
                                    errorDesc.append(str).append("\n");
                                    continue;
                                }
                            }
                            //如果获取到报错信息
                            if(flag){
                                logInfo.setLogTitle(str.split(":")[0]);
                                errorDesc.append(str).append("\n");
                                flag = false;
                                flag2 = true;
                                continue;
                            }
                            //获取一行中的时间
                            if(str.contains("ERROR")){
                                logInfo = new ErrorLogInfo();
                                logInfo.setLogDate(DateUtil.parseDate(str.substring(0,23)));
                                logInfo.setModule(StringUtil.regularExpression(str));
                                flag = true;
                            }
                        }
                        //将读取的位置存储到properties文件
                        pointer = randomFile.getFilePointer();
                        //PropertiesUtil.writeOrderedPropertiesFile("logPath.properties","pointer",String.valueOf(pointer));
//                        logPointerMapper.update(pointer);
                        randomFile.close();
                    }

                } catch (Exception e) {
                    //实时读取日志异常，需要记录时间和lastTimeFileSize 以便后期手动补充
                    logger.warn(dateFormat.format(new Date()) + " File read error, pointer: " + pointer,e);
                } finally {
                    //将pointer 落地以便下次启动的时候，直接从指定位置获取
                    /**Byron start*/
                    TxtUtil.replace(configFilePath, "pointer", pointer);
                    /**Byron end*/
                }
            }

        });

    }
}
