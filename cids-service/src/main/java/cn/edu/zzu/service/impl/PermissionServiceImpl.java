package cn.edu.zzu.service.impl;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.mapper.PermissionMapper;
import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinhao on 2018/5/20.
 */
@Service
@Transactional(transactionManager = "mysqlTransactionManager", rollbackFor = Exception.class)
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Map<String, Object> query(PageInfo page) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", permissionMapper.query(page));
        map.put("size", permissionMapper.count(page));
        return map;

    }

    @Override
    public Integer save(Permission permission) throws Exception {
        return permissionMapper.save(permission);
    }

    @Override
    public Integer delete(Integer permissionId) throws Exception {
        Integer count = permissionMapper.countPermissionRole(permissionId);
        if (count > 0) {
            throw new Exception("删除失败,该权限存在绑定角色");
        }
        permissionMapper.deleteRelation(permissionId);
        return permissionMapper.delete(permissionId);
    }

    @Override
    public Map<String, String> permissionSel() throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        List<Permission> list = permissionMapper.queryAll();
        for (Permission per : list) {
            map.put(per.getPermissionId() + "", per.getPermissionName());
        }
        return map;
    }
}
