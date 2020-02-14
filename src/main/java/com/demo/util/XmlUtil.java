package com.demo.util;

import com.demo.quarz.LoggerDBTimer;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
/**
 *
 * @author zje
 *
 */
public class XmlUtil{

    //private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    public static String XMLReader2JDOM(String xmlPath){
        Document doc = getDocument(xmlPath);
        Element foo = doc.getRootElement();
        List allChildren = foo.getChildren();
        return ((Element) allChildren.get(0)).getChild("POINTER").getText();
    }

    private static Document getDocument(String xmlPath) {
        try {
            //String pathname = getPath(xmlPath);
            String pathname = "C:\\Users\\Administrator.SKY-20190324EMI\\Desktop\\pointer.xml";
            SAXBuilder builder = new SAXBuilder();
            return builder.build(new File(pathname));
        }catch (FileNotFoundException fe){
            //logger.error("未找到xml文件",fe);
        }catch (JDOMException je){
            //logger.error("读取xml文件失败",je);
        }catch (IOException ie){
            //logger.error("io流报错",ie);
        }
        return null;

    }

    private static String getPath(String xmlPath)  {
        /*try {*/
            /*URL uri = PropertiesUtil.class.getResource("/");
            File file = new File(uri.toURI());
            return file.getAbsolutePath() + xmlPath;*/
            String filePath=System.getProperty("user.dir");
            return filePath+"\\src\\main\\resources\\config\\pointer.xml";
        /*}catch (URISyntaxException ue){
            //logger.error("读取URI地址失败",ue);
        }
        return null;*/

    }
    public static void JDOM2XMLReader(String xmlPath,String value){
        Document doc = getDocument(xmlPath);
       // String pathname = getPath(xmlPath);
        String filePath=System.getProperty("user.dir");
        String pathname = filePath+"\\src\\main\\resources\\config\\pointer.xml";
        Element foo = doc.getRootElement();
        List<Element> allChildren = foo.getChildren();
        for (Element el : allChildren) {
            if (el.getAttributeValue("id").equals("ID001")) {
                Element pointer = el.getChild("POINTER");
                pointer.setText(value);
            }
        }
        toXmlValue(doc,pathname);
    }

    private static void toXmlValue(Document doc,String xmlPath) {
        try {
            //设置xml输出格式
            Format format = Format.getPrettyFormat();
            format.setEncoding("utf-8");//设置编码
            format.setIndent("    ");//设置缩进
            //得到xml输出流
            XMLOutputter out = new XMLOutputter(format);
            // 创建文件输出流
            FileWriter writer = new FileWriter(xmlPath);
            //把数据输出到xml中
            out.output(doc, writer);
            writer.close();
        }catch (IOException e){
            //logger.error("io流报错",ie);
        }
    }

    public static void main(String[] args){
        JDOM2XMLReader("/config/pointer.xml","10000");
    }
}
