package cn.edu.zzu.controller.Bean;

import cn.edu.zzu.mysql.pojo.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinhao on 2018/5/3.
 */
public class Node {

    private String text;
    private String requestURI;
    private List<Node> nodes;


    public List<Node> generaterNodes(List<Permission> permissions) {
        List<Node> listNodes = new ArrayList<Node>();
        for (Permission parent : permissions) {
            if (parent.getChildPer() != null && parent.getChildPer().size() > 0 && parent.getParentId().equals("0")) {
                Node node = new Node();
                generaterChildNodes(permissions, parent, node);
                listNodes.add(node);
            }
        }
        return listNodes;
    }

    public Node generaterChildNodes(List<Permission> permissions, Permission parent, Node node) {
        if (parent.getChildPer() == null || parent.getChildPer().size() == 0) {
            node.setRequestURI(parent.getPermissionUrl());
            node.setText(parent.getPermissionName());
        } else {
            List<Node> listNodes = new ArrayList<Node>();
            node.setText(parent.getPermissionName());
            for (Permission childMenu : parent.getChildPer()) {
                Node childNodes = new Node();
                listNodes.add(generaterChildNodes(permissions, childMenu, childNodes));
            }
            node.setNodes(listNodes);
        }
        return node;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
