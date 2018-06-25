package cn.edu.zzu.service;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Permission;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by qinhao on 2018/5/20.
 */
public interface IPermissionService {

    Map<String, Object> query(PageInfo page) throws Exception;

    Integer save(Permission permission) throws Exception;

    Integer delete(Integer permissionId) throws Exception;

    Map<String, String> permissionSel() throws Exception;
}
