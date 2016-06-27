package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.request.Request;
import com.smorales.javalab.workspaceprocessor.control.ProjectCache;
import com.smorales.javalab.workspaceprocessor.entity.Tag;
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
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
        return projectCache.get(lang);
    }

    public JsonObject getById(Integer labId) {
        try {
            Workspace workspace = em.find(Workspace.class, labId);
            return Json.createReader(new StringReader(workspace.getJson())).readObject();
        } catch (NoResultException ex) {
            return Json.createObjectBuilder().add("output", "no workspace available").build();
        }
    }

    public String runCode(Request req) {
        tracer.info("RUN CODE >>> \n" + req.toString());
        return buildTool.runCode(req.getFilesTree(), req.getConfig());
    }

    public String runTests(Request req) {
        tracer.info("RUN TEST >>> \n" + req.toString());
        return buildTool.testCode(req.getFilesTree(), req.getConfig());
    }

    public Integer save(String data) {
        Workspace workspace = new Workspace();
        workspace.setId(null);
        workspace.setJson(data);

        workspace.setDescription(getStringFromJson("description", data));
        workspace.setUserid(em.getReference(User.class, 1));
        workspace.setTags(createTags(getStringFromJson("tags", data)));
        em.persist(workspace);
        return workspace.getId();
    }

    public String newWorkspace() {
        return "new workspace";
    }


    private String getStringFromJson(String toFind, String json) {
        String script = "Java.asJSONCompatible(" + json + ")";
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("javascript");
        Map result;
        try {
            result = (Map) engine.eval(script);
        } catch (ScriptException e) {
            throw new IllegalStateException("Exception executing script engine", e);
        }

        return (String) result.get(toFind);
    }

    private Set<Tag> createTags(String tagsAsString) {
        HashSet<String> split = new HashSet<>(Arrays.asList(tagsAsString.split(",")));
        HashSet<Tag> tags = new HashSet<>();
        for (String tagName : split) {
            Tag tag = new Tag(tagName);
            tags.add(tag);
            em.persist(tag);
        }
        return tags;
    }
}

