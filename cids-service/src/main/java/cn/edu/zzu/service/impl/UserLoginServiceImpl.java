package cn.edu.zzu.service.impl;

import cn.edu.zzu.mysql.mapper.UserMapper;
import cn.edu.zzu.mysql.pojo.User;
import cn.edu.zzu.service.IUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by qinhao on 2018/4/27.
 */
@Service
@Transactional(transactionManager = "mysqlTransactionManager", rollbackFor = Exception.class)
public class UserLoginServiceImpl implements IUserLoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) throws Exception {
        return userMapper.queryUser(username, password);
    }
}
