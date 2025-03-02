package com.hfw.model.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * IO工具类
 * @author farkle
 * @date 2022-04-06
 */
public class IOUtil {
    private static final int BUFFER_SIZE = 1024;

    /**
     * 流拷贝
     * @param is 输入流
     * @param os 输出流
     * @return
     * @throws IOException
     */
    public static long copy(InputStream is, OutputStream os) throws IOException {
        return copy(is,os,BUFFER_SIZE);
    }

    /**
     * 流拷贝
     * @param is 输入流
     * @param os 输出流
     * @param bufferSize 缓冲大小
     * @return
     * @throws IOException
     */
    public static long copy(InputStream is, OutputStream os, int bufferSize) throws IOException{
        byte[] buffer = new byte[bufferSize];
        int length;
        long total = 0;
        while ( (length=is.read(buffer)) > 0 ){
            os.write(buffer,0, length);
            total += length;
        }
        return total;
    }

    /**
     * 获取输入流的大小,并关闭输入流
     * @param is
     * @return
     * @throws IOException
     */
    public long length(InputStream is) throws IOException {
        try (is) {
            long size = 0;
            byte[] buf = new byte[1024];
            int read;
            while ((read = is.read(buf)) != -1) {
                size += read;
            }
            return size;
        }
    }

    /**
     * nio 拷贝
     * @param is
     * @param channel
     * @return
     * @throws IOException
     */
    public static long copy(InputStream is, FileChannel channel, int bufferSize) throws IOException{
        byte[] buffer = new byte[bufferSize];
        int length;
        long total = 0;
        while ( (length=is.read(buffer)) > 0 ){
            channel.write(ByteBuffer.wrap(buffer,0,length));
            total += length;
        }
        return total;
    }

    /**
     * nio 拷贝
     * @param is
     * @param channel
     * @return
     * @throws IOException
     */
    public static long copy(InputStream is, FileChannel channel) throws IOException{
        return copy(is,channel,BUFFER_SIZE);
    }

}
