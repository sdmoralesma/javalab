package com.smorales.javalab.business.containers.boundary.rest;

import com.smorales.javalab.business.containers.boundary.ContainerController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/docker")
public class ContainerResource {

    @Inject
    ContainerController containerController;

    @PostConstruct
    private void init() {

    }


    @GET
    public void dockerVersion() {
        containerController.dockerVersion();
    }
}
