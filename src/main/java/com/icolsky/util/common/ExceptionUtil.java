package com.icolsky.util.common;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by FuChang Liu
 */
@Slf4j
public class ExceptionUtil {

    public static void printStackTrace(Exception e){
        printStackTrace("",e);
    }
    public static void printStackTrace(String meg,Exception e){
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw =  new PrintWriter(sw);
            //将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        }finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        log.error("{},异常错误信息如下:error",meg,e);
    }
}
