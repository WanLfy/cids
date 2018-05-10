package cn.edu.zzu.filter;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
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
            JoranConfigurator joranConfigurator = new JoranConfigurator();
            joranConfigurator.setContext(loggerContext);
            loggerContext.reset();

            path = System.getProperty("config.home.dir") + "/cids_configs/logback.xml";
            joranConfigurator.doConfigure(new File(path));
            StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
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
    }

    @Override
    public void destroy() {
    }
}
