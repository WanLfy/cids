package cn.edu.zzu.service.impl;

import cn.edu.zzu.mysql.mapper.PermissionMapper;
import cn.edu.zzu.mysql.mapper.UserMapper;
import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.mysql.pojo.User;
import cn.edu.zzu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by qinhao on 2018/4/27.
 */
@Service
@Transactional(transactionManager = "mysqlTransactionManager", rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public User checkUser(String username, String password) throws Exception {
        return userMapper.queryUser(username, password);
    }

    @Override
    public List<Permission> getUserPermissions(String userId) throws Exception {
        return permissionMapper.queryByUser(userId);
    }
}
