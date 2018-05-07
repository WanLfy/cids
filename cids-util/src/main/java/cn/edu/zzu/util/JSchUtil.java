package cn.edu.zzu.util;

import cn.edu.zzu.Bean.FileProgressMonitor;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ssh2连接linux工具类
 * Created by qinhao on 2018/5/7.
 */
public class JSchUtil {

    private static final Logger logger = LoggerFactory.getLogger(JSchUtil.class);

    //连接信息
    private static final int SSH_PORT = 22;
    private String user;
    private String passwd;
    private String host;
    private int connectTimeout;

    private Session session;
    private JSch jSch = new JSch();

    public JSchUtil(String user, String passwd, String host, int connectTimeout) {
        this.user = user;
        this.passwd = passwd;
        this.host = host;
        this.connectTimeout = connectTimeout;
    }

    /**
     * 建立连接,获取session
     *
     * @return
     * @throws JSchException
     */
    public void connect() {
        try {
            session = jSch.getSession(user, host, SSH_PORT);
            session.setPassword(passwd);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(connectTimeout);
            session.connect();
        } catch (JSchException e) {
            logger.error("ssh2连接失败：" + e.getMessage());
        }
    }

    /**
     * 关闭连接
     */
    public void disConnect() {
        if (session != null) {
            session.disconnect();
            logger.error("ssh2连接关闭");
        }
    }

    /**
     * 检查主机连通性
     *
     * @return
     */
    public boolean check() {
        boolean result = false;
        if (session != null) {
            Channel channel = null;
            try {
                channel = session.openChannel("exec");
                result = channel.getExitStatus() == 0 ? false : true;
                channel.disconnect();
            } catch (JSchException e) {
                logger.error("ssh2连接失败：" + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 执行shell命令
     *
     * @param cmds
     * @return
     */
    public String execCmd(String cmds) {
        StringBuilder stringBuilder = new StringBuilder();
        if (session != null) {
            Channel channel = null;
            try {
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(cmds);
                InputStream inputStream = channel.getInputStream();
                channel.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                channel.disconnect();
            } catch (Exception e) {
                logger.error("ssh2执行(" + cmds + ")命令失败" + e.getMessage());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 修改文件名称
     *
     * @param oldName
     * @param newName
     */
    public void modifyFileName(String oldName, String newName) {
        if (session != null) {
            try {
                ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect();
                try {
                    //先删除文件
                    channelSftp.rm(newName);
                } finally {
                    //后修改文件名称
                    channelSftp.rename(oldName, newName);
                }
                channelSftp.quit();
            } catch (Exception e) {
                logger.error("ssh2修改文件名称失败" + e.getMessage());
            }
        }
    }

    /**
     * 远程下载文件
     *
     * @param remoteFile
     * @param localFile
     */
    public void download(String remoteFile, String localFile) throws Exception {
        if (session != null) {
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            // 监控下载流程
            channelSftp.get(remoteFile, localFile, new FileProgressMonitor(), ChannelSftp.OVERWRITE);
            channelSftp.quit();
        } else {
            throw new Exception("下载:连接主机失败");
        }
    }

    /**
     * 远程上传文件
     *
     * @param localFile
     * @param remoteFile
     * @throws Exception
     */
    public void upload(String localFile, String remoteFile) throws Exception {
        if (session != null) {
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.put(localFile, remoteFile, ChannelSftp.OVERWRITE);
            channelSftp.quit();
        } else {
            throw new Exception("上传:连接主机失败");
        }
    }
}
