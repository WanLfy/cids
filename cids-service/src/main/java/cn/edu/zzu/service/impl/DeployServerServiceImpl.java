package cn.edu.zzu.service.impl;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.mapper.ApplicationMapper;
import cn.edu.zzu.mysql.mapper.DeployHostMapper;
import cn.edu.zzu.mysql.mapper.DeployServerMapper;
import cn.edu.zzu.mysql.pojo.Application;
import cn.edu.zzu.mysql.pojo.DeployHost;
import cn.edu.zzu.mysql.pojo.DeployServer;
import cn.edu.zzu.service.IConfigService;
import cn.edu.zzu.service.IDeployServerService;
import cn.edu.zzu.util.HttpConnectUtil;
import cn.edu.zzu.util.JSchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinhao on 2018/5/14.
 */
@Service
@Transactional(transactionManager = "mysqlTransactionManager", rollbackFor = Exception.class)
public class DeployServerServiceImpl implements IDeployServerService {

    private static final String CMD_MESS_FIALED = "FAILED";
    private static final String CMD_MESS_ERROR = "ERROR";
    private static final String CMD_MESS_STARTUP = "STARTUP";
    private static final String CMD_MESS_STARTED = "STARTED";
    private static final String CMD_MESS_STOPPED = "STOPPED";
    @Autowired
    private DeployServerMapper deployServerMapper;
    @Autowired
    private DeployHostMapper deployHostMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private IConfigService configService;

    @Override
    public Map<String, Object> query(PageInfo page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DeployServer> list = deployServerMapper.query(page);
        for (DeployServer server : list) {
            //TODO:线程池
            DeployHost host = deployHostMapper.queryByPK(server.getHostId());
            String url = server.getProtocol() + "://" + host.getHostIP() + ":" + server.getPort() + server.getContextPath();
            server.setAddress(url);
            server.setStatus(HttpConnectUtil.getResponseCode(server.getProtocol(), url));
        }
        map.put("list", list);
        map.put("size", deployServerMapper.count(page));
        return map;
    }

    @Override
    public Integer save(DeployServer server) throws Exception {
        DeployHost host = deployHostMapper.queryByPK(server.getHostId());
        JSchUtil ssh = new JSchUtil(host.getLoginName(), host.getPassword(), host.getHostIP(), 3000);
        ssh.connect();
        if (!ssh.check()) {
            throw new Exception("添加失败,不能与主机建立连接");
        }
        String cmd = "file " + server.getStoragePath();
        String cmdRS = ssh.execCmd(cmd);
        if (!isexist(cmdRS)) {
            throw new Exception("添加失败,不能找到指定部署路径");
        }
        //上传可执行脚本
        String shellPath = System.getProperty("config.home.dir") + "/cids_configs/shell/";
        try {
            File file1 = new File(shellPath + "start-ser.sh");
            ssh.upload(file1.getAbsolutePath(), server.getStoragePath());
            File file2 = new File(shellPath + "check-ser.sh");
            ssh.upload(file2.getAbsolutePath(), server.getStoragePath());
            File file3 = new File(shellPath + "stop-ser.sh");
            ssh.upload(file3.getAbsolutePath(), server.getStoragePath());
            //增加可执行权限
            ssh.execCmd("chmod +x " + server.getStoragePath() + "/start-ser.sh");
            ssh.execCmd("chmod +x " + server.getStoragePath() + "/check-ser.sh");
            ssh.execCmd("chmod +x " + server.getStoragePath() + "/stop-ser.sh");
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        } finally {
            ssh.disConnect();
        }
        return deployServerMapper.save(server);
    }

    @Override
    public Map<String, String> hostSel() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<DeployHost> list = deployHostMapper.queryAll();
        for (DeployHost host : list) {
            map.put(host.getHostId() + "", host.getHostName());
        }
        return map;
    }

    @Override
    public Map<String, String> appSel() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<Application> list = applicationMapper.queryAll();
        for (Application app : list) {
            map.put(app.getAppId() + "", app.getAppName());
        }
        return map;
    }

    @Override
    public String start(Integer id) throws Exception {
        String result = CMD_MESS_ERROR;
        DeployServer server = deployServerMapper.queryByPK(id);
        DeployHost host = deployHostMapper.queryByPK(server.getHostId());
        JSchUtil ssh = new JSchUtil(host.getLoginName(), host.getPassword(), host.getHostIP(), 3000);
        ssh.connect();
        if (!ssh.check()) {
            throw new Exception("启动失败,不能与主机建立连接");
        }
        //格式：/home/user/jboss/start-ser.sh /home/user/jboss
        String cmd = server.getStoragePath() + "/start-ser.sh " + server.getStoragePath();
        String cmdRS = ssh.execCmd(cmd);
        if ("".equals(cmdRS)) {
            result = CMD_MESS_STARTUP;
        }
        ssh.disConnect();
        return result;
    }

    @Override
    public String stop(Integer id) throws Exception {
        String result = CMD_MESS_ERROR;
        DeployServer server = deployServerMapper.queryByPK(id);
        DeployHost host = deployHostMapper.queryByPK(server.getHostId());
        JSchUtil ssh = new JSchUtil(host.getLoginName(), host.getPassword(), host.getHostIP(), 3000);
        ssh.connect();
        if (!ssh.check()) {
            throw new Exception("停止失败,不能与主机建立连接");
        }
        //格式：/home/user/jboss/stop-ser.sh /home/user/jboss 8080
        String cmd = server.getStoragePath() + "/stop-ser.sh " + server.getStoragePath() + " " + server.getPort();
        String cmdRS = ssh.execCmd(cmd);
        if (CMD_MESS_STOPPED.equals(cmdRS)) {
            result = CMD_MESS_STOPPED;
        }
        ssh.disConnect();
        return result;
    }

    @Override
    public String check(Integer id) throws Exception {
        String result = CMD_MESS_ERROR;
        DeployServer server = deployServerMapper.queryByPK(id);
        DeployHost host = deployHostMapper.queryByPK(server.getHostId());
        JSchUtil ssh = new JSchUtil(host.getLoginName(), host.getPassword(), host.getHostIP(), 3000);
        ssh.connect();
        if (!ssh.check()) {
            throw new Exception("检查失败,不能与主机建立连接");
        }
        //格式：/home/user/jboss/check-ser.sh /home/user/jboss 8080
        String cmd = server.getStoragePath() + "/check-ser.sh " + server.getStoragePath() + " " + server.getPort();
        String cmdRS = ssh.execCmd(cmd);
        if (CMD_MESS_FIALED.equals(cmdRS)) {
            result = CMD_MESS_FIALED;
        } else if (CMD_MESS_STARTUP.equals(cmdRS)) {
            result = CMD_MESS_STARTUP;
        } else if (CMD_MESS_STARTED.equals(cmdRS)) {
            result = CMD_MESS_STARTED;
        }
        ssh.disConnect();
        return result;
    }

    @Override
    public String deploy(Integer id) throws Exception {
        String result = "SUCCESS";
        String jenkinsIP = configService.getValue("jenkins_host_ip");
        DeployServer server = deployServerMapper.queryByPK(id);
        DeployHost host = deployHostMapper.queryByPK(server.getHostId());
        Application application = applicationMapper.queryByPK(server.getAppId());
        JSchUtil ssh = new JSchUtil(host.getLoginName(), host.getPassword(), host.getHostIP(), 3000);
        ssh.connect();
        if (!ssh.check()) {
            throw new Exception("部署失败,不能与主机建立连接");
        }
        if (jenkinsIP.equals(host.getHostIP())) {
            String cmd = "cp -rf " + application.getStoragePath() + " " + server.getStoragePath() + "/standalone/deployments";
            String cmdRS = ssh.execCmd(cmd);
            if (!"".equals(cmdRS)) {
                throw new Exception("同一主机下,应用部署失败");
            }
        } else {
            try {
                String projectPath = System.getProperty("user.dir");
                ssh.download(application.getStoragePath(), projectPath);
                ssh.upload(projectPath + "/" + suffixWar(application.getStoragePath()), server.getStoragePath() + "/standalone/deployments");
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        ssh.disConnect();
        return result;
    }

    /**
     * 解析cmd结果
     *
     * @param cmdRS
     * @return
     */
    private boolean isexist(String cmdRS) {
        String array[] = cmdRS.split(":");
        String rs = array[array.length - 1];
        if (rs != null && !rs.isEmpty() && rs.trim().indexOf("cannot") == -1) {
            if (rs.trim().equals("directory")) {
                return true;
            }
        }
        return false;
    }

    private String suffixWar(String name) {
        return name.substring(name.lastIndexOf("/"));
    }
}
