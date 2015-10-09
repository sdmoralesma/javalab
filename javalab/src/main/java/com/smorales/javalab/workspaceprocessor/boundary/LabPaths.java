package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.control.Language;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum LabPaths {
    HOME("/home/wildfly/"),
    REPOSITORY_DIR(HOME.dir + ".m2/repository/"),
    JAVA_PROJECT(HOME.dir + "java-gradle"),
    SCALA_PROJECT(HOME.dir + "scala-gradle"),
    GROOY_PROJECT(HOME.dir + "groovy-gradle");

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

    public static LabPaths pathByLanguage(Language lang) {
        switch (lang) {
            case GROOVY:
                return GROOY_PROJECT;
            case JAVA:
                return JAVA_PROJECT;
            case SCALA:
                return SCALA_PROJECT;
            default:
                throw new NotRunnableCodeException("Unknown lang:" + lang);
        }
    }
}
