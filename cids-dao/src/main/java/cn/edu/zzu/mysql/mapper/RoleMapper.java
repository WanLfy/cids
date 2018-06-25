package cn.edu.zzu.mysql.mapper;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * 角色信息
 * Created by qinhao on 2018/4/27.
 */
public interface RoleMapper {

    /**
     * 分页查询
     *
     * @param page
     * @return
     * @throws SQLException
     */
    List<Role> query(PageInfo page) throws SQLException;

    Integer count(PageInfo page) throws SQLException;

    /**
     * 主键查询
     *
     * @param roleId
     * @return
     * @throws SQLException
     */
    List<Role> queryByPK(@Param("roleId") String roleId) throws SQLException;

    /**
     * 查询角色拥有权限
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    List<Role> queryByUser(@Param("userId") String userId) throws SQLException;

    /**
     * 新增
     *
     * @param role
     * @return
     * @throws SQLException
     */
    Integer save(Role role) throws SQLException;

    Integer saveRelation(@Param("roleId") String roleId, @Param("permissionId") Integer permissionId) throws SQLException;

    /**
     * 删除
     *
     * @param roleId
     * @return
     * @throws SQLException
     */
    Integer delete(@Param("roleId") String roleId) throws SQLException;

    Integer deleteRelation(@Param("roleId") String roleId) throws SQLException;

    Integer countRoleUser(@Param("roleId") String roleId) throws SQLException;

    List<Role> queryAll() throws SQLException;
}
