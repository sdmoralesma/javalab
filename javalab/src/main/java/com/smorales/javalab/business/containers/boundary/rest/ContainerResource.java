package com.smorales.javalab.business.containers.boundary.rest;

import com.smorales.javalab.business.containers.boundary.ContainerController;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/docker")
public class ContainerResource {

    @Inject
    ContainerController containerController;

    @GET
    public Response dockerVersion() {
        return Response.ok(containerController.dockerVersion(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("container")
    public Response createJavaContainer() {
        containerController.createJavaContainer();
        return Response.ok("ok", MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("image")
    public Response createImage() {
        return Response.ok(containerController.createImage(), MediaType.APPLICATION_JSON).build();
    }


    @GET
    @Path("test")
    public Response test() throws IOException {
        return Response.ok("DONE", MediaType.APPLICATION_JSON).build();
    }
}
