package cn.edu.zzu.controller.business.formBean;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.User;

/**
 * Created by qinhao on 2018/5/4.
 */
public class UserFormBean extends PageInfo<User> {

    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
