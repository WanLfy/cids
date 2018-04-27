package cn.edu.zzu.mysql.mapper;

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

}
