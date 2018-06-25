package cn.edu.zzu.service.impl;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.mapper.PermissionMapper;
import cn.edu.zzu.mysql.mapper.RoleMapper;
import cn.edu.zzu.mysql.mapper.UserMapper;
import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.mysql.pojo.Role;
import cn.edu.zzu.mysql.pojo.User;
import cn.edu.zzu.service.IUserService;
import com.sun.tracing.dtrace.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User checkUser(String username, String password) throws Exception {
        return userMapper.queryUser(username, password);
    }

    @Override
    public List<Permission> getUserPermissions(String userId) throws Exception {
        return permissionMapper.queryByUser(userId);
    }

    @Override
    public Map<String, Object> query(PageInfo page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", userMapper.query(page));
        map.put("size", userMapper.count(page));
        return map;
    }

    @Override
    public Map<String, String> roleSel() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<Role> list = roleMapper.queryAll();
        for (Role role : list) {
            map.put(role.getRoleId() + "", role.getRoleName());
        }
        return map;
    }

    @Override
    public Integer save(User user, String roleId) throws Exception {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        user.setUserId(id);
        userMapper.saveRelation(id, roleId);
        return userMapper.save(user);
    }

    @Override
    public Integer delete(String userId) throws Exception {
        return userMapper.update(userId, "2");
    }

    @Override
    public Integer active(String userId) throws Exception {
        return userMapper.update(userId, "0");
    }
}
