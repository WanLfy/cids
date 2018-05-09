package cn.edu.zzu.mysql.pojo;

/**
 * 应用信息
 * Created by qinhao on 2018/4/27.
 */
public class Application {

    private Integer appId;// id
    private String appName;// 应用名称
    private String storagePath;// 应用war包存储路径
    private String viewName;// 视图名称
    private String createTimestamp;// 创建时间
    private String updateTimestamp;// 更新时间


    private long size;// war包大小
    private Integer buildNum;// 构建号
    private String buildDate;// 构建时间
    private String buildResult;// 构建结果
    private String buildInfoUrl;// 构建详细信息地址

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Integer getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(Integer buildNum) {
        this.buildNum = buildNum;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getBuildResult() {
        return buildResult;
    }

    public void setBuildResult(String buildResult) {
        this.buildResult = buildResult;
    }

    public String getBuildInfoUrl() {
        return buildInfoUrl;
    }

    public void setBuildInfoUrl(String buildInfoUrl) {
        this.buildInfoUrl = buildInfoUrl;
    }
}
