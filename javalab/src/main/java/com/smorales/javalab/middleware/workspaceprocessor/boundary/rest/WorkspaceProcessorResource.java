package com.smorales.javalab.middleware.workspaceprocessor.boundary.rest;

import com.smorales.javalab.middleware.workspaceprocessor.boundary.WorkspaceProcessor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Path("/process")
public class WorkspaceProcessorResource {

    @Inject
    WorkspaceProcessor workspaceProcessor;

    @GET
    @Path("/init")
    public Response initialize() {
        return Response.ok().entity(workspaceProcessor.initialize()).build();
    }

    @GET
    @Path("/{idBase62}")
    public Response getByBase62(@PathParam("idBase62") String idBase62) {
        return Response.ok().entity(workspaceProcessor.getByBase62(idBase62)).build();
    }

    @POST
    @Path("/run")
    public Response runCode(Request req) {
        return Response.ok().entity(workspaceProcessor.runCode(req)).build();
    }


    @POST
    @Path("/tests")
    public Response runTests(Request req) {
        return Response.ok().entity(workspaceProcessor.runTests(req)).build();
    }

    @POST
    @Path("/save")
    public Response save(String data) {
        return Response.ok().entity(workspaceProcessor.save(data)).build();
    }

    @POST
    @Path("/new")
    public Response newWorkspace(Request req) {
        return Response.ok().entity(workspaceProcessor.newWorkspace(req)).build();
    }

}
