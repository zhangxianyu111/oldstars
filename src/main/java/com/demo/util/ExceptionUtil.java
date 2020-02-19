package com.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
    public static String getTrace(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        StringBuffer  sb = sw.getBuffer();
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            int i = 1/0;
        }catch (Exception e){
            System.out.println(e.getClass().getName());
        }


    }
}
