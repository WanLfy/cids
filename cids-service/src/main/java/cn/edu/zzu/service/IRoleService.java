package cn.edu.zzu.service;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色信息
 * Created by qinhao on 2018/5/20.
 */
public interface IRoleService {

    Map<String, Object> query(PageInfo page) throws Exception;

    Integer save(Role role, Integer[] permissions) throws Exception;

    Integer delete(String roleId) throws Exception;

    Map<String, String> permissionSel() throws Exception;
}
