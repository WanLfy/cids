package cn.edu.zzu.controller.business.formBean;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.DeployHost;

/**
 * Created by qinhao on 2018/5/14.
 */
public class DeployHostFormBean extends PageInfo<DeployHost> {

    private String hostName;
    private String hostIP;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }
}
