package cn.edu.zzu.service.impl;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.mapper.PermissionMapper;
import cn.edu.zzu.mysql.mapper.RoleMapper;
import cn.edu.zzu.mysql.pojo.DeployHost;
import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.mysql.pojo.Role;
import cn.edu.zzu.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinhao on 2018/5/20.
 */
@Service
@Transactional(transactionManager = "mysqlTransactionManager", rollbackFor = Exception.class)
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Map<String, Object> query(PageInfo page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Role> list = roleMapper.query(page);
        for (Role role : list) {
            role.setPermissions(permissionMapper.queryByRole(role.getRoleId()));
        }
        map.put("list", list);
        map.put("size", roleMapper.count(page));
        return map;
    }

    @Override
    public Integer save(Role role, Integer[] permissions) throws Exception {
        for (Integer permissionId : permissions) {
            roleMapper.saveRelation(role.getRoleId(), permissionId);
        }
        return roleMapper.save(role);
    }

    @Override
    public Integer delete(String roleId) throws Exception {
        int count = roleMapper.countRoleUser(roleId);
        if (count > 0) {
            throw new Exception("删除失败,该角色存在绑定用户");
        }
        roleMapper.deleteRelation(roleId);
        return roleMapper.delete(roleId);
    }

    @Override
    public Map<String, String> permissionSel() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<Permission> list = permissionMapper.queryAll();
        for (Permission per : list) {
            map.put(per.getPermissionId() + "", per.getPermissionName());
        }
        return map;
    }
}
