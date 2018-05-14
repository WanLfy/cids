package cn.edu.zzu.service;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.DeployHost;

import java.util.List;
import java.util.Map;

/**
 * 目标主机
 * Created by qinhao on 2018/5/14.
 */
public interface IDeployHostService {

    Map<String, Object> query(PageInfo page) throws Exception;

    Integer save(DeployHost host) throws Exception;
}
