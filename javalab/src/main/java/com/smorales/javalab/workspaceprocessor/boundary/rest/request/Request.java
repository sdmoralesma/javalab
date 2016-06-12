package com.smorales.javalab.workspaceprocessor.boundary.rest.request;

import java.util.List;

public class Request {

    private List<TreeData> treedata;
    private RunnableNode runnableNode;
    private InitConfig initConfig;
    private String description;
    private String tags;

    public List<TreeData> getTreedata() {
        return treedata;
    }

    public void setTreedata(List<TreeData> treedata) {
        this.treedata = treedata;
    }

    public RunnableNode getRunnableNode() {
        return runnableNode;
    }

    public void setRunnableNode(RunnableNode runnableNode) {
        this.runnableNode = runnableNode;
    }

    public InitConfig getInitConfig() {
        return initConfig;
    }

    public void setInitConfig(InitConfig initConfig) {
        this.initConfig = initConfig;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Request{" +
                "treedata=" + treedata +
                ", runnableNode=" + runnableNode +
                ", initConfig=" + initConfig +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
