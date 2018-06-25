package cn.edu.zzu.controller.business;

import cn.edu.zzu.controller.Bean.Message;
import cn.edu.zzu.controller.base.BaseController;
import cn.edu.zzu.controller.business.formBean.PermissionFormBean;
import cn.edu.zzu.controller.business.formBean.RoleFormBean;
import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.service.IPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinhao on 2018/5/20.
 */
@Controller
@RequestMapping(value = {"/permission/"})
public class PermissionController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private IPermissionService permissionService;

    /**
     * 查询页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.GET)
    public String queryPage() {
        return "business/permission/list";
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
    public Object query(PermissionFormBean formBean, Model model) {
        try {
            Map<String, Object> result = permissionService.query(formBean);
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
            map = permissionService.permissionSel();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        model.addAttribute("permissionsMap", map);
        return "business/permission/save";
    }

    /**
     * 新增
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "save.htm")
    public String save(Permission permission) {
        try {
            permissionService.save(permission);
            Message.setNotice("新增权限成功");
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
    public String delete(@RequestParam("permissionId") Integer permissionId) {
        try {
            permissionService.delete(permissionId);
            Message.setNotice("删除权限成功");
        } catch (Exception e) {
            Message.setError(e.getMessage());
        }
        return queryPage();
    }
}
