package cn.edu.zzu.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinhao on 2018/5/3.
 */
public class Nodes {

    private String text;
    private String href;
    private List<Nodes> nodesList = new ArrayList<Nodes>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Nodes> getNodesList() {
        return nodesList;
    }

    public void setNodesList(List<Nodes> nodesList) {
        this.nodesList = nodesList;
    }
}
