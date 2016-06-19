package com.smorales.javalab.workspaceprocessor.boundary.rest.request;

import java.util.List;

public class TreeNode {

    private String id;
    private String label;
    private String icon;
    private String parentId;
    private String expandedIcon;
    private String collapsedIcon;
    private String data;
    private Cursor cursor;//TODO: verify usefulness
    private List<TreeNode> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getExpandedIcon() {
        return expandedIcon;
    }

    public void setExpandedIcon(String expandedIcon) {
        this.expandedIcon = expandedIcon;
    }

    public String getCollapsedIcon() {
        return collapsedIcon;
    }

    public void setCollapsedIcon(String collapsedIcon) {
        this.collapsedIcon = collapsedIcon;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", icon='" + icon + '\'' +
                ", data='" + data + '\'' +
                ", cursor=" + cursor +
                ", children=" + children +
                '}';
    }
}
