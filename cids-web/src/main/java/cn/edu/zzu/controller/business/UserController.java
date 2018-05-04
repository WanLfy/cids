package cn.edu.zzu.controller.business;

import cn.edu.zzu.controller.Bean.Message;
import cn.edu.zzu.controller.base.BaseController;
import cn.edu.zzu.mysql.pojo.Permission;
import cn.edu.zzu.mysql.pojo.User;
import cn.edu.zzu.service.IUserService;
import cn.edu.zzu.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        String mdPasswd = MD5Util.md5Password(password);
        try {
            User user = userService.checkUser(username, mdPasswd);
            if (user == null)
                throw new Exception("用户名或密码错误！");
            user.setPassword("");
            request.getSession().setAttribute(SESSION_KEY_USER, user);
            List<Permission> permissions = userService.getUserPermissions(user.getUserId());
            user.setPermissions(permissions);

        } catch (Exception e) {
            logger.error(username + " login faild!");
            model.addAttribute("loginSrc", "1");
            model.addAttribute("loginFailMsg", e.getMessage());
            return "user/login";
        }
        return "redirect:home.htm";
    }
}
