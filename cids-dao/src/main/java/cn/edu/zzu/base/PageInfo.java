package cn.edu.zzu.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 页面分页
 * Created by qinhao on 2018/4/27.
 *
 * @param <T>
 */
public class PageInfo<T extends Object> implements Serializable {


    private static final long serialVersionUID = -5468020866682626287L;
    /*当前页数*/
    private int currentPage = 1;
    /*每页大小*/
    private int pageSize = 10;
    /*项目总数*/
    private int totalItem;
    /*当前页项目对象集合*/
    private List<T> pageItems;

    private String pageErrorInfo;

    public int getCurrentPage() {
        return currentPage > 0 ? currentPage : 1;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /*获取总页数*/
    public int getTotalPage() {
        return totalItem / pageSize + (totalItem % pageSize == 0 ? 0 : 1);
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public List<T> getPageItems() {
        return pageItems;
    }

    public void setPageItems(List<T> pageItems) {
        this.pageItems = pageItems;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartItem() {
        return (getCurrentPage() - 1) * getPageSize();
    }

    public String getPageErrorInfo() {
        return pageErrorInfo;
    }

    public void setPageErrorInfo(Exception e) {
        setPageErrorInfo(e.getMessage());
    }

    public void setPageErrorInfo(String pageErrorInfo) {
        this.pageErrorInfo = pageErrorInfo;
    }

    /**
     * 设置结果集和总数
     *
     * @param result
     */
    public void setData(Map<String, Object> result) {
        setPageItems((List<T>) result.get("list"));
        setTotalItem((Integer) result.get("size"));
    }
}
