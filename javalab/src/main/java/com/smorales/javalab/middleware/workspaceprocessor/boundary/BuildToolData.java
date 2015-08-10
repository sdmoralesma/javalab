package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import com.smorales.javalab.middleware.workspaceprocessor.boundary.rest.RunnableNode;
import com.smorales.javalab.middleware.workspaceprocessor.entity.Library;
import com.smorales.javalab.middleware.workspaceprocessor.entity.TreeData;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BuildToolData {

    private final List<TreeData> treedata;
    private final List<Library> libraries;
    private final RunnableNode runnableNode;

    private List<Path> mainclass;
    private List<Path> testclass;


    public BuildToolData(List<TreeData> treedata, List<Library> libraries, RunnableNode runnableNode) {
        this.treedata = treedata;
        this.libraries = libraries;
        this.runnableNode = runnableNode;
        this.mainclass = new ArrayList<>();
        this.testclass = new ArrayList<>();
    }


    public List<TreeData> getTreedata() {
        return treedata;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public RunnableNode getRunnableNode() {
        return runnableNode;
    }

    public List<Path> getMainclass() {
        return mainclass;
    }

    public List<Path> getTestclass() {
        return testclass;
    }

}
