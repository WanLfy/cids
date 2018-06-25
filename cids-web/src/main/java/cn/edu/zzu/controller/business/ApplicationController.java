package cn.edu.zzu.controller.business;

import cn.edu.zzu.controller.Bean.BaseResult;
import cn.edu.zzu.controller.Bean.Message;
import cn.edu.zzu.controller.base.BaseController;
import cn.edu.zzu.controller.business.formBean.ApplicationFormBean;
import cn.edu.zzu.mysql.pojo.Application;
import cn.edu.zzu.service.IApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Jennings管理应用
 * Created by qinhao on 2018/5/7.
 */
@Controller
@RequestMapping(value = {"/application/"})
public class ApplicationController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    @Autowired
    private IApplicationService applicationService;

    /**
     * 查询页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.GET)
    public String queryPage(Model model) {
        List<String> viewNames = null;
        try {
            viewNames = applicationService.getViewNames();
            model.addAttribute("viewNames", viewNames);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "business/application/list";
    }

    /**
     * 查询
     *
     * @param formBean
     * @param model
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.POST)
    @ResponseBody
    public Object query(ApplicationFormBean formBean, Model model) {
        try {
            Map<String, Object> result = applicationService.query(formBean);
            formBean.setData(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return queryPage(model);
        }
        return formBean;
    }

    /**
     * 新增
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "save.htm")
    public String save(Model model) {
        Integer num = 0;
        try {
            num = applicationService.saveJobsInfo();
            Message.setNotice("新增" + num + "条应用信息");
        } catch (Exception e) {
            Message.setError(e.getMessage());
        }
        return queryPage(model);
    }

    /**
     * 开始构建
     *
     * @param appName
     * @return
     */
    @RequestMapping(value = "build.htm")
    @ResponseBody
    public BaseResult<String> build(@RequestParam("appName") String appName) {
        BaseResult<String> result = new BaseResult<>();
        try {
            String buildRS = applicationService.build(appName);
            result.setSuccess(true);
            result.setRetMsg(buildRS);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setRetMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 查询构建结果
     *
     * @param appName
     * @return
     */
    @RequestMapping(value = "queryBuild.htm")
    @ResponseBody
    public BaseResult<String> queryBuild(@RequestParam("appName") String appName) {
        BaseResult<String> result = new BaseResult<>();
        try {
            String buildRS = applicationService.queryBuild(appName);
            result.setSuccess(true);
            result.setRetMsg(buildRS);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setRetMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }
}
