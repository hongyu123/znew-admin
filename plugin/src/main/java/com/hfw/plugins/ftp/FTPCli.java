package com.hfw.plugins.ftp;

import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.StringTokenizer;

/**
 * FTP客户端
 * 待完善,连接池
 * @author farkle
 * @date 2022-04-07
 */
@Data
public class FTPCli {
    private static final int TIMEOUT = 30;

    //匿名登录
    private String ANONYMOUS_USER="anonymous";
    private String host = "127.0.0.1";
    private Integer port = 21;
    private String username;
    private String password;
    private String ftpPattern="passive";
    private String ftpEncode = "iso-8859-1";
    private String localEncode = "GBK";


    public FTPClient connect() throws IOException {
        return this.connect(TIMEOUT);
    }
    /**
     * 连接FTP Server
     * @throws IOException
     */
    public FTPClient connect(int timeout) throws IOException {
        FTPClient client = new FTPClient();
        //连接FTP Server
        client.setConnectTimeout(timeout*1000);
        client.connect(this.host, this.port);
        //登陆
        if(this.username==null||"".equals(this.username)){
            //使用匿名登陆
            client.login(ANONYMOUS_USER, ANONYMOUS_USER);
        } else{
            client.login(this.username, this.password);
        }
        //设置文件格式
        client.setFileType(FTPClient.BINARY_FILE_TYPE);
        //client.setControlEncoding(this.encode);
        if("passive".equals(this.ftpPattern)){
            client.enterLocalPassiveMode(); //被动模式
        }
        //获取FTP Server 应答
        int reply = client.getReplyCode();
        if(!FTPReply.isPositiveCompletion(reply)){
            client.disconnect();
            throw new RuntimeException("ftp:服务连接失败,"+this.host+":"+this.port+"无应答");
        }
        //log.info("ftp:服务连接成功,"+this.host+":"+this.port);
        return client;
    }
    private String filePath(String dir, String file){
        return dir+ File.separator+ file;
    }
    private String dirPath(String dir, String child){
        String dirPath = dir+ File.separator + child;
        File file = new File(dirPath);
        if(!file.exists()){
            file.mkdirs();
        }
        return dirPath;
    }
    private String remoteToLocaName(String name) throws UnsupportedEncodingException {
        return new String(name.getBytes(this.ftpEncode), this.localEncode);
    }
    private String localToRemoteName(String name) throws UnsupportedEncodingException{
        return new String(name.getBytes(this.localEncode), this.ftpEncode);
    }

    /**
     * 递归下载目录
     * @param ftpClient
     * @param dir ftp目录
     * @param localDir 本地目录
     * @return 返回下载成功的文件个数
     * @throws IOException
     */
    public int dirDownload(FTPClient ftpClient, String dir, String localDir) throws IOException {
        //如果服务器上没有此目录,返回数据长度为0
        FTPFile[] ftpFiles = ftpClient.listFiles(dir);
        if(ftpFiles==null || ftpFiles.length<=0){
            //log.info("ftp:server目录不存在或为空,"+dir);
            return 0;
        }
        int cnt = 0;
        for(FTPFile file : ftpFiles){
            if(file.isFile()){
                String localFile = filePath(localDir, remoteToLocaName(file.getName()));
                String remoteFile = filePath(dir, file.getName() );
                try(FileOutputStream fos = new FileOutputStream(localFile)){
                    Boolean res = ftpClient.retrieveFile(remoteFile, fos);
                    if(res){
                        //log.info("ftp:下载成功,ftpPath:"+remoteFile+",localPath:"+localFile);
                        cnt ++;
                    }else{
                        //log.error("ftp:下载失败,ftpPath:"+remoteFile+",localPath:"+localFile);
                        fos.close();
                        new File(localFile).delete();
                    }
                }
            }else if(file.isDirectory()){
                if(!".".equals(file.getName()) && !"..".equals(file.getName())){
                    String localDirName = remoteToLocaName(file.getName());
                    cnt += dirDownload(ftpClient, dirPath(dir, file.getName()), dirPath(localDir, localDirName));
                }
            }

        }
        return cnt;
    }
    /**
     * 断开FTP连接
     * @param ftpClient
     * @throws IOException
     */
    public void close(FTPClient ftpClient) throws IOException{
        if(ftpClient!=null && ftpClient.isConnected()){
            ftpClient.logout();
            ftpClient.disconnect();
        }
        //log.info("ftp:断开连接"+this.host+":"+this.port);
    }
    public void upload(String remote, String localFile) throws IOException {
        File file = new File(localFile);
        if(!file.exists()){
            //log.error("ftp:上传失败,文件不存在:localPath:"+localFile);
            return ;
        }
        FTPClient ftpClient = null;
        FileInputStream fis = null;
        try{
            ftpClient = connect(30);
            fis = new FileInputStream(localFile);
            ftpClient.makeDirectory(remote);
            //若FTP服务器路径不存在会上传失败
            String remoteFile = localToRemoteName( filePath(remote, file.getName()) );
            boolean result = ftpClient.storeFile(remoteFile, fis);
            if(result){
                //log.info("ftp:上传成功,ftpPath:"+remote+",localPath:"+localFile);
            }else{
                //log.error("ftp:上传失败,ftpPath:"+remote+",localPath:"+localFile);
            }
        }finally {
            if(fis!=null){
                fis.close();
            }
            close(ftpClient);
        }
    }

    /**
     * 切换工作目录，远程目录不存在时,创建目录
     * @param client
     * @throws IOException
     */
    private void changeWorkingDirectory(FTPClient client, String ftpPath) throws IOException{
        if(ftpPath==null || "".equals(ftpPath)){
            throw new IllegalArgumentException("ftp Path is blank");
        }
        Boolean ok = client.changeWorkingDirectory(ftpPath);
        if(!ok){
            //ftpPath 不存在，手动创建目录
            StringTokenizer token = new StringTokenizer(ftpPath,"\\//");
            while(token.hasMoreTokens()){
                String path = token.nextToken();
                client.makeDirectory(path);
                client.changeWorkingDirectory(path);
            }
        }
    }

    /**
     * 列出当前目录下的所有文件
     * @throws IOException
     */
    public void listFiles(String ftpPath) throws IOException {
        FTPClient ftpClient = null;
        try{
            ftpClient = connect(30);
            FTPFile[] ftpFiles = ftpClient.listFiles(ftpPath);
            for(FTPFile file : ftpFiles){
                String info = file.getName()+","+file.getSize()+","+file.isFile();
                System.out.println(info);
            }
        }finally {
            if(ftpClient!=null){
                close(ftpClient);
            }
        }
    }

    /**
     * 递归列出目录结构
     * @param ftpClient
     * @param path
     * @param tab
     * @throws Exception
     */
    public void list(FTPClient ftpClient, String path, String tab) throws Exception{
        FTPFile[] ftpFiles = ftpClient.listFiles(path);
        for(FTPFile file : ftpFiles){
            if(file.isDirectory()){
                if(".".equals(file.getName()) || "..".equals(file.getName())){
                    continue;
                }
                System.out.println(tab+"["+file.getName()+"]");
                list(ftpClient, path+"/"+file.getName(), tab+"\t");
            }else{
                System.out.println(tab+file.getName());
            }
        }
    }

    /**
     * 下载文件
     * @param remote ftp文件路径
     * @param localPath 本地下载路径
     * @return
     * @throws IOException
     */
    public boolean download(String remote, String localPath) throws IOException {
        FTPClient ftpClient = null;
        FileOutputStream fos = null;
        try{
            ftpClient = connect(30);
            if(ftpClient.getStatus(remote)==null){
                //log.error(remote+":文件不存在");
                return false;
            }else{
                fos = new FileOutputStream(localPath);
                return ftpClient.retrieveFile(remote, fos);
            }
        }finally {
            if(fos!=null){
                fos.close();
            }
            close(ftpClient);
        }
    }

    /**
     * 删除文件
     * @param remote ftp文件目录
     * @return
     * @throws IOException
     */
    public boolean delFile(String remote) throws IOException {
        FTPClient ftpClient = null;
        try{
            ftpClient = connect(30);
            return ftpClient.deleteFile(remote);
        }finally {
            close(ftpClient);
        }
    }

}
