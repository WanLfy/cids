package cn.edu.zzu.controller.business.formBean;

import cn.edu.zzu.base.PageInfo;
import cn.edu.zzu.mysql.pojo.DeployServer;

/**
 * Created by qinhao on 2018/5/14.
 */
public class DeployServerFormBean extends PageInfo<DeployServer> {

    private Integer hostId;
    private String serverName;

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
