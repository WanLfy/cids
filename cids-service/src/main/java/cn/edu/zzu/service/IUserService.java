package cn.edu.zzu.service;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.mysql.pojo.Role;
import cn.edu.zzu.mysql.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * 角色信息
 * Created by qinhao on 2018/4/27.
 */
public interface IUserService {

    User checkUser(String username, String password) throws Exception;

    List<Permission> getUserPermissions(String userId) throws Exception;

    Map<String, Object> query(PageInfo page) throws Exception;

    Map<String, String> roleSel() throws Exception;

    Integer save(User user, String roleId) throws Exception;

    Integer delete(String userId) throws Exception;

    Integer active(String userId) throws Exception;
}
