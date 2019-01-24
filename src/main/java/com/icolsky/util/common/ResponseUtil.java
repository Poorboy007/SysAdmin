package com.icolsky.util.common;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lijj on 2018/2/5.
 */
public class ResponseUtil {

    public static void sendResult(HttpServletRequest request, HttpServletResponse response, Object args){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String originHeader = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            String json= JSON.toJSONString(args);
            writer.print(json);
            writer.flush();
        } catch (IOException e) {
            ExceptionUtil.printStackTrace(e);
        }finally{
            if(writer != null) writer.close();
        }
    }

}
