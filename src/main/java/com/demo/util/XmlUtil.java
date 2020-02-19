package com.demo.util;

import com.demo.common.Statistics;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

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

    /**
     * 将xml文件转换成map
     * @param pathName
     * @throws DocumentException
     */
    public static void xmlToMap(String pathName) throws DocumentException {
        Map<String,Object> map = new HashMap<String,Object>();

        //创建读取xml的对象
        SAXReader reader = new SAXReader();
        //指定读取的文件
        org.dom4j.Document doc = reader.read(new File(pathName));

        //读取根节点
        org.dom4j.Element root = doc.getRootElement();

        //进行循环遍历，添加到集合
        //获取迭代器，迭代元素
        for(Iterator iterator = root.elementIterator(); iterator.hasNext();){

            //迭代根节点中的所有子元素
            org.dom4j.Element e = (org.dom4j.Element) iterator.next();
            //System.out.println(e.getName());

            //获取该元素下的所有子元素，并放入list集合中
            List list = e.elements();
            //System.out.println(list.size());

            //判断该节点下是否还有子节点
            if(list.size()>0){
                //如果该节点下还有子节点，则调用方法，继续进行解析
                map.put(e.getName(),returnMap(e));
            }else {
                map.put(e.getName(),e.getText());
            }
        }
        //遍历出map集合中的元素
        Set<String> keySet = map.keySet();
        for(String s : keySet){
            System.out.println(s+"  :  "+map.get(s));
        }
    }






    //如果节点下还有节点，则调用该方法，继续进行解析
    public static Map returnMap(org.dom4j.Element e){

        Map map = new HashMap();
        //将该节点下所有子元素读进来，并放在list集合中
        List list = e.elements();

        //如果还有子元素，则继续进行解析
        if(list.size()>0){
            for(int i = 0;i<list.size();i++){
                //循环获取集合中的元素
                org.dom4j.Element ele = (org.dom4j.Element) list.get(i);
                List listMapList = new ArrayList();

                //如果该元素下，仍有子节点，则用递归进行进一步解析
                if(ele.elements().size()>0){
                    //调用自身会返回一个Map集合
                    Map m = returnMap(ele);
                    //判断map中是否已经有该元素,!=null,则代表该key已经存在
                    if(map.get(ele.getName()) != null){
                        //如果已经存在，获取该对象
                        Object obj = map.get(ele.getName());
                        //判断该元素是不是一个list集合，如果不是一个list集合，则直接添加到listMapList集合中
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){
                            listMapList.add(obj);
                            listMapList.add(m);//将返回的map集合也放进集合中
                        }//如果该元素是一个list集合，则将该元素赋值给listMapList
                        if(obj.getClass().getName().equals("java.util.ArrayList")){
                            listMapList = (List) obj;
                            listMapList.add(m);
                        }
                        //将该元素添加到map集合中去
                        map.put(ele.getName(),listMapList);
                    }else{    //如果该元素还未存在map集合中，则直接添加到map中
                        map.put(ele.getName(),m);

                    }
                }else{    //如果该节点下，没有子节元素了，
                    if(map.get(ele.getName()) !=null){
                        Object obj = map.get(ele.getName());
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){
                            listMapList.add(obj);
                            listMapList.add(ele.getText());
                        }
                        if(obj.getClass().getName().equals("java.util.ArrayList")){
                            listMapList = (List) obj;
                            listMapList.add(ele.getText());
                        }
                        map.put(ele.getName(), listMapList);
                    }else{
                        map.put(ele.getName(), ele.getText());
                    }
                }
            }
        }else{
            map.put(e.getName(), e.getText());

        }
        return map;
    }


    public static List<Map<String, String>> xml2list(String xml,String node) {
        xml =xml.replace("\n","").replace(" ","");
        List<Map<String, String>> resList = new ArrayList();
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = null;
        try {
            document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            org.dom4j.Element root = document.getRootElement();
            org.dom4j.Element dataList = (org.dom4j.Element) root.selectSingleNode(node);
            if(null==dataList){
                return new ArrayList();
            }
            Iterator it = dataList.elementIterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();
                Map<String, String> temMap = new HashMap<String, String>();
                temMap.put(element.getQualifiedName(), element.getText());
                resList.add(temMap);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resList;
    }


    /**
     * 将XML转为指定的POJO
     * @param clazz
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static Object xmlStrToOject(Class<?> clazz, String xmlStr) throws Exception {
        Object xmlObject = null;
        Reader reader = null;
        JAXBContext context = JAXBContext.newInstance(clazz);

        // XML 转为对象的接口
        Unmarshaller unmarshaller = context.createUnmarshaller();

        reader = new StringReader(xmlStr);
        xmlObject = unmarshaller.unmarshal(reader);

        if (null != reader) {
            reader.close();
        }

        return xmlObject;
    }

    public static Statistics getStatistics(String pathName) throws Exception {
        // 读取XML文件
        Resource resource = new ClassPathResource(pathName);
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = br.readLine()) !=null) {
            buffer.append(line);
        }

        br.close();

        // XML转为Java对象
        Statistics statistics = (Statistics)xmlStrToOject(Statistics.class, buffer.toString());
        return statistics;
    }




    public static void main(String[] args) throws Exception{
        Statistics s = getStatistics("config/warn.xml");
        System.out.println(s);
    }
}
