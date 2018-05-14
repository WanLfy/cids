package cn.edu.zzu.service.impl;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.mapper.DeployHostMapper;
import cn.edu.zzu.mysql.pojo.DeployHost;
import cn.edu.zzu.service.IDeployHostService;
import cn.edu.zzu.util.JSchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinhao on 2018/5/14.
 */
@Service
@Transactional(transactionManager = "mysqlTransactionManager", rollbackFor = Exception.class)
public class DeployHostServiceImpl implements IDeployHostService {

    @Autowired
    private DeployHostMapper deployHostMapper;


    @Override
    public Map<String, Object> query(PageInfo page) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("list", deployHostMapper.query(page));
        map.put("size", deployHostMapper.count(page));
        return map;
    }

    @Override
    public Integer save(DeployHost host) throws Exception {
        JSchUtil ssh = new JSchUtil(host.getLoginName(), host.getPassword(), host.getHostIP(), 3000);
        ssh.connect();
        if (!ssh.check()) {
            throw new Exception("添加失败,不能与主机建立连接");
        }
        ssh.disConnect();
        return deployHostMapper.save(host);
    }
}
