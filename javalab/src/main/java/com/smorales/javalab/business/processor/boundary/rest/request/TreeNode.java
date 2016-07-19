package com.smorales.javalab.business.processor.boundary.rest.request;

import java.util.List;

public class TreeNode {

    private String id;
    private String label;
    private String icon;
    private String parentId;
    private String expandedIcon;
    private String collapsedIcon;
    private String data;
    private List<TreeNode> children;

    public TreeNode() {
    }

    public TreeNode(String id) {
        this.id = id;
    }

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

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNode treeNode = (TreeNode) o;

        return id != null ? id.equals(treeNode.id) : treeNode.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", icon='" + icon + '\'' +
                ", data='" + data + '\'' +
                ", children=" + children +
                '}';
    }
}
