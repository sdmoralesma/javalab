package com.smorales.javalab.workspaceprocessor.boundary.buildtool;

import com.smorales.javalab.workspaceprocessor.boundary.rest.request.Request;

import javax.inject.Inject;

public class BuildToolFactory {

    @Inject
    private Gradle gradle;

    @Inject
    private Buildr buildr;

    public BuildTool get(Request req) {
        switch (req.getConfig().getLanguage()) {
            case "java":
                return buildr;
            case "scala":
                return gradle;
            case "groovy":
                return gradle;
            default:
                throw new IllegalArgumentException("unknown language:" + req.getConfig().getLanguage());
        }
    }

}
