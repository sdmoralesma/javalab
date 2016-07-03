package com.smorales.javalab.workspaceprocessor.boundary.rest.model;

import com.smorales.javalab.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.workspaceprocessor.boundary.Language;
import com.smorales.javalab.workspaceprocessor.control.ConsoleMsgInitializer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

public class JsonModelCreator {

    public static final String FA_FOLDER_OPEN = "fa-folder-open";
    public static final String FA_TH_LARGE = "fa-th-large";
    public static final String FA_FOLDER = "fa-folder";
    public static final String FA_FILE_TEXT_O = "fa-file-text-o";

    public static final String HELLO_WORLD_NODE_ID = "c91e25db-5487-4de4-a8d5-0353a2920c7b";
    public static final String HELLO_WORLD_TEST_NODE_ID = "dcdba544-c787-4627-94d5-d952b2a38e2b";

    @Inject
    Logger tracer;

    @Inject
    ConsoleMsgInitializer consoleMsgInitializer;

    @Inject
    LanguageSourcesReader languageSourcesReader;

    private String terminalMessage;
    private String initialNode;

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
                .add("filesTree", createTreedataNode(languageSourcesReader.readFilesRecursively(rootDir), lang))
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
                .add("languageMode", lang.getMode())
                .add("initialNode", this.initialNode)
                .add("runnable", this.initialNode)
                .add("action", "RUN")
                .add("javaClasses", Json.createArrayBuilder()
                        .add(HELLO_WORLD_NODE_ID)
                        .add(HELLO_WORLD_TEST_NODE_ID)
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
        String key = entry(map, LanguageSourcesReader.INIT_DEPS_REGEX);
        return Json.createObjectBuilder()
                .add("id", "5bf3fa6a-4220-4036-bb57-6e0266d233e5")
                .add("label", "dependencies")
                .add("icon", FA_FILE_TEXT_O)
                .add("data", map.get(key))
                .add("children", Json.createArrayBuilder().build())
                .build();
    }

    private JsonObject createHelloWorldNode(Map<String, String> map, Language lang) {
        String srcMainDirUUID = "a3d6bf2a-8031-4e11-a7cf-4199ec82a195";
        String packageUUID = "d8b7b7ac-0e99-473d-ad9d-3b4177e26ad2";
        String filename = entry(map, LanguageSourcesReader.HELLO_WORLD_REGEX);

        this.initialNode = HELLO_WORLD_NODE_ID;
        JsonValue helloWorldNode = Json.createObjectBuilder()
                .add("id", this.initialNode)
                .add("label", filename)
                .add("icon", FA_FILE_TEXT_O)
                .add("parentId", packageUUID)
                .add("data", map.get(filename))
                .add("children", Json.createArrayBuilder().build())
                .build();

        JsonValue packageNode = Json.createObjectBuilder()
                .add("id", packageUUID)
                .add("label", "com.company.project")
                .add("icon", FA_TH_LARGE)
                .add("parentId", srcMainDirUUID)
                .add("children", Json.createArrayBuilder().add(helloWorldNode))
                .build();

        return Json.createObjectBuilder()
                .add("id", srcMainDirUUID)
                .add("label", "src/main/" + lang.getDescription() + File.separator)
                .add("expandedIcon", FA_FOLDER_OPEN)
                .add("collapsedIcon", FA_FOLDER)
                .add("children", Json.createArrayBuilder().add(packageNode))
                .build();
    }

    private JsonObject createHelloWorldTestNode(Map<String, String> map, Language lang) {
        String srcTestDirUUID = "4ac7a8cc-f99c-4d17-8fbd-2d6caebf56e1";
        String packageUUID = "b1d1a112-dfdd-4808-a672-5fd5444e17b7";
        String filename = entry(map, LanguageSourcesReader.HELLO_WORLD_TEST_REGEX);

        JsonValue helloWorldTestNode = Json.createObjectBuilder()
                .add("id", HELLO_WORLD_TEST_NODE_ID)
                .add("label", filename)
                .add("icon", FA_FILE_TEXT_O)
                .add("parentId", packageUUID)
                .add("data", map.get(filename))
                .add("children", Json.createArrayBuilder().build())
                .build();

        JsonValue packageNode = Json.createObjectBuilder()
                .add("id", packageUUID)
                .add("label", "com.company.project")
                .add("icon", FA_TH_LARGE)
                .add("parentId", srcTestDirUUID)
                .add("children", Json.createArrayBuilder().add(helloWorldTestNode))
                .build();

        return Json.createObjectBuilder()
                .add("id", srcTestDirUUID)
                .add("label", "src/test/" + lang.getDescription() + File.separator)
                .add("expandedIcon", FA_FOLDER_OPEN)
                .add("collapsedIcon", FA_FOLDER)
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

}
