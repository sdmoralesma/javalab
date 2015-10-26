package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.Request;
import com.smorales.javalab.workspaceprocessor.control.ProjectCache;
import com.smorales.javalab.workspaceprocessor.entity.User;
import com.smorales.javalab.workspaceprocessor.entity.Workspace;
import com.smorales.javalab.workspaceprocessor.tracing.TimeLogger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.StringReader;
import java.util.logging.Logger;

@Stateless
@Interceptors({TimeLogger.class})
public class WorkspaceProcessor {

    @PersistenceContext
    EntityManager em;

    @Inject
    BuildTool buildTool;

    @Inject
    ProjectCache projectCache;

    @Inject
    Logger tracer;

    public JsonObject initialize(String lang) {
        JsonObject jsonProject = projectCache.get(lang);
        Workspace workspace = new Workspace(1, jsonProject.toString());
        return Json.createReader(new StringReader(workspace.getJson())).readObject();
    }

    public JsonObject getById(String labId) {
        try {
            Workspace workspace = em.find(Workspace.class, labId);
            return Json.createReader(new StringReader(workspace.getJson())).readObject();
        } catch (NoResultException ex) {
            return Json.createObjectBuilder().add("output", "no workspace available").build();
        }
    }

    public String runCode(Request req) {
        tracer.info("RUN CODE >>> \n" + req.toString());
        return buildTool.runCode(req.getTreedata(), req.getRunnableNode(), req.getInitConfig());
    }

    public String runTests(Request req) {
        tracer.info("RUN TEST >>> \n" + req.toString());
        return buildTool.testCode(req.getTreedata(), req.getRunnableNode(), req.getInitConfig());
    }

    public Integer save(String data) {
        Workspace workspace = new Workspace();
        workspace.setId(null);
        workspace.setJson(data);
        workspace.setUserid(em.getReference(User.class, 1));
        em.persist(workspace);
        return workspace.getId();
    }

    public String newWorkspace() {
        return "new workspace";
    }
}

