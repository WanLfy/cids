package cn.edu.zzu.controller.business;

import cn.edu.zzu.util.Constants;
import cn.edu.zzu.util.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * 用户登录
 * Created by qinhao on 2018/4/26.
 */
@Controller
@RequestMapping(value = "/")
public class UserLoginController {

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping(value = "login.htm")
    public String loginPage(Model model, HttpServletRequest request) {
        Locale locale = request.getLocale();
        Map<String, String> map = Constants.getDictListMap(Constants.TIsFlag, locale);
        String flag = Constants.getDictLabel(Constants.TIsFlag, "0", locale);
        model.addAttribute("flag", flag);
        Message.setWarn("123", model);
        return "login";
    }

}
