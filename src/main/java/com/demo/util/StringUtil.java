package com.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author zje
 *
 */
public class StringUtil {
    /**
     * 获取中括号之间的内容
     * @param str
     * @return
     */
    public static String regularExpression(String str){
        String regex = "\\[(.*?)]";
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            list.add(matcher.group(1)) ;
        }
        return list.size()>0?list.get(list.size() - 1):null;
    }

    public static void main(String[] args) {
        String s = regularExpression("2020-02-11 10:29:24,489 [http-bio-8081-exec-7] ERROR [com.demo.controller.LoggerController] - 绯荤粺鎶ラ敊");
        System.out.println(s);
    }
}
