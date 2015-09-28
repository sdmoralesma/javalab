package com.smorales.javalab.workspaceprocessor.boundary;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum LabPaths {
    HOME("/home/wildfly/"),
    REPOSITORY_DIR(HOME.dir + ".m2/repository/"),
    JAVA_PROJECT(LabPaths.HOME.asString() +"java-gradle"),
    SCALA_PROJECT(LabPaths.HOME.asString() +"scala-gradle"),
    GROOY_PROJECT(LabPaths.HOME.asString() +"groovy-gradle");

    private String dir;

    LabPaths(String path) {
        this.dir = path;
    }

    public String asString() {
        return this.dir;
    }

    public Path asPath() {
        return Paths.get(this.dir);
    }
}
