package cn.edu.zzu.service.impl;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.mapper.ApplicationMapper;
import cn.edu.zzu.mysql.pojo.Application;
import cn.edu.zzu.service.IApplicationService;
import cn.edu.zzu.service.IConfigService;
import cn.edu.zzu.util.JSchUtil;
import cn.edu.zzu.util.JenkinsUtil;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinhao on 2018/5/7.
 */
@Service
@Transactional(transactionManager = "mysqlTransactionManager", rollbackFor = Exception.class)
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private IConfigService configService;
    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public Integer saveJobsInfo() throws Exception {
        Integer num = 0;
        String url = configService.getValue("jenkins_url");
        String user = configService.getValue("jenkins_user");
        String pass = configService.getValue("jenkins_pass");
        String hostIp = configService.getValue("jenkins_host_ip");
        String hostUser = configService.getValue("jenkins_host_user");
        String hostPass = configService.getValue("jenkins_host_pass");
        String storagePath = configService.getValue("jenkins_workspace");

        //与主机建立连接
        JSchUtil ssh2 = new JSchUtil(hostUser, hostPass, hostIp, 3000);
        ssh2.connect();
        if (!ssh2.check()) {
            throw new Exception("与Jenkins所在主机连接失败");
        }
        //初始化util
        JenkinsUtil util = new JenkinsUtil(url, user, pass);
        //获取视图
        Map<String, View> viewsMap = util.getViews();
        for (String viewName : viewsMap.keySet()) {
            if ("all".equals(viewName)) {
                continue;
            }
            //获取视图下的Jobs
            Map<String, Job> jobsMap = util.getJobsByView(viewName);
            for (String key : jobsMap.keySet()) {
                JobWithDetails job = util.getJobDetails(key);
                Application application = new Application();
                application.setAppName(job.getName());
                application.setViewName(viewName);
                if (job.getLastSuccessfulBuild() != null) {
                    //从顶层开始一直遍历到最低层
                    String findCmd = "find " + storagePath + "/" + job.getName() + " ! -empty -name '*.war'";
                    String result = ssh2.execCmd(findCmd);
                    application.setStoragePath(result);
                }
                applicationMapper.save(application);
                num++;
            }
        }
        ssh2.disConnect();
        return num;
    }

    @Override
    public Map<String, Object> query(PageInfo page) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("list", applicationMapper.query(page));
        map.put("size", applicationMapper.count(page));
        return map;
    }
}
