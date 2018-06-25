package cn.edu.zzu.controller.business;

import cn.edu.zzu.controller.Bean.BaseResult;
import cn.edu.zzu.controller.Bean.Message;
import cn.edu.zzu.controller.base.BaseController;
import cn.edu.zzu.controller.business.formBean.DeployServerFormBean;
import cn.edu.zzu.mysql.pojo.DeployServer;
import cn.edu.zzu.service.IDeployServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 部署应用服务器
 * Created by qinhao on 2018/5/14.
 */
@Controller
@RequestMapping(value = {"/server/"})
public class DeployServerController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DeployHostController.class);
    @Autowired
    private IDeployServerService deployServerService;

    /**
     * 查询页面
     *
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.GET)
    public String queryPage(Model model) {
        Map<String, String> hostsMap = new HashMap<String, String>();
        try {
            hostsMap = deployServerService.hostSel();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        model.addAttribute("hostsMap", hostsMap);
        return "business/deployserver/list";
    }

    /**
     * 分页查询
     *
     * @param formBean
     * @return
     */
    @RequestMapping(value = "queryList.htm", method = RequestMethod.POST)
    @ResponseBody
    public Object query(DeployServerFormBean formBean, Model model) {
        try {
            Map<String, Object> result = deployServerService.query(formBean);
            formBean.setData(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return queryPage(model);
        }
        return formBean;
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "save.htm", method = RequestMethod.GET)
    public String savePage(Model model) {
        Map<String, String> hostsMap = new HashMap<String, String>();
        Map<String, String> appsMap = new HashMap<String, String>();
        try {
            hostsMap = deployServerService.hostSel();
            appsMap = deployServerService.appSel();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        model.addAttribute("hostsMap", hostsMap);
        model.addAttribute("appsMap", appsMap);
        return "business/deployserver/save";
    }

    /**
     * 新增服务器
     *
     * @param server
     * @param model
     * @return
     */
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    public String save(DeployServer server, Model model) {
        try {
            deployServerService.save(server);
        } catch (Exception e) {
            logger.error(e.getMessage());
            Message.setError(e.getMessage());
            return savePage(model);
        }
        Message.setNotice("新增成功！");
        return queryPage(model);
    }

    /**
     * 启动服务器
     *
     * @return
     */
    @RequestMapping(value = "start.htm")
    @ResponseBody
    public BaseResult<String> start(@RequestParam("id") Integer id) {
        BaseResult<String> result = new BaseResult<String>();
        try {
            String cmdRS = deployServerService.start(id);
            result.setSuccess(true);
            result.setResult(cmdRS);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 停止服务器
     *
     * @return
     */
    @RequestMapping(value = "stop.htm")
    @ResponseBody
    public BaseResult<String> stop(@RequestParam("id") Integer id) {
        BaseResult<String> result = new BaseResult<String>();
        try {
            String cmdRS = deployServerService.stop(id);
            result.setSuccess(true);
            result.setResult(cmdRS);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 检查服务器启动状态
     *
     * @return
     */
    @RequestMapping(value = "check.htm")
    @ResponseBody
    public BaseResult<String> check(@RequestParam("id") Integer id) {
        BaseResult<String> result = new BaseResult<String>();
        try {
            String cmdRS = deployServerService.check(id);
            result.setSuccess(true);
            result.setResult(cmdRS);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 服务器部署应用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deploy.htm")
    @ResponseBody
    public BaseResult<String> deploy(@RequestParam("id") Integer id) {
        BaseResult<String> result = new BaseResult<>();
        try {
            String cmdRS = deployServerService.deploy(id);
            if ("SUCCESS".equals(cmdRS)) {
                result.setSuccess(true);
                result.setRetMsg("应用部署成功,请启动服务器");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setRetMsg(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }
}
