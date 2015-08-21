package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.Request;
import com.smorales.javalab.workspaceprocessor.control.Base62;
import com.smorales.javalab.workspaceprocessor.entity.Workspace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.StringReader;

@Stateless
public class WorkspaceProcessor {

    private static final int OFFSET = 1000000;

    @PersistenceContext
    EntityManager em;

    @Inject
    Base62 base62;

    @Inject
    BuildTool buildTool;

    public JsonObject initialize() {
        Workspace workspace = em.createNamedQuery(Workspace.findFirstRow, Workspace.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);

        return Json.createReader(new StringReader(workspace.getJson())).readObject();
    }

    public JsonObject getByBase62(String base62) {
        try {
            Workspace workspace = em.createNamedQuery(Workspace.findByBase62, Workspace.class)
                    .setParameter("base62", base62)
                    .getSingleResult();
            return Json.createReader(new StringReader(workspace.getJson())).readObject();
        } catch (NoResultException ex) {
            return Json.createObjectBuilder().add("output", "no workspace available").build();
        }
    }

    public String runCode(Request req) {
        BuildToolData data = new BuildToolData(req.getTreedata(), req.getLibraries(), req.getRunnableNode());
        return buildTool.runCode(data);
    }

    public String runTests(Request req) {
        BuildToolData data = new BuildToolData(req.getTreedata(), req.getLibraries(), req.getRunnableNode());
        return buildTool.testCode(data);
    }

    public String save(String data) {
        Workspace workspace = new Workspace();
        workspace.setId(null);
        workspace.setJson(data);
        workspace.setPath(generateBase62Number());
        em.persist(workspace);
        return workspace.getPath();
    }

    private String generateBase62Number() {
        int lastId = em.createNamedQuery(Workspace.findIdLastRow, Integer.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);
        return base62.fromBase10WithOffset(lastId + 1, OFFSET);
    }

    public String newWorkspace() {
        return "new workspace";
    }
}

