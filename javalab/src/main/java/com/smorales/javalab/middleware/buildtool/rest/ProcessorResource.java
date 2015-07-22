package com.smorales.javalab.middleware.buildtool.rest;

import com.smorales.javalab.middleware.buildtool.boundary.BuildTool;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/process")
public class ProcessorResource {

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
