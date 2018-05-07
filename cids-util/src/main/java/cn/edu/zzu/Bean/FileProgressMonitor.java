package cn.edu.zzu.Bean;

import com.jcraft.jsch.SftpProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qinhao on 2018/5/7.
 */
public class FileProgressMonitor implements SftpProgressMonitor {

    private static final Logger logger = LoggerFactory.getLogger(FileProgressMonitor.class);

    @Override
    public void init(int i, String s, String s1, long l) {
        logger.info("开始下载war包！");
    }

    @Override
    public boolean count(long l) {
        return true;
    }

    @Override
    public void end() {
        logger.info("下载war包完成！");
    }
}
