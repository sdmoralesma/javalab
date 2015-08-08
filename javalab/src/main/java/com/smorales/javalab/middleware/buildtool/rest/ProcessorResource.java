package com.smorales.javalab.middleware.buildtool.rest;

import com.smorales.javalab.middleware.buildtool.boundary.BuildTool;
import com.smorales.javalab.middleware.buildtool.control.Base62;
import com.smorales.javalab.middleware.buildtool.entity.Workspace;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;

@Path("/process")
@Stateless
public class ProcessorResource {

    public static final int OFFSET = 1000000;

    @PersistenceContext
    EntityManager em;

    @GET
    @Path("/init")
    @Produces(MediaType.APPLICATION_JSON)
    public Response initialize(@Context ServletContext context) {
        Workspace workspace = em.createNamedQuery("Workspace.findFirstRow", Workspace.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);

        JsonObject jsonObject = Json.createReader(new StringReader(workspace.getWorkspace())).readObject();
        return Response.ok().entity(jsonObject).build();
    }

    @GET
    @Path("/{base62}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByBase62(@PathParam("base62") String base62) {
        TypedQuery<Workspace> query = em.createNamedQuery("Workspace.findByBase62", Workspace.class);
        query.setParameter("base62", base62);
        Workspace workspace;

        try {
            workspace = query.getSingleResult();
        } catch (NoResultException ex) {
            return Response.ok().entity("No workspace found").build();
        }

        JsonObject jsonObject = Json.createReader(new StringReader(workspace.getWorkspace())).readObject();
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
    @Consumes(MediaType.TEXT_PLAIN)
    public Response save(String data) {
        Workspace workspace = new Workspace();
        workspace.setId(null);
        workspace.setWorkspace(data);
        workspace.setBase62(generateBase62Number());
        em.persist(workspace);

        String out = "Workspace saved: http://javalab.co/" + workspace.getBase62();
        return Response.ok().entity(out).build();
    }

    private String generateBase62Number() {
        TypedQuery<Integer> query = em.createNamedQuery("Workspace.findIdLastRow", Integer.class);
        int lastId = query.getSingleResult();
        return Base62.fromBase10WithOffset(lastId + 1, OFFSET);
    }

    @POST
    @Path("/new")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newWorkspace(Request req) {
        String out = "new workspace";
        return Response.ok().entity(out).build();
    }

}
