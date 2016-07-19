package com.smorales.javalab.business.processor.boundary.rest;

import com.smorales.javalab.business.processor.boundary.WorkspaceProcessor;
import com.smorales.javalab.business.processor.boundary.rest.request.Request;

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
        JsonObject jsonObject = workspaceProcessor.initialize(lang);
        tracer.info(jsonObject.toString());
        return Response.ok().entity(jsonObject).build();
    }

    @GET
    @Path("/{labId}")
    public Response getByBase62(@PathParam("labId") Integer labId) {
        return Response.ok().entity(workspaceProcessor.getById(labId)).build();
    }

    @POST
    @Path("/run")
    public Response runCode(Request req) {
        String result = workspaceProcessor.runCode(req);
        JsonObject jsonObject = Json.createObjectBuilder().add("output", result).build();
        return Response.ok().entity(jsonObject).build();
    }

    @POST
    @Path("/test")
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

    @POST
    @Path("/download")
    @Produces({"application/zip"})
    public Response download(Request req) {
        byte[] zipFileContent = workspaceProcessor.download(req);
        return Response
                .ok(zipFileContent)
                .type("application/zip")
                .header("Content-Disposition", "attachment; filename = \"project.zip\"")
                .build();
    }
}