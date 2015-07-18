package com.smorales.javalab.middleware.boundary.rest;

import com.smorales.javalab.middleware.boundary.buildtool.BuildTool;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/process")
public class ProcessorResource {

    @GET
    @Path("/ping")
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public Response ping() {
        return Response.ok().entity("Pong!, Processor is up and running.").build();
    }


    @POST
    @Path("/run")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response runCode(Request req) {
        BuildTool buildTool = BuildTool.get(BuildTool.Type.JAVAC, req.getTreedata(), req.getLibraries(), req.getRunnableNode());
        String out = buildTool.runCode();
        return Response.ok().entity(out).build();
    }


    @POST
    @Path("/tests")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response runTests(Request req) {
        BuildTool buildTool = BuildTool.get(BuildTool.Type.JAVAC, req.getTreedata(), req.getLibraries(), req.getRunnableNode());
        String out = buildTool.testCode();
        return Response.ok().entity(out).build();
    }

}
