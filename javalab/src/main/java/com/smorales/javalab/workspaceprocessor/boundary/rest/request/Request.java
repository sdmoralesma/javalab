package com.smorales.javalab.workspaceprocessor.boundary.rest.request;

import java.util.List;

public class Request {

    private List<TreeData> filesTree;
    private RunnableNode runnableNode;
    private Config config;
    private String terminal;
    private String description;
    private List<String> tags;

    public List<TreeData> getFilesTree() {
        return filesTree;
    }

    public void setFilesTree(List<TreeData> filesTree) {
        this.filesTree = filesTree;
    }

    public RunnableNode getRunnableNode() {
        return runnableNode;
    }

    public void setRunnableNode(RunnableNode runnableNode) {
        this.runnableNode = runnableNode;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Request{" +
                "filesTree=" + filesTree +
                ", runnableNode=" + runnableNode +
                ", config=" + config +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
