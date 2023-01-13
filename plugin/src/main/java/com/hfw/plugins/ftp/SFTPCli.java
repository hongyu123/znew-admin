package com.hfw.plugins.ftp;

import com.jcraft.jsch.*;
import lombok.Data;

import java.util.Properties;
import java.util.Vector;

/**
 * SFTP客户端
 * 待完善,连接池
 * @author farkle
 * @date 2022-04-07
 */
@Data
public class SFTPCli {
    private static final int TIMEOUT = 30;
    private String host = "127.0.0.1";
    private int port = 22;
    private String username;
    private String password;

    public Session getSession() throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        return session;
    }
    public ChannelSftp connect(Session session) throws JSchException {
        return this.connect(session,TIMEOUT);
    }
    public ChannelSftp connect(Session session, int timeout) throws JSchException {
        if(session == null){
            return null;
        }
        session.connect(timeout*1000);
        Channel channel = session.openChannel("sftp");
        channel.connect(timeout*1000);
        return (ChannelSftp) channel;
    }
    public void close(ChannelSftp channelSftp){
        if(channelSftp==null || !channelSftp.isConnected()){
            return ;
        }
        channelSftp.disconnect();
    }
    public void close(Session session) {
        if(session==null || !session.isConnected()){
            return ;
        }
        session.disconnect();
    }

    public void download(ChannelSftp channelSftp, String remoteFile, String localPath) throws SftpException {
        channelSftp.get(remoteFile, localPath);
    }

    /**
     * sftp下载文件
     * @param remoteFile sftp文件路径
     * @param localPath 本地文件路径
     * @throws JSchException
     * @throws SftpException
     */
    public void download(String remoteFile, String localPath) throws JSchException, SftpException {
        Session session = null;
        ChannelSftp channelSftp = null;
        try{
            session = this.getSession();
            channelSftp = this.connect(session, 30);
            this.download(channelSftp, remoteFile,localPath);
        }finally {
            this.close(session);
            this.close(channelSftp);
        }
    }

    public static void sftp()throws Exception{
        SFTPCli cli = new SFTPCli();
        cli.setHost("192.168.48.130");
        cli.setUsername("mysftp");
        cli.setPassword("mysftp123");
        Session session = null;
        ChannelSftp channelSftp = null;
        try{
            session = cli.getSession();
            channelSftp = cli.connect(session ,30);
            channelSftp.get("/upload/design.txt","D://tmp/my.txt");
            //channelSftp.mkdir("/upload/test01");
            Vector vector = channelSftp.ls("/upload/");
            for (int i = 0; i < vector.size(); i++) {
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) vector.get(i);
                System.out.println(lsEntry.getFilename());
            }
        }finally {
            cli.close(session);
            cli.close(channelSftp);
        }
    }

}
