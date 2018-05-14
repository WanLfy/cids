package cn.edu.zzu.service;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.DeployHost;
import cn.edu.zzu.mysql.pojo.DeployServer;

import java.util.List;
import java.util.Map;

/**
 * 部署应用服务器
 * Created by qinhao on 2018/5/14.
 */
public interface IDeployServerService {

    Map<String, Object> query(PageInfo page) throws Exception;

    Integer save(DeployServer server) throws Exception;

    Map<String, String> hostSel() throws Exception;

    Map<String, String> appSel() throws Exception;
}
