package com.smorales.javalab.workspaceprocessor.boundary.rest;

import com.smorales.javalab.workspaceprocessor.entity.Library;
import com.smorales.javalab.workspaceprocessor.entity.TreeData;

import java.util.List;

public class Request {

    private List<Library> libraries;
    private List<TreeData> treedata;
    private RunnableNode runnableNode;
    private InitConfig initConfig;

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

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
}
