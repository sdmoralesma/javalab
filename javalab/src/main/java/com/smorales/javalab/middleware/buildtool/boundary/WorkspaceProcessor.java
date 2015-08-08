package com.smorales.javalab.middleware.buildtool.boundary;

import com.smorales.javalab.middleware.buildtool.control.Base62;
import com.smorales.javalab.middleware.buildtool.entity.Workspace;
import com.smorales.javalab.middleware.buildtool.rest.Request;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.StringReader;

@Stateless
public class WorkspaceProcessor {

    public static final int OFFSET = 1000000;

    @PersistenceContext
    EntityManager em;

    public JsonObject initialize() {
        Workspace workspace = em.createNamedQuery("Workspace.findFirstRow", Workspace.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);

        return Json.createReader(new StringReader(workspace.getWorkspace())).readObject();
    }

    public JsonObject getByBase62(String base62) {
        TypedQuery<Workspace> query = em.createNamedQuery("Workspace.findByBase62", Workspace.class);
        query.setParameter("base62", base62);
        Workspace workspace;

        try {
            workspace = query.getSingleResult();
        } catch (NoResultException ex) {
            throw new IllegalStateException("Exists more than 1 workspace with the same base62 id");
        }

        return Json.createReader(new StringReader(workspace.getWorkspace())).readObject();
    }

    public String runCode(Request req) {
        BuildTool buildTool = BuildTool.get(BuildTool.Type.JAVAC, req.getTreedata(), req.getLibraries(), req.getRunnableNode());
        return buildTool.runCode();
    }

    public String runTests(Request req) {
        BuildTool buildTool = BuildTool.get(BuildTool.Type.JAVAC, req.getTreedata(), req.getLibraries(), req.getRunnableNode());
        return buildTool.testCode();
    }

    public String save(String data) {
        Workspace workspace = new Workspace();
        workspace.setId(null);
        workspace.setWorkspace(data);
        workspace.setBase62(generateBase62Number());
        em.persist(workspace);

        return "Workspace saved: http://javalab.co/" + workspace.getBase62();
    }

    private String generateBase62Number() {
        int lastId = em.createNamedQuery("Workspace.findIdLastRow", Integer.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);
        return Base62.fromBase10WithOffset(lastId + 1, OFFSET);
    }

    public String newWorkspace(Request req) {
        return "new workspace";
    }
}

