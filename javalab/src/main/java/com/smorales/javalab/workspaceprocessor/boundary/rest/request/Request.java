package com.smorales.javalab.workspaceprocessor.boundary.rest.request;

import java.util.List;

public class Request {

    private List<TreeNode> filesTree;
    private Config config;
    private String terminal;
    private String description;
    private List<String> tags;

    private RunnableNode runnableNode;

    public List<TreeNode> getFilesTree() {
        return filesTree;
    }

    public void setFilesTree(List<TreeNode> filesTree) {
        this.filesTree = filesTree;
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


    public RunnableNode getRunnableNode() {
        return runnableNode;
    }

    public void setRunnableNode(RunnableNode runnableNode) {
        this.runnableNode = runnableNode;
    }

    @Override
    public String toString() {
        return "Request{" +
                "filesTree=" + filesTree +
                ", config=" + config +
                ", terminal=" + terminal +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
