package com.smorales.javalab.workspaceprocessor.boundary.rest;

public class RunnableNode {

    private String id;
    private boolean mainClass;
    private boolean testClass;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMainClass() {
        return mainClass;
    }

    public void setMainClass(boolean mainClass) {
        this.mainClass = mainClass;
    }

    public boolean isTestClass() {
        return testClass;
    }

    public void setTestClass(boolean testClass) {
        this.testClass = testClass;
    }
}
