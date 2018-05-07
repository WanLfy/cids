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

    List<Application> query(PageInfo page) throws SQLException;

    Integer count(PageInfo page) throws SQLException;

    int save(Application application) throws SQLException;

    int update(Application application) throws SQLException;

}
