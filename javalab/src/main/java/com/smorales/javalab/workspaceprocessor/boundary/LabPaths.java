package com.smorales.javalab.workspaceprocessor.boundary;

public enum LabPaths {
    HOME("/home/wildfly/"),
    REPOSITORY_DIR(HOME.path + ".m2/repository/");

    private String path;

    LabPaths(String path) {
        this.path = path;
    }

    public String asString() {
        return this.path;
    }

}
