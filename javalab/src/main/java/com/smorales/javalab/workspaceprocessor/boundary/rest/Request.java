package com.smorales.javalab.workspaceprocessor.boundary.rest;

import java.util.List;

public class Request {

    private List<TreeData> treedata;
    private RunnableNode runnableNode;
    private InitConfig initConfig;

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

    @Override
    public String toString() {
        return "Request{" +
                "treedata=" + treedata +
                ", runnableNode=" + runnableNode +
                ", initConfig=" + initConfig +
                '}';
    }
}
