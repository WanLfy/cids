package cn.edu.zzu.service;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Application;

import java.util.List;
import java.util.Map;

/**
 * 应用
 * Created by qinhao on 2018/5/7.
 */
public interface IApplicationService {

    Integer saveJobsInfo() throws Exception;

    Map<String, Object> query(PageInfo page) throws Exception;

    List<String> getViewNames() throws Exception;

    String build(String appName) throws Exception;

    String queryBuild(String appName) throws Exception;
}
