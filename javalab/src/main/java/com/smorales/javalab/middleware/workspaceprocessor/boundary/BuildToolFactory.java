package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class BuildToolFactory {

    @Produces
    public BuildTool createBuildTool() {

        return null;
    }
}
