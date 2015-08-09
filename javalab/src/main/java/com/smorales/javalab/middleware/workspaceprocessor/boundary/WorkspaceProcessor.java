package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import com.smorales.javalab.middleware.workspaceprocessor.boundary.rest.Request;
import com.smorales.javalab.middleware.workspaceprocessor.control.Base62;
import com.smorales.javalab.middleware.workspaceprocessor.entity.Workspace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.StringReader;

@Stateless
public class WorkspaceProcessor {

    private static final int OFFSET = 1000000;

    @PersistenceContext
    EntityManager em;

    @Inject
    Base62 base62;

    public JsonObject initialize() {
        Workspace workspace = em.createNamedQuery(Workspace.findFirstRow, Workspace.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);

        return Json.createReader(new StringReader(workspace.getWorkspace())).readObject();
    }

    public JsonObject getByBase62(String base62) {
        Workspace workspace = em.createNamedQuery(Workspace.findByBase62, Workspace.class)
                .setParameter("base62", base62)
                .getSingleResult();

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
        return workspace.getBase62();
    }

    private String generateBase62Number() {
        int lastId = em.createNamedQuery(Workspace.findIdLastRow, Integer.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);
        return base62.fromBase10WithOffset(lastId + 1, OFFSET);
    }

    public String newWorkspace(Request req) {
        return "new workspace";
    }
}

