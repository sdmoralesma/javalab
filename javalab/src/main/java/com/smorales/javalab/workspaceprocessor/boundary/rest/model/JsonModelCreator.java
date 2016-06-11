package com.smorales.javalab.workspaceprocessor.boundary.rest.model;

import com.smorales.javalab.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;
import com.smorales.javalab.workspaceprocessor.control.ConsoleMsgInitializer;
import com.smorales.javalab.workspaceprocessor.control.Language;

import javax.annotation.PostConstruct;
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
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class JsonModelCreator {

    private static final String HELLO_WORLD_REGEX = "HelloWorld\\.(java|scala|groovy)";
    private static final String HELLO_WORLD_TEST_REGEX = "HelloWorldTest\\.(java|scala|groovy)";
    private static final String INIT_DEPS_REGEX = "init-deps";

    @Inject
    Logger tracer;

    @Inject
    ConsoleMsgInitializer consoleMsgInitializer;

    private String terminalMessage;

    @PostConstruct
    private void init() {
        terminalMessage = consoleMsgInitializer.get();
    }

    public JsonObject readProjectAsJson(Language lang) {
        Path rootDir = LabPaths.pathByLanguage(lang).asPath();
        tracer.info(() -> "Reading project: " + rootDir);
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("terminal", terminalMessage)
                .add("description", "hardcoded description")
                .add("tags", createTagsNode())
                .add("filesTree", createTreedataNode(readContentAllFilesRecursively(rootDir), lang))
                .add("config", createConfigNode(lang))
                .build();
        tracer.info(() -> "Json for project: \n" + jsonObject.toString());
        return jsonObject;
    }

    private JsonArray createTagsNode() {
        return Json.createArrayBuilder()
                .add("hardcoded-tag1")
                .add("hardcoded-tag2")
                .add("hardcoded-tag3")
                .build();
    }

    private JsonObject createConfigNode(Language lang) {
        return Json.createObjectBuilder()
                .add("language", lang.getDescription())
                .add("languageMode", "ace/mode/" + lang.getDescription())
                .add("javaClasses", Json.createArrayBuilder()
                        .add(generateUUID())
                        .add(generateUUID())
                        .build())
                .build();
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
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
                .add("id", this.generateUUID())
                .add("name", "dependencies")
                .add("type", "file")
                .add("code", map.get(key))
                .add("children", Json.createArrayBuilder().build())
                .build();
    }

    private JsonObject createHelloWorldNode(Map<String, String> map, Language lang) {
        String key = entry(map, HELLO_WORLD_REGEX);
        JsonValue helloWorldNode = Json.createObjectBuilder()
                .add("id", this.generateUUID())
                .add("name", key)
                .add("type", "file")
                .add("code", map.get(key))
                .add("cursor", "")
                .add("children", Json.createArrayBuilder().build())
                .build();

        JsonValue packageNode = Json.createObjectBuilder()
                .add("id", this.generateUUID())
                .add("name", "com.company.project")
                .add("type", "folder")
                .add("children", Json.createArrayBuilder().add(helloWorldNode))
                .build();

        return Json.createObjectBuilder()
                .add("id", this.generateUUID())
                .add("name", "src/main/" + lang.getDescription() + "/")
                .add("type", "folder")
                .add("children", Json.createArrayBuilder().add(packageNode))
                .build();
    }

    private JsonObject createHelloWorldTestNode(Map<String, String> map, Language lang) {
        String key = entry(map, HELLO_WORLD_TEST_REGEX);
        JsonValue helloWorldTestNode = Json.createObjectBuilder()
                .add("id", this.generateUUID())
                .add("name", key)
                .add("type", "file")
                .add("code", map.get(key))
                .add("cursor", "")
                .add("children", Json.createArrayBuilder().build())
                .build();

        JsonValue packageNode = Json.createObjectBuilder()
                .add("id", this.generateUUID())
                .add("name", "com.company.project")
                .add("type", "folder")
                .add("children", Json.createArrayBuilder().add(helloWorldTestNode))
                .build();

        return Json.createObjectBuilder()
                .add("id", this.generateUUID())
                .add("name", "src/test/" + lang.getDescription() + "/")
                .add("type", "folder")
                .add("children", Json.createArrayBuilder().add(packageNode))
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


    private String entry(Map<String, String> map, String regex) {
        Optional<String> first = map.entrySet().stream()
                .map(Map.Entry::getKey)
                .filter(k -> k.matches(regex))
                .findFirst();
        return first.get();
    }

}
