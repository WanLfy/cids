package cn.edu.zzu.mysql.mapper;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.DeployHost;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * 目标主机
 * Created by qinhao on 2018/5/13.
 */
public interface DeployHostMapper {

    List<DeployHost> queryAll() throws SQLException;

    /**
     * 主键查询
     * @param id
     * @return
     * @throws SQLException
     */
    DeployHost queryByPK(@Param("id") Integer id) throws SQLException;

    /**
     * 分页查询
     *
     * @param page
     * @return
     * @throws SQLException
     */
    List<DeployHost> query(PageInfo page) throws SQLException;

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
     * @param page
     * @return
     * @throws SQLException
     */
    Integer save(DeployHost host) throws SQLException;
}
