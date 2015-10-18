package com.smorales.javalab.workspaceprocessor.boundary.rest;

import com.smorales.javalab.workspaceprocessor.boundary.WorkspaceProcessor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Stateless
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("/process")
public class WorkspaceProcessorResource {

    @Inject
    WorkspaceProcessor workspaceProcessor;

    @Inject
    Logger tracer;

    @GET
    @Path("/init/{lang}")
    public Response initialize(@PathParam("lang") String lang) {
        return Response.ok().entity(workspaceProcessor.initialize(lang)).build();
    }

    @GET
    @Path("/new")
    public Response newWorkspace() {
        String result = workspaceProcessor.newWorkspace();
        JsonObject output = Json.createObjectBuilder().add("output", result).build();
        return Response.ok().entity(output).build();
    }

    @GET
    @Path("/{idBase62}")
    public Response getByBase62(@PathParam("idBase62") String idBase62) {
        return Response.ok().entity(workspaceProcessor.getById(idBase62)).build();
    }

    @POST
    @Path("/run")
    public Response runCode(Request req) {
        String result = workspaceProcessor.runCode(req);
        JsonObject jsonObject = Json.createObjectBuilder().add("output", result).build();
        return Response.ok().entity(jsonObject).build();
    }


    @POST
    @Path("/tests")
    public Response runTests(Request req) {
        String result = workspaceProcessor.runTests(req);
        JsonObject output = Json.createObjectBuilder().add("output", result).build();
        return Response.ok().entity(output).build();
    }

    @POST
    @Path("/save")
    public Response save(String data) {
        Integer result = workspaceProcessor.save(data);
        JsonObject output = Json.createObjectBuilder().add("output", result).build();
        return Response.ok().entity(output).build();
    }

}