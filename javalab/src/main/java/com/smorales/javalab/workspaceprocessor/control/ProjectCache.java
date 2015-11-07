package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ProjectCache {

    private static final String HELLO_WORLD_REGEX = "HelloWorld\\.(java|scala|groovy)";
    private static final String HELLO_WORLD_TEST_REGEX = "HelloWorldTest\\.(java|scala|groovy)";
    private static final String INIT_DEPS_REGEX = "init-deps";

    private Map<Language, JsonObject> projectsCache;
    private String consoleMessage;

    @Inject
    Logger tracer;

    @Inject
    ConsoleMsgInitializer consoleMsgInitializer;

    @PostConstruct
    private void readProjects() {
        projectsCache = new ConcurrentHashMap<>();
        consoleMessage = consoleMsgInitializer.get();
        projectsCache.put(Language.GROOVY, readProjectAsJson(Language.GROOVY));
        projectsCache.put(Language.JAVA, readProjectAsJson(Language.JAVA));
        projectsCache.put(Language.SCALA, readProjectAsJson(Language.SCALA));
    }

    public JsonObject get(String lang) {
        Language language = Language.from(lang);
        return projectsCache.get(language);
    }

    private JsonObject readProjectAsJson(Language lang) {
        Path rootDir = LabPaths.pathByLanguage(lang).asPath();
        tracer.info(() -> "Reading project: " + rootDir);
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("console", consoleMessage)
                .add("treedata", createTreedataNode(readContentAllFilesRecursively(rootDir), lang))
                .add("runnableNode", createRunnableNode())
                .add("initConfig", createInitConfigNode(lang))
                .build();
        tracer.info(() -> "Json for project: \n" + jsonObject.toString());
        return jsonObject;
    }

    private JsonObject createInitConfigNode(Language lang) {
        return Json.createObjectBuilder()
                .add("language", lang.getDescription())
                .add("languageMode", "ace/mode/" + lang.getDescription())
                .add("javaClasses", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("id", 211)
                                .add("name", "HelloWorld" + lang.getExtension())
                                .add("path", "com.company.project.HelloWorld")
                                .build())
                        .add(Json.createObjectBuilder()
                                .add("id", 311)
                                .add("name", "HelloWorldTest" + lang.getExtension())
                                .add("path", "com.company.project.HelloWorldTest")
                                .build())
                        .build())
                .build();
    }

    private JsonArray createTreedataNode(Map<String, String> filesMap, Language lang) {
        return Json.createArrayBuilder()
                .add(createDependenciesNode(filesMap))
                .add(createHelloWorldNode(filesMap, lang))
                .add(createHelloWorldTestNode(filesMap, lang))
                .build();
    }

    private JsonObject createDependenciesNode(Map<String, String> map) {
        String key = entry(map, INIT_DEPS_REGEX);
        return Json.createObjectBuilder()
                .add("id", 1)
                .add("name", "dependencies")
                .add("type", "file")
                .add("code", map.get(key))
                .add("children", Json.createArrayBuilder().build())
                .build();
    }

    private JsonObject createHelloWorldNode(Map<String, String> map, Language lang) {
        String key = entry(map, HELLO_WORLD_REGEX);
        JsonValue helloWorldNode = Json.createObjectBuilder()
                .add("id", 211)
                .add("name", key)
                .add("type", "file")
                .add("code", map.get(key))
                .add("cursor", "")
                .add("children", Json.createArrayBuilder().build())
                .build();

        JsonValue packageNode = Json.createObjectBuilder()
                .add("id", 21)
                .add("name", "com.company.project")
                .add("type", "folder")
                .add("children", Json.createArrayBuilder().add(helloWorldNode))
                .build();

        return Json.createObjectBuilder()
                .add("id", 2)
                .add("name", "src/main/" + lang.getDescription() + "/")
                .add("type", "folder")
                .add("children", Json.createArrayBuilder().add(packageNode))
                .build();
    }

    private String entry(Map<String, String> map, String regex) {
        Optional<String> first = map.entrySet().stream()
                .map(Map.Entry::getKey)
                .filter(k -> k.matches(regex))
                .findFirst();
        return first.get();
    }

    private JsonObject createHelloWorldTestNode(Map<String, String> map, Language lang) {
        String key = entry(map, HELLO_WORLD_TEST_REGEX);
        JsonValue helloWorldTestNode = Json.createObjectBuilder()
                .add("id", 311)
                .add("name", key)
                .add("type", "file")
                .add("code", map.get(key))
                .add("cursor", "")
                .add("children", Json.createArrayBuilder().build())
                .build();

        JsonValue packageNode = Json.createObjectBuilder()
                .add("id", 31)
                .add("name", "com.company.project")
                .add("type", "folder")
                .add("children", Json.createArrayBuilder().add(helloWorldTestNode))
                .build();

        return Json.createObjectBuilder()
                .add("id", 3)
                .add("name", "src/test/" + lang.getDescription() + "/")
                .add("type", "folder")
                .add("children", Json.createArrayBuilder().add(packageNode))
                .build();
    }

    private JsonObject createRunnableNode() {
        return Json.createObjectBuilder()
                .add("id", "undefined")
                .add("mainClass", false)
                .add("testClass", false)
                .build();
    }

    private Map<String, String> readContentAllFilesRecursively(final Path path) {
        try {
            return Files.walk(path)
                    .filter(p -> !p.toFile().isDirectory())
                    .filter(p -> p.getFileName().toString().matches(HELLO_WORLD_REGEX)
                            || p.getFileName().toString().matches(HELLO_WORLD_TEST_REGEX)
                            || p.getFileName().toString().matches(INIT_DEPS_REGEX))
                    .collect(Collectors.toMap(p -> p.getFileName().toString(), this::linesByFile));
        } catch (final IOException e) {
            tracer.severe(e.getMessage());
            throw new NotRunnableCodeException("Cannot walk path: " + path);
        }
    }

    private String linesByFile(final Path path) {
        try {
            return new String(Files.readAllBytes(path), Charset.forName("UTF-8"));
        } catch (final IOException e) {
            tracer.severe(e.getMessage());
            throw new NotRunnableCodeException("Cannot read file: " + path);
        }
    }
}
