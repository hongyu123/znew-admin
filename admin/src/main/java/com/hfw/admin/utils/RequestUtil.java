package com.hfw.admin.utils;

import com.alibaba.fastjson2.JSON;
import com.hfw.model.utils.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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

    /*
    public static TreeMap<String, String> getParametersMap(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        TreeMap params = new TreeMap();

        while(paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if(values != null){
                if(values.length==1){
                    String value = values[0];
                    if(StrUtil.hasText(value)){
                        params.put(paramName, value);
                    }
                }else if(values.length>1){
                    params.put(paramName, values);
                }
            }
        }
        return params;
    }*/

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
            printWriter.print(JSON.toJSONString(obj));
        }
    }

    /**
     * 获取浏览器信息
     * system 操作系统
     * browser 浏览器
     *
     * @param request
     * @return
     */
    public static Map<String, String> getSystemBrowserInfo(HttpServletRequest request) {
        StringBuilder userAgent = new StringBuilder("[");
        userAgent.append(request.getHeader("User-Agent"));
        userAgent.append("]");
        int indexOfMac = userAgent.indexOf("Mac OS X");
        int indexOfWindows = userAgent.indexOf("Windows NT");
        int indexOfIE = userAgent.indexOf("MSIE");
        int indexOfIE11 = userAgent.indexOf("rv:");
        int indexOfFF = userAgent.indexOf("Firefox");
        int indexOfSogou = userAgent.indexOf("MetaSr");
        int indexOfChrome = userAgent.indexOf("Chrome");
        int indexOfSafari = userAgent.indexOf("Safari");
        boolean isMac = indexOfMac > 0;
        boolean isWindows = indexOfWindows > 0;
        boolean isLinux = userAgent.indexOf("Linux") > 0;
        boolean containIE = indexOfIE > 0 || (isWindows && (indexOfIE11 > 0));
        boolean containFF = indexOfFF > 0;
        boolean containSogou = indexOfSogou > 0;
        boolean containChrome = indexOfChrome > 0;
        boolean containSafari = indexOfSafari > 0;
        String browser = "";
        if (containSogou) {
            if (containIE) {
                browser = "搜狗" + userAgent.substring(indexOfIE, indexOfIE + "IE x.x".length());
            } else if (containChrome) {
                browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
            }
        } else if (containChrome) {
            browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
        } else if (containSafari) {
            int indexOfSafariVersion = userAgent.indexOf("Version");
            browser = "Safari "
                    + userAgent.substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x.x".length());
        } else if (containFF) {
            browser = userAgent.substring(indexOfFF, indexOfFF + "Firefox/xx".length());
        } else if (containIE) {
            if (indexOfIE11 > 0) {
                browser = "IE 11";
            } else {
                browser = userAgent.substring(indexOfIE, indexOfIE + "IE x.x".length());
            }
        }
        String os = "";
        if (isMac) {
            os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxxxxxx".length());
        } else if (isLinux) {
            os = "Linux";
        } else if (isWindows) {
            os = "Windows ";
            String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
                    + "Windows NTx.x".length());
            switch (version.trim()) {
                case "5.0":
                    os += "2000";
                    break;
                case "5.1":
                    os += "XP";
                    break;
                case "5.2":
                    os += "2003";
                    break;
                case "6.0":
                    os += "Vista";
                    break;
                case "6.1":
                    os += "7";
                    break;
                case "6.2":
                    os += "8";
                    break;
                case "6.3":
                    os += "8.1";
                    break;
                case "10":
                    os += "10";
                    break;
            }
        }

        Map<String, String> info = new HashMap<>();
        info.put("system", os);
        info.put("browser", browser.replaceAll("/", " "));
        return info;
    }

}
