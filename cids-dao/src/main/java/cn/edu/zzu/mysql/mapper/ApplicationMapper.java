package cn.edu.zzu.mysql.mapper;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Application;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;
import java.util.List;

/**
 * jenkins应用
 * Created by qinhao on 2018/5/7.
 */
public interface ApplicationMapper {

    List<Application> queryAll() throws SQLException;

    /**
     * 分页查询
     *
     * @param page
     * @return
     * @throws SQLException
     */
    List<Application> query(PageInfo page) throws SQLException;

    /**
     * 计数
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Integer count(PageInfo page) throws SQLException;

    /**
     * 新增
     *
     * @param application
     * @return
     * @throws SQLException
     */
    int save(Application application) throws SQLException;

    /**
     * 更新
     *
     * @param application
     * @return
     * @throws SQLException
     */
    int update(Application application) throws SQLException;

}
