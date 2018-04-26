package cn.edu.zzu.service.impl;

import cn.edu.zzu.service.IConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by qinhao on 2018/4/24.
 */
@Service
public class ConfigServiceImpl implements IConfigService {
    //日志器
    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Resource(name = "configProperties")
    private Properties configProperties;
    //@Autowired
    //private Properties configProperties;

    @Override
    public String getValue(String name) {
        if (this.configProperties == null) {
            throw new RuntimeException("properties 未注入！");
        } else {
            String value = this.configProperties.getProperty(name);
            logger.debug("获取属性{}值:{}", name, value);
            return value;
        }
    }
}
