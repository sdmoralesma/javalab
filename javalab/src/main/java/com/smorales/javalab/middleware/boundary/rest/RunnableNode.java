package com.smorales.javalab.middleware.boundary.rest;

public class RunnableNode {

    private long id;
    private boolean mainClass;
    private boolean testClass;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
