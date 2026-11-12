package com.hfw.service.utils;

import com.hfw.model.jackson.ObjectMapperUtil;
import com.hfw.model.utils.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Request工具类
 * @author farkle
 * @date 2022-04-06
 */
public class RequestUtil {

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StrUtil.hasText(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StrUtil.hasText(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        ip = request.getRemoteAddr();
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 根据请求头判断是否是ajax请求
     */
    public static boolean isJson(HttpServletRequest request) {
        String header = request.getHeader("Content-Type");
        if(header==null){
            header = request.getHeader("content-type");
        }
        return header!=null && header.contains("application/json");
        /*String acceptHeader = request.getHeader("accept");
        String XRequestHeader = request.getHeader("X-Requested-With");
        if( (acceptHeader!=null&&acceptHeader.indexOf("application/json")>-1) || (XRequestHeader!=null&&XRequestHeader.indexOf("XMLHttpRequest")>-1) ) {
            return true;
        }
        return false;*/
    }

    /**
     * 响应json字符串
     * @param response
     * @throws IOException
     */
    public static void json(HttpServletResponse response, Object obj) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        try(PrintWriter printWriter = response.getWriter()){
            printWriter.print(ObjectMapperUtil.singleton().writeValueAsString(obj));
        }
    }

}
