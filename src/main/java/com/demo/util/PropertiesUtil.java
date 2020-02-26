package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
/**
 *
 * @author zje
 *
 */
public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties props ;
    synchronized static public void loadProps(String propPath){
        logger.info("开始加载properties文件内容.......");
        InputStream in = null;
        try {
            props  = new Properties();
            //第一种，通过类加载器进行获取properties文件流
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propPath);
            //第二种，通过类进行获取properties文件流
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("module-xml.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("module-xml.properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + props);
    }

    public static String getProperty(String key,String propPath){
        if(null == props || props.isEmpty()) {
            loadProps(propPath);
        }
        String property = props.getProperty(key);
        if (property == null){
            loadProps(propPath);
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue,String propPath) {
        if(null == props || props.isEmpty()) {
            loadProps(propPath);
        }
        return props.getProperty(key, defaultValue);
    }

    /**
     * 写Properties文件（有序）
     */
    public static void writeOrderedPropertiesFile(String propPath,String name,String value) {
        OutputStreamWriter outputStreamWriter = null;
        try {
            //if(null == props || props.isEmpty()) {
                loadProps(propPath);
            //}
            URL uri = PropertiesUtil.class.getResource("/");
            File file = new File(uri.toURI());
            String pathname = file.getAbsolutePath() + "/module-xml.properties";
            file = new File(pathname);
            //保存属性到b.properties文件
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);//true表示追加打开,false每次都是清空再重写
            //prop.store(oFile, "此参数是保存生成properties文件中第一行的注释说明文字");//这个会两个地方乱码
            //prop.store(new OutputStreamWriter(oFile, "utf-8"), "汉字乱码");//这个就是生成的properties文件中第一行的注释文字乱码
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            props.store(outputStreamWriter, new Date().toString());
        }catch(URISyntaxException e){
            logger.error("web目录获取失败",e);
        }catch (FileNotFoundException e) {
            logger.error("module-xml.properties文件未找到",e);
        } catch (IOException e) {
            logger.error("出现IOException",e);
        } finally {
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    logger.error("module-xml.properties文件流关闭出现异常",e);
                }
            }
        }

    }
}
