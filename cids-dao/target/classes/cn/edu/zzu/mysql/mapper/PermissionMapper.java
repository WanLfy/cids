package cn.edu.zzu.mysql.mapper;

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
}
