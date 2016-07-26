package com.smorales.javalab.business.containers.boundary.rest;

import com.smorales.javalab.business.containers.boundary.ContainerController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/docker")
public class ContainerResource {

    @Inject
    ContainerController containerController;

    @PostConstruct
    private void init() {

    }


    @GET
    public Response dockerVersion() {
        return Response.ok(containerController.dockerVersion(), MediaType.APPLICATION_JSON).build();
    }
}
