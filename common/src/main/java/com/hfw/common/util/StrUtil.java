package com.hfw.common.util;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符工具类
 * @author farkle
 * @date 2022-04-06
 */
public class StrUtil {
    /**
     * 字符转int
     * @param c
     * @return
     */
    public static int char2int(char c){
        return c-'0';
    }

    /**
     * 判断一个字符是否中文
     * 中文unicode编码
     * 基本汉字 20902字 4E00-9FA5
     * 基本汉字补充 90字 9FA6-9FFF
     * @param c
     * @return
     */
    public static boolean isChinese(char c){
        return c>='\u4E00' && c<='\u9FFF';
    }

    /**
     * 首字母大写
     * a的二进制码-->1100001
     * A的二进制码-->1000001
     * @param name
     * @return
     */
    public static String upperCase(String name) {
        char[] cs = name.toCharArray();
        //cs[0] -= 32;
        cs[0] &= 0xDF;
        return String.valueOf(cs);
    }
    /**
     * 首字母小写
     * @param name
     * @return
     */
    public static String lowerCase(String name){
        char[] cs = name.toCharArray();
        //cs[0] += 32;
        cs[0] |= 0x20;
        return String.valueOf(cs);
    }


    /** 下划线转驼峰 */
    public static String lineToHump(String str){
        str = str.toLowerCase();
        String result = "";
        String[] arr = str.split("_");
        if(arr!=null && arr.length>0){
            result += arr[0];
            for(int i=1;i<arr.length;i++){
                result += upperCase(arr[i]);
            }
        }
        return result;
    }
    /** 下划线转驼峰2 */
    public static String lineToHump2(String str) {
        str = str.toLowerCase();
        Pattern linePattern = Pattern.compile("_(\\w)");
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    public static String humpToLine(String str){
        int diff = 'a'-'A';
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(c>='A' && c<='Z'){
                sb.append('_');
                c = (char)(c+diff);
                sb.append(c);
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /** 驼峰转下划线(简单写法，效率低于humpToLine2) */
    public static String humpToLine3(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
    /** 驼峰转下划线,效率比上面高 */
    public static String humpToLine2(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取文件名
     * @param path
     * @return
     */
    public static String filename(String path) {
        if (path == null) {
            return "";
        }
        int separatorIndex = path.lastIndexOf(File.separator);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    /**
     * 获取文件扩展名
     * @param path
     * @return
     */
    public static String getFilenameExtension(String path) {
        if(path==null){
            return "";
        }
        int extIndex = path.lastIndexOf(".");
        if (extIndex == -1) {
            return "";
        }
        return path.substring(extIndex + 1);
    }

    /**
     * 字符串解析替换${}
     * 遍历map属性替换
     * @param str
     * @param map
     * @return
     */
    public static String resolve(String str, Map<String,String> map){
        String result = str;
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            result = result.replaceAll("\\$\\{"+key+"}", map.get(key));
        }
        return result;
    }

    /**
     * 字符串解析替换${}
     * 摘自tomcat-Bootstrap.replace()
     * @param str
     * @param map
     * @return
     */
    public static String replace(String str, Map<String,String> map){
        String result = str;
        int pos_start = str.indexOf("${");
        if (pos_start >= 0) {
            StringBuilder builder = new StringBuilder();
            int pos_end = -1;
            while (pos_start >= 0) {
                builder.append(str, pos_end + 1, pos_start);
                pos_end = str.indexOf('}', pos_start + 2);
                if (pos_end < 0) {
                    pos_end = pos_start - 1;
                    break;
                }
                String propName = str.substring(pos_start + 2, pos_end);
                String replacement = null;
                if(propName.length()>0){
                    if(map!=null && map.containsKey(propName)) {
                        replacement = map.get(propName);
                    }
                    if(replacement==null){
                        replacement = System.getenv(propName);
                        if(replacement==null){
                            replacement = System.getProperty(propName);
                        }
                    }
                }
                if (replacement != null) {
                    builder.append(replacement);
                } else {
                    builder.append(str, pos_start, pos_end + 1);
                }
                pos_start = str.indexOf("${", pos_end + 1);
            }
            builder.append(str, pos_end + 1, str.length());
            result = builder.toString();
        }
        return result;
    }
    public static String replace(String str, String... params){
        String result = str;
        int pos_start = str.indexOf("${");
        if (pos_start >= 0) {
            StringBuilder builder = new StringBuilder();
            int pos_end = -1;
            while (pos_start >= 0) {
                builder.append(str, pos_end + 1, pos_start);
                pos_end = str.indexOf('}', pos_start + 2);
                if (pos_end < 0) {
                    pos_end = pos_start - 1;
                    break;
                }
                String propName = str.substring(pos_start + 2, pos_end);
                String replacement = null;
                if(propName.length()>0){
                    Integer paramsIndex = Integer.parseInt(propName.trim());
                    replacement = params[paramsIndex];
                }
                if (replacement != null) {
                    builder.append(replacement);
                } else {
                    builder.append(str, pos_start, pos_end + 1);
                }
                pos_start = str.indexOf("${", pos_end + 1);
            }
            builder.append(str, pos_end + 1, str.length());
            result = builder.toString();
        }
        return result;
    }

    public static String trim(String str, char c) {
        int len = str.length();
        int st = 0;
        while ( (st < len) && (str.charAt(st) == c) ){
            st ++;
        }
        while ( (st < len) && (str.charAt(len-1) == c) ){
            len --;
        }
        return (st >0) && (len<str.length())? str.substring(st, len): str;
    }
    public static String trimLeft(String str, char c){
        int len = str.length();
        int st = 0;
        while ( (st < len) && (str.charAt(st) == c) ){
            st ++;
        }
        if(st >0){
            return str.substring(st);
        }
        return str;
    }
    public static String trimRight(String str, char c){
        int len = str.length();
        int st = 0;
        while ( (st < len) && (str.charAt(len-1) == c) ){
            len --;
        }
        if(len<str.length()){
            return str.substring(st, len);
        }
        return str;
    }

    public static boolean hasText(String str) {
        return str != null && str.trim().length()>0;
    }

    public static String join(Collection collection, CharSequence delimiter, CharSequence prefix, CharSequence suffix){
        StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
        collection.forEach( item->joiner.add(item.toString()) );
        return joiner.toString();
    }
    public static String join(Collection collection, CharSequence delimiter){
        return join(collection,delimiter,"","");
    }

    /**
     * 限制字符串的长度
     * @param str
     * @param length
     * @return
     */
    public static String limitLength(String str, int length){
        if(str.length()>length){
            return str.substring(0,length);
        }
        return str;
    }
}
