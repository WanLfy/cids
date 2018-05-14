package cn.edu.zzu.mysql.mapper;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.DeployServer;

import java.sql.SQLException;
import java.util.List;

/**
 * 部署应用服务器
 * Created by qinhao on 2018/5/14.
 */
public interface DeployServerMapper {

    List<DeployServer> query(PageInfo page) throws SQLException;

    Integer count(PageInfo page) throws SQLException;

    Integer save(DeployServer server) throws SQLException;
}
