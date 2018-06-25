package cn.edu.zzu.controller.business;

import cn.edu.zzu.controller.Bean.Message;
import cn.edu.zzu.controller.base.BaseController;
import cn.edu.zzu.controller.business.formBean.PermissionFormBean;
import cn.edu.zzu.controller.business.formBean.UserFormBean;
import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.mysql.pojo.User;
import cn.edu.zzu.service.IUserService;
import cn.edu.zzu.util.Constants;
import cn.edu.zzu.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 用户登录
 * Created by qinhao on 2018/4/26.
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping(value = "login.htm")
    public String loginPage() {
        return "user/login";
    }


    /**
     * 登出
     */
    @RequestMapping(value = "logout.htm")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_KEY_USER);
        return "redirect:login.htm";
    }

    /**
     * 主页
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "home.htm")
    public String home(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SESSION_KEY_USER);
        model.addAttribute("user", user);
        return "user/home";
    }

    /**
     * 用户登录
     *
     * @param request
     * @param model
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "loginSubmit.htm", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, Model model,
                          @RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password) {
        Locale locale = request.getLocale();
        String mdPasswd = MD5Util.md5Password(password);
        try {
            User user = userService.checkUser(username, mdPasswd);
            if (user == null) {
                throw new Exception("用户名或密码错误！");
            }
            if (!Constants.TUser_Active.equals(user.getStatus())) {
                throw new Exception("该用户已被" + Constants.getDictLabel(Constants.TUserStatus, user.getStatus(), locale));
            }
            user.setPassword("");
            request.getSession().setAttribute(SESSION_KEY_USER, user);
            List<Permission> permissions = userService.getUserPermissions(user.getUserId());
            user.setPermissions(permissions);
            logger.info(username + " login success!");
        } catch (Exception e) {
            logger.error(username + " login failed!\n" + e.getMessage());
            model.addAttribute("loginSrc", "1");
            model.addAttribute("loginFailMsg", "登录系统出错");
            return "user/login";
        }
        return "redirect:home.htm";
    }

    /**
     * 查询页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.GET)
    public String queryPage() {
        return "business/user/list";
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
    public Object query(UserFormBean formBean, Model model) {
        try {
            Map<String, Object> result = userService.query(formBean);
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
            map = userService.roleSel();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        model.addAttribute("rolesMap", map);
        return "business/user/save";
    }

    /**
     * 新增
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "save.htm")
    public String save(User user, @RequestParam("roleId") String roleId) {
        try {
            user.setPassword(MD5Util.md5Password(user.getPassword()));
            userService.save(user, roleId);
            Message.setNotice("新增用户成功");
        } catch (Exception e) {
            Message.setError(e.getMessage());
        }
        return queryPage();
    }

    /**
     * 冻结用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "update.htm")
    public String update(@RequestParam("operaType") boolean operaType, @RequestParam("userId") String userId) {
        try {
            if (operaType) {
                userService.active(userId);
                Message.setNotice("用户激活成功");
            } else {
                userService.delete(userId);
                Message.setNotice("用户冻结成功");
            }
        } catch (Exception e) {
            Message.setError(e.getMessage());
        }
        return queryPage();
    }

}
