package cn.edu.zzu.mysql.mapper;

import cn.edu.zzu.mysql.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;

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
}
