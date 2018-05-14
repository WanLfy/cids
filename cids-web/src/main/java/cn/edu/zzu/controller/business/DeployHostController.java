package cn.edu.zzu.controller.business;

import cn.edu.zzu.controller.Bean.Message;
import cn.edu.zzu.controller.base.BaseController;
import cn.edu.zzu.controller.business.formBean.DeployHostFormBean;
import cn.edu.zzu.mysql.pojo.DeployHost;
import cn.edu.zzu.service.IDeployHostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 目标主机
 * Created by qinhao on 2018/5/14.
 */
@Controller
@RequestMapping(value = {"/host/"})
public class DeployHostController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(DeployHostController.class);

    @Autowired
    private IDeployHostService hostDeployService;

    /**
     * 查询页面
     *
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.GET)
    public String queryPage() {
        return "business/deployhost/list";
    }

    /**
     * 分页查询
     *
     * @param formBean
     * @param model
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.POST)
    @ResponseBody
    public Object query(DeployHostFormBean formBean, Model model) {
        try {
            Map<String, Object> map = hostDeployService.query(formBean);
            formBean.setData(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return queryPage();
        }
        return formBean;
    }

    /**
     * 新增页面
     *
     * @return
     */
    @RequestMapping(value = "save.htm", method = RequestMethod.GET)
    public String savePage() {
        return "business/deployhost/save";
    }

    /**
     * 新增
     *
     * @param host
     * @return
     */
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    public String save(DeployHost host) {
        try {
            hostDeployService.save(host);
            Message.setNotice("新增成功！");
        } catch (Exception e) {
            logger.error(e.getMessage());
            Message.setError(e.getMessage());
            return savePage();
        }
        return queryPage();
    }

}
