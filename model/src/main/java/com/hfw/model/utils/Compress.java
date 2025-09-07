package com.hfw.model.utils;

import java.io.*;
import java.util.List;
import java.util.zip.*;

/**
 * 压缩工具
 * @author farkle
 * @date 2022-04-06
 */
public class Compress {
    private static final int  BUFFER_SIZE = 2 * 1024;

    /**
     * zip压缩文件/递归压缩目录
     * @param srcDir 文件/目录 路径
     * @param outFile 输出文件
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws IOException
     */
    public static void zip(String srcDir, String outFile, boolean KeepDirStructure) throws IOException{
        long start = System.currentTimeMillis();
        FileOutputStream fos = new FileOutputStream(outFile);
        ZipOutputStream zos = new ZipOutputStream(fos) ;
        try(fos; zos){
            File sourceFile = new File(srcDir);
            zip(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            //System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        }
    }

    /**
     * zip压缩多个文件
     * @param srcFiles 源文件数据
     * @param outFile 输出文件
     * @throws IOException
     */
    public static void zip(List<File> srcFiles , String outFile) throws IOException{
        long start = System.currentTimeMillis();
        FileOutputStream fis = new FileOutputStream(outFile);
        ZipOutputStream zos = new ZipOutputStream(fis);
        try(fis; zos){
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream is = new FileInputStream(srcFile);
                try(is){
                    while ((len = is.read(buf)) != -1){
                        zos.write(buf, 0, len);
                    }
                }
                zos.closeEntry();
            }
            long end = System.currentTimeMillis();
            //System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        }
    }

    /**
     * zip递归压缩方法
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void zip(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws IOException {
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            try(in){
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
            }
            // Complete the entry
            zos.closeEntry();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        zip(file, zos, name + File.separator + file.getName(),KeepDirStructure);
                    } else {
                        zip(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }

    /**
     * zip 压缩单个文件
     * @param src
     * @param out
     */
    public static void zip(String src, String out) throws IOException{
        FileInputStream fis = new FileInputStream(src);
        ZipOutputStream zos =  new ZipOutputStream(new FileOutputStream(out));
        try(fis; zos){
            zos.putNextEntry(new ZipEntry(StrUtil.filename(src)));
            IOUtil.copy(fis,zos);
            zos.closeEntry();
        }
    }
    /**
     * zip内存压缩
     * @param bytes
     * @param entry
     * @return
     * @throws IOException
     */
    public static byte[] zip(byte[] bytes, String entry) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(bos);
        try(bos; zos) {
            ZipEntry zipEntry = new ZipEntry(entry);
            zos.putNextEntry(zipEntry);
            zos.write(bytes);
            zos.closeEntry();
        }
        return bos.toByteArray();
    }

    /**
     * zip解压单个文件
     * @param src
     * @param out
     * @throws IOException
     */
    public static void unzip(String src, String out) throws IOException{
        ZipFile zipFile = new ZipFile(src);
        FileOutputStream fos = new FileOutputStream(out);
        InputStream is = zipFile.getInputStream(zipFile.entries().nextElement());
        try(zipFile; fos ; is){
            IOUtil.copy(is,fos);
        }
    }

    /**
     * zip内存解压
     * @param bytes
     * @return
     * @throws IOException
     */
    public static byte[] unzip(byte[] bytes)throws IOException{
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ZipInputStream zis = new ZipInputStream(bis);
        try(bis; zis){
            zis.getNextEntry();
            byte[] zipBytes = zis.readAllBytes();
            zis.closeEntry();
            return zipBytes;
        }
    }

    /**
     * gz压缩
     * @param srcPath
     * @throws IOException
     */
    public static void gz(String srcPath) throws IOException{
        FileInputStream fis = new FileInputStream(srcPath);
        FileOutputStream fos = new FileOutputStream(srcPath + ".gz");
        GZIPOutputStream gzos = new GZIPOutputStream(fos);
        try(fis; fos; gzos){
            IOUtil.copy(fis,gzos);
        }
    }
    /**
     * gz内存压缩
     * @param bytes
     * @return
     * @throws IOException
     */
    public static byte[] gz(byte[] bytes) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzos = new GZIPOutputStream(bos);
        try(bos; gzos){
            gzos.write(bytes);
        }
        return bos.toByteArray();
    }

    /**
     * gz解压
     * @param src
     * @throws IOException
     */
    public static void ungz(String src) throws IOException {
        FileInputStream fis = new FileInputStream(src);
        GZIPInputStream gz = new GZIPInputStream(fis);
        FileOutputStream os = new FileOutputStream(src.replace(".gz",""));
        try(fis; gz; os){
            IOUtil.copy(gz,os);
        }
    }
    /**
     * gz内存解压
     * @return
     * @throws IOException
     */
    public static byte[] ungz(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        GZIPInputStream gzis = new GZIPInputStream(bis);
        try(bis; gzis){
            return gzis.readAllBytes();
        }
    }

}
