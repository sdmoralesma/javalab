package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum LabPaths {
    HOME("/home/wildfly/"),
    REPOSITORY_DIR(HOME.path + ".m2/repository/");

    private String path;

    LabPaths(String path) {
        this.path = path;
    }

    public Path asPath() {
        return Paths.get(this.path);
    }

    public String asString() {
        return this.path;
    }

}
