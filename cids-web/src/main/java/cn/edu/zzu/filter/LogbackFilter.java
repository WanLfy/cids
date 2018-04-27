package cn.edu.zzu.filter;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by clg on 2017/11/28.
 */
public class LogbackFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ServletContextListener.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //读取logback配置文件
        String path = "";
        try {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            loggerContext.reset();
            JoranConfigurator joranConfigurator = new JoranConfigurator();
            joranConfigurator.setContext(loggerContext);

            path = System.getProperty("config.home.dir") + "/cids_configs/logback.xml";
            joranConfigurator.doConfigure(new File(path));
            logger.debug("loaded slf4j configure file from {}", path);
        } catch (JoranException e) {
            e.printStackTrace();
            logger.error("can loading slf4j configure file from " + path, e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        //logback记录sessionID
//        String JSESSIONID = "JSESSIONID";
//        String sessionId = "";
//        //从cookie中取JSESSIONID，如果没有就随机生成一个
//        if (StringUtils.isNotBlank(getCookie(req, JSESSIONID))) {
//            sessionId = getCookie(req, JSESSIONID);
//        } else {
//            sessionId = UUID.randomUUID().toString().replace("-", "");
//        }
//        MDC.put(JSESSIONID, sessionId);
//
//        chain.doFilter(request,response);
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
    public static String getCookie(HttpServletRequest request, String key) {
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
