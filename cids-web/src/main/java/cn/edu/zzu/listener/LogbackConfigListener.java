package cn.edu.zzu.listener;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class LogbackConfigListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
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
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
