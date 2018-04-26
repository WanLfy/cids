package cn.edu.zzu.filter;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * 登录拦截器
 * Created by qinhao on 2018/3/22.
 */
public class UserSessionFilter implements Filter {
    // 不拦截路径
    private String[] excludeUrls;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 获取参数
        String excludeUrl = filterConfig.getInitParameter("excludeUrls");
        if (!StringUtils.isEmpty(excludeUrl)) {
            this.excludeUrls = excludeUrl.split(";");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest oldReq = (HttpServletRequest) servletRequest;
        // 获取请求url
        String url = request.getRequestURI();
        // check url
        boolean notFilter = checkUrl(url);
        if (notFilter) {
            filterChain.doFilter(request, response);
            return;
        }

        final String JSESSIONID = "JSESSIONID";
        String sessionId = "";
        // 从cookie中取JSESSIONID，如果没有就随机生成一个
        if (StringUtils.isEmpty(getCookie(oldReq, JSESSIONID))) {
            sessionId = getCookie(oldReq, JSESSIONID);
        } else {
            sessionId = UUID.randomUUID().toString().replace("-", "");
        }
        MDC.put(JSESSIONID, sessionId);

        HttpSession session = request.getSession();
        Object user = session.getAttribute("ssm-user");
        // 判断是否登录
        if (user == null) {
            // 空缺2：cookie是否有登录信息
            // login.htm 是post请求,无法直接跳转
            // request.setAttribute("autoBackUrl", request.getContextPath() + "/login.htm");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } else {
            // 已登录
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 检查url是否过滤
     *
     * @param url
     * @return
     */
    private boolean checkUrl(String url) {
        for (String str : this.excludeUrls) {
            if (url.indexOf(str) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从cookie中取值
     *
     * @param request
     * @param key
     * @return
     */
    private static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
