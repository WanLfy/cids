package cn.edu.zzu.service;

import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.mysql.pojo.User;

import java.util.List;

/**
 * Created by qinhao on 2018/4/27.
 */
public interface IUserService {

    User checkUser(String username, String password) throws Exception;

    List<Permission> getUserPermissions(String userId) throws Exception;
}
