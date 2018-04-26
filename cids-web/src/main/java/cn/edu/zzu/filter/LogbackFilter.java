package cn.edu.zzu.filter;


import ch.qos.logback.access.joran.JoranConfigurator;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 重置LoggerFactory
 * Created by qinhao on 2018/3/20.
 */
public class LogbackFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ServletContextListener.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String path = "";
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            // loggerContext配置重置
            loggerContext.reset();
            JoranConfigurator joranConfigurator = new JoranConfigurator();
            joranConfigurator.setContext(loggerContext);

            // 获取外置化配置文件的路径
            path = System.getProperty("config.home.dir") + "/cids_configs/logback.xml";
            joranConfigurator.doConfigure(new File(path));
            logger.debug("loaded logback config file from {}", path);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // logback记录sessionID
        final String JSESSIONID = "JSESSIONID";
        String sessionId = "";
        // 从cookie中取JSESSIONID，如果没有就随机生成一个
        if (StringUtils.isEmpty(getCookie(request, JSESSIONID))) {
            sessionId = getCookie(request, JSESSIONID);
        } else {
            sessionId = UUID.randomUUID().toString().replace("-", "");
        }
        MDC.put(JSESSIONID, sessionId);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
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
