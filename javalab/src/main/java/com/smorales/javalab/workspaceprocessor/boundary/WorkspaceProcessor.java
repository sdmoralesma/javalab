package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.Request;
import com.smorales.javalab.workspaceprocessor.control.Base62;
import com.smorales.javalab.workspaceprocessor.control.ProjectCache;
import com.smorales.javalab.workspaceprocessor.entity.Workspace;
import com.smorales.javalab.workspaceprocessor.tracing.TimeLogger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.StringReader;

@Stateless
@Interceptors({TimeLogger.class})
public class WorkspaceProcessor {

    private static final int OFFSET = 1000000;

    //    @PersistenceContext
    EntityManager em;

    @Inject
    Base62 base62;

    @Inject
    BuildTool buildTool;

    @Inject
    ProjectCache projectCache;

    public JsonObject initialize(String lang) {
//        Workspace workspace = em.createNamedQuery(Workspace.findFirstRow, Workspace.class)
//                .setMaxResults(1)
//                .getResultList()
//                .get(0);

        JsonObject jsonProject = projectCache.get(lang);
        Workspace workspace = new Workspace(1, "eeee", jsonProject.toString());
        return Json.createReader(new StringReader(workspace.getJson())).readObject();
    }

    public JsonObject getByBase62(String base62) {
        try {
//            Workspace workspace = em.createNamedQuery(Workspace.findByBase62, Workspace.class)
//                    .setParameter("base62", base62)
//                    .getSingleResult();
            Workspace workspace = new Workspace();
            return Json.createReader(new StringReader(workspace.getJson())).readObject();
        } catch (NoResultException ex) {
            return Json.createObjectBuilder().add("output", "no workspace available").build();
        }
    }

    public String runCode(Request req) {
        return buildTool.runCode(req.getTreedata(), req.getRunnableNode(), req.getInitConfig());
    }

    public String runTests(Request req) {
        return buildTool.testCode(req.getTreedata(), req.getRunnableNode(), req.getInitConfig());
    }

    public String save(String data) {
        Workspace workspace = new Workspace();
        workspace.setId(null);
        workspace.setJson(data);
        workspace.setPath(generateBase62Number());
//        em.persist(workspace);
        return workspace.getPath();
    }

    private String generateBase62Number() {
//        int lastId = em.createNamedQuery(Workspace.findIdLastRow, Integer.class)
//                .setMaxResults(1)
//                .getResultList()
//                .get(0);
        int lastId = 0;
        return base62.fromBase10WithOffset(lastId + 1, OFFSET);
    }

    public String newWorkspace() {
        return "new workspace";
    }
}

