package com.smorales.javalab.middleware.boundary.rest;

import com.smorales.javalab.middleware.boundary.entity.Library;
import com.smorales.javalab.middleware.boundary.entity.TreeData;

import java.util.List;

public class Request {

    private List<Library> libraries;
    private List<TreeData> treedata;
    private RunnableNode runnableNode;

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
}
