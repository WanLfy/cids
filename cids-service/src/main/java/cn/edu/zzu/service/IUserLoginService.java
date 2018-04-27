package cn.edu.zzu.service;

import cn.edu.zzu.mysql.pojo.User;

/**
 * Created by qinhao on 2018/4/27.
 */
public interface IUserLoginService {

    User checkUser(String username, String password) throws Exception;
}
