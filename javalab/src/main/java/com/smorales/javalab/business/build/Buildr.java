package com.smorales.javalab.business.build;

import com.smorales.javalab.business.processor.boundary.LabPaths;
import com.smorales.javalab.business.processor.boundary.Language;
import com.smorales.javalab.business.NotRunnableCodeException;
import com.smorales.javalab.business.processor.boundary.SimpleNode;
import com.smorales.javalab.business.processor.boundary.rest.request.Config;
import com.smorales.javalab.business.files.FileManager;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class Buildr extends BuildTool {

    private static final String DEPENDENCIES = "/dependencies";

    @Inject
    Logger tracer;

    @Inject
    FileManager fileManager;

    @Override
    protected String buildRunCommand(Path tempDir) {
        return "buildr run";
    }

    @Override
    protected String buildTestCommand(Path tempDir) {
        return "buildr test";
    }

    @Override
    protected void createAuxFiles(Path tempDir, Config config, List<SimpleNode> simpleNodes) {
        Objects.requireNonNull(tempDir);
        Objects.requireNonNull(config);

        try {
            String pathByLang = LabPaths.pathByLanguage(Language.from(config.getLanguage())).asString();
            String template = new String(Files.readAllBytes(Paths.get(pathByLang, "buildfile.template")));

            SimpleNode simpleNode = fileManager.findSimpleNode(new SimpleNode(config.getRunnable()), simpleNodes);
            Path path = fileManager.calculatePathForNode(simpleNode, simpleNodes);
            String runnableClassName = path.toString()
                    .replaceAll("src/main/(java|groovy|scala)/", "")
                    .replaceAll("\\/", "\\.");
            String removedExtension = fileManager.removeExtension(runnableClassName);

            template = template.replace("{runnableClassPath}", removedExtension);
            template = template.replace("{dependenciesSet}", readDependencies(tempDir));
            Path buildrFile = Files.createFile(Paths.get(tempDir + "/buildfile"));

            tracer.info("template: " + template);

            Files.write(buildrFile, template.getBytes());
            fileManager.printAllFilesInFolder(tempDir);
        } catch (IOException e) {
            tracer.severe(e::getMessage);
            throw new NotRunnableCodeException("Cannot create AUX files");
        }
    }

    @Override
    protected String cleanResultMessage(String toClean) {
        for (String b : blackListedStrings()) {
            toClean = toClean.replaceAll(b, "");
        }

        return toClean;
    }

    private List<String> blackListedStrings() {
        return Arrays.asList("Trying to override old definition of datatype junit [junit] Testsuite: com.company.project.HelloWorldTest [junit]",
                "/home/wildfly/",
                "Trying to override old definition of datatype junit");
    }


    private String readDependencies(Path tempDir) {
        Path depsPath = Paths.get(tempDir + DEPENDENCIES);

        try {
            Set<String> deps = Files.readAllLines(depsPath)
                    .stream()
                    .map(String::trim)
                    .filter(dep -> !dep.isEmpty())
                    .collect(Collectors.toSet());

            findInvalidDependencies(deps);
            return deps.stream()
                    .filter(this::validateDependency)
                    .map(this::convertDependencyToBuildr)
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            tracer.severe(e::getMessage);
            throw new NotRunnableCodeException("Cannot read dependencies file");
        }
    }


    String convertDependencyToBuildr(String gradleFormat) {
        gradleFormat = gradleFormat.replaceAll("testCompile", "test.with");
        gradleFormat = gradleFormat.replaceAll("compile", "compile.with");

        String[] split = gradleFormat.split(":");
        List<String> stringList = Arrays.stream(split).collect(Collectors.toList());
        stringList.add(2, "jar");
        return stringList.stream().collect(Collectors.joining(":"));
    }

}
