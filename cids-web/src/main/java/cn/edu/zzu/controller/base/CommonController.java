package cn.edu.zzu.controller.base;

import cn.edu.zzu.controller.Bean.Node;
import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.mysql.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取公共JSON数据类
 * Created by qinhao on 2018/5/3.
 */
@Controller
@RequestMapping(value = {"/common/"})
public class CommonController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    /**
     * 首页
     */
    @RequestMapping(value = "/main.htm")
    public String main() {
        return "common/main";
    }

    /**
     * 获取菜单json
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getMenuTree.json")
    @ResponseBody
    public List<Node> getMenuTree(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_KEY_USER);
        List<Permission> permissions = getUserMenu(user);
        return new Node().generaterNodes(permissions);
    }

    /**
     * 获取用户权限
     *
     * @param user
     * @return
     */
    private List<Permission> getUserMenu(User user) {
        List<Permission> permissions = user.getPermissions();
        for (Permission parent : permissions) {
            List<Permission> childPer = new ArrayList<Permission>();
            Integer parentId = parent.getPermissionId();
            for (Permission permission : permissions) {
                if (parentId == Integer.parseInt(permission.getParentId())) {
                    childPer.add(permission);
                }
            }
            parent.setChildPer(childPer);
        }
        return permissions;
    }

}
