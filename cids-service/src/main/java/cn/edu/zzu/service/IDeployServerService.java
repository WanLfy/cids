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

    /**
     * 启动服务器
     *
     * @param id
     * @return
     * @throws Exception
     */
    String start(Integer id) throws Exception;

    /**
     * 停止服务器
     *
     * @param id
     * @return
     * @throws Exception
     */
    String stop(Integer id) throws Exception;

    /**
     * 检查服务器启动结果
     *
     * @param id
     * @return
     * @throws Exception
     */
    String check(Integer id) throws Exception;

    /**
     * 服务器部署应用
     *
     * @param id
     * @return
     * @throws Exception
     */
    String deploy(Integer id) throws Exception;
}
