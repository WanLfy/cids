package cn.edu.zzu.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * Created by qinhao on 2018/6/25.
 */
//@ControllerAdvice
public class ControllerConfig {

    @Autowired
    private ResourceUrlProvider provider;

    @ModelAttribute("urlProvider")
    public ResourceUrlProvider urlProvider() {
        return this.provider;
    }
}
