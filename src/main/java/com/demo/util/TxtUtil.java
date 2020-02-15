package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static List<Map<String, String>> result = null;
    private static List<String> pojectList = null;
    private static List<String> fileList = null;

    synchronized private static void loadLocalTxts(String path){
        logger.info("开始读取" + path + "下的所有 txt 文件.......");
        if(path == null){
            logger.error("String path is null;   参数 path 不能为空。");
            return;
        }
        fileList = new ArrayList<>();
        getAllFileName(path, fileList);
        logger.info("获取日志文件路径完成。。。" + fileList);

        logger.info("开始加载配置的日志路径文件 txt 的内容.......");
        result = new ArrayList<>();
        for(String file: fileList){
            loadLocalFile(file);
        }
    }

    synchronized private static void loadProjectTxt(String file){
        logger.info("开始加载 Project 中 txt 文件内容.......");
        if(file == null){
            logger.error("String path is null;   参数 path 不能为空。");
            return;
        }
        loadProjectFile(file);
    }
    synchronized private static void loadLocalFile(String file){
        logger.info("开始加载"+file+"文件内容.......");
        try {
            List<String> lines = Files.readAllLines(Paths.get(file));
            Map<String, String> map = new HashMap<>();
            map.put("configFilePath", new File(file).getAbsolutePath());
            int lineNo = 1;
            for (String line : lines) {
                if (lineNo == 2) {
                    String[] split = line.split(" = ");
                    if(split.length == 2){
                        map.put(split[0], split[1]);
                    }
                } else {
                    String[] split = line.split(" = ");
                    if(split.length == 2){
                        map.put(split[0], split[1]);
                    }
                }
                lineNo++;
            }
            result.add(map);
        } catch (FileNotFoundException e) {
            logger.error(file + "文件未找到" + e.getMessage());
        } catch (IOException e) {
            logger.error("出现IOException" + e.getMessage());
        } finally {
            logger.info("加载" + file + "文件内容完成...........");
        }
    }

    synchronized private static void loadProjectFile(String file){
        logger.info("开始加载"+file+"文件内容.......");
        BufferedReader br = null;//构造一个BufferedReader类来读取文件
        pojectList = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(new File(file)));
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                pojectList.add(s);
            }
            br.close();
        } catch (FileNotFoundException e) {
            logger.error(file + "文件未找到" + e.getMessage());
        } catch (IOException e) {
            logger.error("出现IOException" + e.getMessage());
        } finally {
            try {
                if(null != br) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error("logpath.txt文件流关闭出现异常");
            }
            logger.info("加载" + file + "文件内容完成...........");
        }
    }

    public static List<String> getProjectFileList(String path){
        if(null == pojectList || pojectList.isEmpty()){
            loadProjectTxt(path);
        }
        return pojectList;
    }

    public static List<String> getFileList(String path){
        if(null == fileList || fileList.isEmpty()){
            getAllFileName(path, fileList);
        }
        return fileList;
    }

    public static List<Map<String, String>> getLocalFileDetail(String path){
        loadLocalTxts(path);
        return result;
    }

    private static void getAllFileName(String path, List<String> fileNameList) {
        File file = new File(path);
        if(!file.exists()){
            logger.error(file + "不存在。。。");
            return ;
        }
        if(file.isFile()){
            String absolutePath = file.getAbsolutePath();
            fileNameList.add(absolutePath);
            logger.info("添加文件地址：" + absolutePath);
        }
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files != null) {
                for (File file1 : files) {
                    getAllFileName(file1.getAbsolutePath(), fileNameList);
                }
            }else{
                logger.error(file +"是个文件夹 ? ? ? ? ? ");
            }
        }
    }

    public static void replace(String configFilePath, String condition, long pointer1) {
        if(configFilePath == null || condition == null){
            logger.error("Parameter can't be Null...");
            return;
        }
        pointer1 = (pointer1 >= 0) ? pointer1 :  0  ;
        logger.info("开始修改"+configFilePath+"文件内容.......");
        try {
            List<String> lines = Files.readAllLines(Paths.get(configFilePath));
            List<String> replaced = new ArrayList<>();
            //设置起始行的行号
            int lineNo = 1;
            for (String line : lines) {
                if (lineNo == 2) {
                    replaced.add(condition + " = " + pointer1);
                } else {
                    replaced.add(line);
                }
                lineNo++;
            }
            Files.write(Paths.get(configFilePath), replaced);
        } catch (FileNotFoundException e) {
            logger.error(configFilePath + "文件未找到" + e.getMessage());
        } catch (IOException e) {
            logger.error("出现IOException" + e.getMessage());
        } finally {
            logger.info("修改" + configFilePath + "文件内容完成...........");
        }
    }
}













