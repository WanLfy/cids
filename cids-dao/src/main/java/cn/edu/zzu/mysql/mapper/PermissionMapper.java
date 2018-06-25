package cn.edu.zzu.mysql.mapper;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * 权限信息
 * Created by qinhao on 2018/4/27.
 */
public interface PermissionMapper {

    /**
     * 查询角色拥有权限
     *
     * @param roleId
     * @return
     * @throws SQLException
     */
    List<Permission> queryByRole(@Param("roleId") String roleId) throws SQLException;

    /**
     * 查询用户拥有权限
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    List<Permission> queryByUser(@Param("userId") String userId) throws SQLException;

    List<Permission> queryAll() throws SQLException;

    /**
     * 分页查询
     *
     * @param page
     * @return
     * @throws SQLException
     */
    List<Permission> query(PageInfo page) throws SQLException;

    Integer count(PageInfo page) throws SQLException;

    /**
     * 新增
     *
     * @param permission
     * @return
     */
    Integer save(Permission permission);

    /**
     * 删除
     *
     * @param permissionId
     * @return
     */
    Integer delete(@Param("permissionId") Integer permissionId);

    Integer deleteRelation(@Param("permissionId") Integer permissionId);

    Integer countPermissionRole(@Param("permissionId") Integer permissionId);
}
