package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.Language;
import com.smorales.javalab.workspaceprocessor.boundary.rest.model.JsonModelCreator;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ProjectCache {

    private Map<Language, JsonObject> projectsCache;

    @Inject
    Logger tracer;

    @Inject
    JsonModelCreator jsonModelCreator;

    @PostConstruct
    private void readProjects() {
        projectsCache = new ConcurrentHashMap<>();
        projectsCache.put(Language.GROOVY, jsonModelCreator.readProjectAsJson(Language.GROOVY));
        projectsCache.put(Language.JAVA, jsonModelCreator.readProjectAsJson(Language.JAVA));
        projectsCache.put(Language.SCALA, jsonModelCreator.readProjectAsJson(Language.SCALA));
    }

    public JsonObject get(String lang) {
        Language language = Language.from(lang);
        return projectsCache.get(language);
    }

}
