package cn.edu.zzu.mysql.mapper;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户信息
 * Created by qinhao on 2018/4/27.
 */
public interface UserMapper {

    /**
     * 主键查询
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    User queryByPK(@Param("id") String userId) throws SQLException;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    User queryUser(@Param("username") String username, @Param("password") String password) throws SQLException;

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    List<User> query(PageInfo page) throws SQLException;

    Integer count(PageInfo page) throws SQLException;

    /**
     * 新增用户
     *
     * @param user
     * @return
     * @throws SQLException
     */
    Integer save(User user) throws SQLException;

    Integer saveRelation(@Param("userId") String userId, @Param("roleId") String roleId) throws SQLException;

    /**
     * 冻结用户
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    Integer update(@Param("userId") String userId, @Param("status") String status) throws SQLException;

}
