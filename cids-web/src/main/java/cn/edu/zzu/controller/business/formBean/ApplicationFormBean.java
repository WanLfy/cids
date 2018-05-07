package cn.edu.zzu.controller.business.formBean;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Application;

/**
 * Created by qinhao on 2018/5/7.
 */
public class ApplicationFormBean extends PageInfo<Application> {

    private String appName;
    private String viewName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
