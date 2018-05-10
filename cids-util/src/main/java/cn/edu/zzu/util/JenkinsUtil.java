package cn.edu.zzu.util;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.JenkinsTriggerHelper;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by qinhao on 2017/12/27.
 */
public class JenkinsUtil {


    private static final Logger logger = LoggerFactory.getLogger(JenkinsUtil.class);

    private JenkinsServer server;

    /**
     * @param url      jenkinsServer地址
     * @param username 用户名
     * @param password 密码
     */
    public JenkinsUtil(String url, String username, String password) {
        try {
            this.server = new JenkinsServer(new URI(url), username, password);
        } catch (URISyntaxException e) {
            logger.error("Connect JenkinsServer failed!");
        }
    }

    /**
     * 通过名称获取jenkinsJob
     *
     * @param name
     * @return
     */
    public Job getJob(String name) {
        Job job = null;
        if (this.server != null) {
            try {
                job = this.server.getJob(name);
            } catch (IOException e) {
                logger.error("getJob failed!");
            }
        }
        return job;
    }

    /**
     * 获取Jenkins中所有的Job
     *
     * @return
     */
    public Map<String, Job> getJobs() {
        Map<String, Job> result = null;
        if (this.server != null) {
            try {
                result = this.server.getJobs();
            } catch (IOException e) {
                logger.error("getJobs failed!");
            }
        }
        return result;
    }

    /**
     * 获取所有视图
     *
     * @return
     */
    public Map<String, View> getViews() {
        Map<String, View> result = null;
        if (this.server != null) {
            try {
                result = this.server.getViews();
            } catch (IOException e) {
                logger.error("getViews failed!");
            }
        }
        return result;
    }

    /**
     * 获取视图下所有Job
     *
     * @param viewName
     * @return
     */
    public Map<String, Job> getJobsByView(String viewName) {
        Map<String, Job> result = null;
        if (this.server != null) {
            try {
                result = this.server.getJobs(viewName);
            } catch (IOException e) {
                logger.error("getJobsByView failed!");
            }
        }
        return result;
    }

    /**
     * 通过JobName获取Job的详细信息
     *
     * @param jobName
     * @return
     */
    public JobWithDetails getJobDetails(String jobName) {
        JobWithDetails job = null;
        if (this.server != null) {
            try {
                job = this.server.getJob(jobName);
            } catch (IOException e) {
                logger.error("getJobDetails failed!");
            }
        }
        return job;
    }

    /**
     * 通过JobName触发构建工作,并等待构建结束或取消
     * 问题：org.apache.http.client.HttpResponseException: No valid crumb was included in the request
     * 原因：Jenkins全局安全设置中"防止跨站点请求伪造"是开启状态
     *
     * @param jobName
     * @return
     */
    public BuildWithDetails startBuild(String jobName) {
        BuildWithDetails build = null;
        if (this.server != null) {
            JenkinsTriggerHelper jth = new JenkinsTriggerHelper(this.server);
            try {
                build = jth.triggerJobAndWaitUntilFinished(jobName);
            } catch (Exception e) {
                logger.error("startBuild failed!");
            }
        }
        return build;
    }
}
