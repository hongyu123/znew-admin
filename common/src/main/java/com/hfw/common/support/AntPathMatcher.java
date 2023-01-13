package com.hfw.common.support;

/**
 * Ant风格路径匹配
 * ? 匹配任何单字符
 * * 匹配0或者任意数量的字符
 * ** 匹配0或者更多的目录
 *
 * @author farkle
 * @date 2022-04-06
 */
public class AntPathMatcher {

    /**
     * 默认分隔符
     */
    private static String DEFAULT_SEPARATOR = "/";

    /**
     * 模式匹配路径
     * @param path 路径
     * @param pattern 模式
     * @return
     */
    public static boolean match(String path, String pattern){
        String[] pathArr = path.split(DEFAULT_SEPARATOR);
        String[] patternArr = pattern.split(DEFAULT_SEPARATOR);
        return match(pathArr, patternArr);
    }

    /**
     * 模式匹配路径
     * @param pathArr 路径数组
     * @param patternArr 模式数组
     * @return
     */
    private static boolean match(String[] pathArr, String[] patternArr){
        int pathIndex = 0;
        for(int patternIndex=0; patternIndex<patternArr.length; patternIndex++){
            if(pathIndex>=pathArr.length){
                return false;
            }
            if("**".equals(patternArr[patternIndex])){
                if(pathIndex+1 >= patternArr.length){
                    return true;
                }
                pathIndex += skipPath(patternArr[pathIndex+1],pathArr, pathIndex);
                continue;
            }else{
                if(patternArr[patternIndex].indexOf('*') >-1){
                    if(!matchStr( pathArr[pathIndex], patternArr[patternIndex] )){
                        return false;
                    }
                }else if(patternArr[patternIndex].indexOf('?') >-1){
                    if(!matchStr( pathArr[pathIndex], patternArr[patternIndex] )){
                        return false;
                    }
                }else if( !patternArr[patternIndex].equals( pathArr[pathIndex] ) ){
                    return false;
                }
                pathIndex++;
            }
        }
        if(pathIndex!=pathArr.length){
            return false;
        }
        return true;
    }

    /**
     * 模式匹配字符串
     * 最小匹配原则,*.jsp不能匹配test.jsp.jsp
     * @param str
     * @param pattern
     * @return
     */
    private static boolean matchStr(String str, String pattern){
        int strIndex = 0;
        for(int patternIndex=0; patternIndex<pattern.length(); patternIndex++){
            if(strIndex>=str.length()){
                return false;
            }
            if('*'==pattern.charAt(patternIndex)){
                if(patternIndex+1 >= pattern.length()){
                    return true;
                }
                strIndex += skipChar(pattern.charAt(patternIndex+1), str,strIndex);
                continue;
            }else{
                if('?'==pattern.charAt(patternIndex)){
                    strIndex++;
                    continue;
                }else if(pattern.charAt(patternIndex) != str.charAt(strIndex)){
                    return false;
                }
                strIndex++;
            }
        }
        if(strIndex!=str.length()){
            return false;
        }
        return true;
    }

    /**
     * 模式跳过路径
     * @param nextPattern 要匹配的模式
     * @param pathArr 路径数组
     * @param pathIndex 路径起始索引
     * @return 跳过的数量
     */
    private static int skipPath(String nextPattern, String[] pathArr, int pathIndex){
        int skip = 0;
        for(; pathIndex<pathArr.length; pathIndex++){
            /*if(nextPattern.equals(pathArr[pathIndex])){
                return skip;
            }*/
            if(matchStr(pathArr[pathIndex], nextPattern)){
                return skip;
            }
            skip++;
        }
        return skip;
    }

    /**
     * 模式跳过字符
     * @param nextPattern 要匹配的字符
     * @param str 字符串
     * @param strIndex 字符串起始索引
     * @return 跳过的数量
     */
    private static int skipChar(char nextPattern, String str, int strIndex){
        int skip = 0;
        for(; strIndex<str.length(); strIndex++){
            if(nextPattern == str.charAt(strIndex)){
                return skip;
            }
            skip++;
        }
        return skip;
    }

}