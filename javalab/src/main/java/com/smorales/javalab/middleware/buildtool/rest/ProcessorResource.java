package com.smorales.javalab.middleware.buildtool.rest;

import com.smorales.javalab.middleware.buildtool.boundary.BuildTool;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/process")
@Stateless
public class ProcessorResource {

    @PersistenceContext
    EntityManager em;

    @GET
    @Path("/init")
    @Produces(MediaType.APPLICATION_JSON)
    public Response initialize(@Context ServletContext context) {
        InputStream inputStream = context.getResourceAsStream("/WEB-INF/json/init-model.json");
        JsonObject jsonObject = Json.createReader(inputStream).readObject();
        return Response.ok().entity(jsonObject).build();
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

    @POST
    @Path("/save")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Request req) {
//        BuildTool buildTool = BuildTool.get(BuildTool.Type.JAVAC, req.getTreedata(), req.getLibraries(), req.getRunnableNode());
//        String out = buildTool.testCode();
        String out = "saved!";
        return Response.ok().entity(out).build();
    }

    @POST
    @Path("/new")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newWorkspace(Request req) {
//        BuildTool buildTool = BuildTool.get(BuildTool.Type.JAVAC, req.getTreedata(), req.getLibraries(), req.getRunnableNode());
//        String out = buildTool.testCode();
        String out = "new workspace";
        return Response.ok().entity(out).build();
    }

}
