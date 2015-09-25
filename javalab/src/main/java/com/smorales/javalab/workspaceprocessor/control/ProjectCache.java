package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ProjectCache {

    @Inject
    Logger tracer;

    private static final String GIT_IGNORE_FILE = ".gitignore";
    private static final String BUILD_GRADLE_FILE = "build.gradle";
    private static final String JAVALAB_LATEST_VERSION = "https://api.github.com/repos/sdmoralesma/javalab/releases/latest";
    private static final String WELCOME_MSG_TEMPLATE = "Welcome to Javalab {labVersion} !\r\n$ java -version : {javaVersion} Java HotSpot(TM) 64-Bit Server VM";

    private static final String HELLO_WORLD_REGEX = "HelloWorld\\.(.)*";
    private static final String HELLO_WORLD_TEST_REGEX = "HelloWorldTest\\.(.)*";

    private Map<String, JsonObject> projectsCache;
    private String consoleMessage;

    @PostConstruct
    public void readProjects() {
        projectsCache = new ConcurrentHashMap<>();
        consoleMessage = initializeConsoleMessage();
        projectsCache.put("groovy", readProjectAsJson(LabPaths.HOME.append("groovy-gradle")));
        projectsCache.put("scala", readProjectAsJson(LabPaths.HOME.append("scala-gradle")));
    }

    private JsonObject readProjectAsJson(final Path rootDir) {
        tracer.info(() -> "Reading project: " + rootDir);
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("console", consoleMessage);
        jsonBuilder.add("treedata", createTreedataNode(readContentAllFilesRecursively(rootDir)));
        jsonBuilder.add("runnableNode", createRunnableNode());

        JsonObject jsonObject = jsonBuilder.build();
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
        Map<String, String> map = new HashMap<>();
        try {
            Files.walk(path)
                    .filter(p -> !p.toFile().isDirectory())
                    .filter(p -> !p.getFileName().toString().equals(GIT_IGNORE_FILE))
                    .filter(p -> !p.getFileName().toString().equals(BUILD_GRADLE_FILE))
                    .forEach(p -> {
                        String fileName = p.getFileName().toString();
                        if (fileName.matches(HELLO_WORLD_REGEX) || fileName.matches(HELLO_WORLD_TEST_REGEX)) {
                            map.put(fileName, linesByFile(p));
                        }
                    });
        } catch (final IOException e) {
            tracer.severe(e.getMessage());
        }
        return map;
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
