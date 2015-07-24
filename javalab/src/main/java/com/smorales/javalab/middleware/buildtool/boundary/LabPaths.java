package com.smorales.javalab.middleware.buildtool.boundary;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum LabPaths {
    HOME("/home/wildfly/"),
    REPOSITORY_DIR(HOME.path + ".m2/repository/");

    private String path;

    LabPaths(String path) {
        this.path = path;
    }

    public Path getPath() {
        return Paths.get(this.path);
    }

    public String getPathAsString() {
        return this.path;
    }

}
