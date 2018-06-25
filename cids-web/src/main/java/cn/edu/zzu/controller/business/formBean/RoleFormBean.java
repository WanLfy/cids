package cn.edu.zzu.controller.business.formBean;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.Role;

/**
 * Created by qinhao on 2018/5/20.
 */
public class RoleFormBean extends PageInfo<Role> {

    private String roleName;
    private String roleDesc;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
