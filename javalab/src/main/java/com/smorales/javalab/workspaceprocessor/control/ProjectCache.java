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
import java.io.InputStream;
import java.net.URL;
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

    private static final String JAVALAB_LATEST_VERSION = "https://api.github.com/repos/sdmoralesma/javalab/releases/latest";
    private static final String WELCOME_MSG_TEMPLATE = "Welcome to Javalab {labVersion} !\r\n$ java -version : {javaVersion} Java HotSpot(TM) 64-Bit Server VM";
    private static final String HELLO_WORLD_REGEX = "HelloWorld\\.(.)*";
    private static final String HELLO_WORLD_TEST_REGEX = "HelloWorldTest\\.(.)*";

    private Map<Language, JsonObject> projectsCache;
    private String consoleMessage;

    @Inject
    Logger tracer;

    @PostConstruct
    private void readProjects() {
        projectsCache = new ConcurrentHashMap<>();
        consoleMessage = initializeConsoleMessage();
        projectsCache.put(Language.JAVA, readProjectAsJson(LabPaths.JAVA_PROJECT.asPath()));
        projectsCache.put(Language.GROOVY, readProjectAsJson(LabPaths.GROOY_PROJECT.asPath()));
        projectsCache.put(Language.SCALA, readProjectAsJson(LabPaths.SCALA_PROJECT.asPath()));
    }

    public JsonObject get(Language language) {
        return projectsCache.get(language);
    }

    private JsonObject readProjectAsJson(Path rootDir) {
        tracer.info(() -> "Reading project: " + rootDir);
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("console", consoleMessage)
                .add("treedata", createTreedataNode(readContentAllFilesRecursively(rootDir)))
                .add("runnableNode", createRunnableNode())
                .build();
        tracer.info(() -> "Json for project: \n" + jsonObject.toString());
        return jsonObject;
    }

    private JsonArray createTreedataNode(Map<String, String> filesMap) {
        return Json.createArrayBuilder()
                .add(createDependenciesNode())
                .add(createHelloWorldNode(filesMap))
                .add(createHelloWorldTestNode(filesMap))
                .build();
    }

    private JsonObject createDependenciesNode() {
        return Json.createObjectBuilder()
                .add("id", 1)
                .add("name", "dependencies")
                .add("type", "file")
                .add("code", "testCompile 'junit:junit:4.+'")
                .add("children", Json.createArrayBuilder().build())
                .build();
    }

    private JsonObject createHelloWorldNode(Map<String, String> map) {
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
                .add("name", "src/main/java/")
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

    private JsonObject createHelloWorldTestNode(Map<String, String> map) {
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
                .add("name", "src/test/java/")
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

    private String initializeConsoleMessage() {
        String template = WELCOME_MSG_TEMPLATE;
        template = template.replace("{labVersion}", readJavalabVersion());
        template = template.replace("{javaVersion}", System.getProperty("java.version"));
        return template;
    }

    private String readJavalabVersion() {
        try {
            URL url = new URL(JAVALAB_LATEST_VERSION);
            InputStream is = url.openStream();
            return Json.createReader(is)
                    .readObject()
                    .getString("tag_name");
        } catch (IOException ex) {
            throw new NotRunnableCodeException("Cannot read javalab version from URL: " + JAVALAB_LATEST_VERSION);
        }
    }

    private Map<String, String> readContentAllFilesRecursively(final Path path) {
        try {
            return Files.walk(path)
                    .filter(p -> !p.toFile().isDirectory())
                    .filter(p -> p.getFileName().toString().matches(HELLO_WORLD_REGEX)
                            || p.getFileName().toString().matches(HELLO_WORLD_TEST_REGEX))
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
