package com.hfw.common.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * CSV文件解析器
 * @author zyh
 * @date 2022-04-07
 */
public class CSVParser {
    private char separator = ',';//分割符
    private char quotes = '"';//引号
    private boolean multiLine = false;//跨行是否抛出异常
    private boolean skipQuotes = true;//跳过引号
    private String charset = "utf8";
    private Map<String,Integer> columnIndex = new HashMap<>();

    public void setSeparator(char separator) {
        this.separator = separator;
    }
    public void setQuotes(char quotes) {
        this.quotes = quotes;
    }
    public void setSkipQuotes(boolean skipQuotes) {
        this.skipQuotes = skipQuotes;
    }
    public void setCharset(String charset) {
        this.charset = charset;
    }
    public void setMultiLine(boolean multiLine){
        this.multiLine = multiLine;
    }
    public Map<String,Integer> getColumnIndex(){
        return this.columnIndex;
    }

    //表头合法字符范围[0,127]:ASCII表,[19968,40869]:中文字符
    public static boolean isLegalChar(char c){
        return c<=127 || (c>=19968 && c<=40869) ;
    }
    //去除非法字符
    public static String legalStr(String str){
        String res = "";
        int startIndex = 0;
        int i = 0;
        for (; i < str.length(); i++) {
            if(!isLegalChar(str.charAt(i))){
                res+= str.substring(startIndex,i);
                startIndex = i+1;
            }
        }
        if(startIndex<=i){
            res+= str.substring(startIndex,i);
        }
        return res;
    }
    public BufferedReader parseInputStream(InputStream is)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
        String line = br.readLine();
        if(line==null){
            throw new IOException("内容为空!");
        }
        List<String> list = CSVParser.split(line,separator,quotes,skipQuotes,multiLine);
        for (int i = 0; i < list.size(); i++) {
            columnIndex.put( legalStr(list.get(i)), i);
        }
        return br;
    }
    public Iterator<List<String>> parse(InputStream is) throws IOException {
        return new Itr(parseInputStream(is));
    }
    public List<List<String>> parseAll(InputStream is) throws IOException {
        List<List<String>> list = new ArrayList();
        String line = null;
        BufferedReader br = parseInputStream(is);
        while ( (line=br.readLine()) !=null && line.trim().length()>0){
            try {
                list.add( CSVParser.split(line,separator,quotes,skipQuotes,multiLine) );
            }catch (MultiLineException e){
                //跨行处理
                while (true){
                    String s = br.readLine();
                    if(s==null){
                        throw e;
                    }
                    int quotesIndex = s.indexOf(quotes);
                    if(quotesIndex>-1){
                        line += "\r\n"+s.substring(0, quotesIndex+1);
                        list.add( CSVParser.split(line,separator,quotes,skipQuotes,multiLine) );

                        line = s.substring(quotesIndex+1);
                        if(line.trim().length()>0){
                            list.add( CSVParser.split(line,separator,quotes,skipQuotes,multiLine) );
                        }
                        break;
                    }else{
                        line += "\r\n"+s;
                    }
                }
            }
        }
        return list;
    }
    public String get(String columnName, List<String> list){
        Integer index = columnIndex.get(columnName);
        if(index==null || index>=list.size()){
            return null;
        }
        return list.get(index);
    }

    private class Itr implements Iterator<List<String>> {
        private BufferedReader br;
        private String line;
        public Itr(BufferedReader br){
            this.br = br;
        }
        @Override
        public boolean hasNext() {
            try{
                this.line = br.readLine();
            }catch (IOException e){
                return false;
            }
            return this.line!=null;
        }

        @Override
        public List<String> next() {
            return CSVParser.split(this.line,separator,quotes,skipQuotes,multiLine);
        }
    }
    static class MultiLineException extends RuntimeException{
        public MultiLineException(String msg) {
            super(msg);
        }
    }


    public static List<String> split(String line, char separator, char quotes, boolean skipQuotes, boolean multiLine){
        List<String> list = new ArrayList<>();
        int startIndex = 0;
        boolean inQuotes = false;//在引号内
        int quotesIndex = 0;
        int i = 0;
        for (; i < line.length(); i++) {
            //是分隔符
            if(separator==line.charAt(i)){
                if (!inQuotes) {
                    list.add(line.substring(startIndex, i-quotesIndex));
                    quotesIndex = 0;
                    startIndex = i+1;//跳过分割符
                }
            }else if(quotes==line.charAt(i)){
                if(inQuotes){
                    inQuotes = false;
                    quotesIndex = skipQuotes? 1: 0;
                }else{
                    inQuotes = true;
                    startIndex = skipQuotes? i+1: i;//跳过引号
                }
            }
        }
        if(multiLine && inQuotes){
            throw new MultiLineException("不允许跨行!");
        }
        if(startIndex<=i){
            list.add(line.substring(startIndex, i-quotesIndex));
        }
        return list;
    }
    public static List<String> split(String line){
        return CSVParser.split(line,',','"',true,false);
    }
    public static List<String> split(String line, char separator){
        return CSVParser.split(line,separator,'"',true,false);
    }

    public static void main(String[] args) throws Exception{
        String s = CSVParser.legalStr("，1，，2，，，3，，，");
        System.out.println("-"+s+"-");
    }
}
