package cn.edu.zzu.controller.business;

import cn.edu.zzu.controller.Bean.Message;
import cn.edu.zzu.controller.base.BaseController;
import cn.edu.zzu.controller.business.formBean.RoleFormBean;
import cn.edu.zzu.mysql.pojo.Role;
import cn.edu.zzu.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinhao on 2018/5/20.
 */
@Controller
@RequestMapping(value = {"/role/"})
public class RoleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private IRoleService roleService;

    /**
     * 查询页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.GET)
    public String queryPage() {
        return "business/role/list";
    }

    /**
     * 查询
     *
     * @param formBean
     * @param model
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.POST)
    @ResponseBody
    public Object query(RoleFormBean formBean, Model model) {
        try {
            Map<String, Object> result = roleService.query(formBean);
            formBean.setData(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return queryPage();
        }
        return formBean;
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "savePage.htm")
    public String savePage(Model model) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            map = roleService.permissionSel();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        model.addAttribute("permissionsMap", map);
        return "business/role/save";
    }

    /**
     * 新增
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "save.htm")
    public String save(@RequestParam("permissionId") Integer[] permissions, Role role) {
        try {
            roleService.save(role, permissions);
            Message.setNotice("新增角色成功");
        } catch (Exception e) {
            Message.setError(e.getMessage());
        }
        return queryPage();
    }

    /**
     * 删除用户
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "delete.htm")
    public String delete(@RequestParam("roleId") String roleId) {
        try {
            roleService.delete(roleId);
            Message.setNotice("删除角色成功");
        } catch (Exception e) {
            Message.setError(e.getMessage());
        }
        return queryPage();
    }
}
