package cn.edu.zzu.controller.business.formBean;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Permission;

/**
 * Created by qinhao on 2018/5/20.
 */
public class PermissionFormBean extends PageInfo<Permission> {

    private String permissionName;
    private String permissionDesc;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }
}
