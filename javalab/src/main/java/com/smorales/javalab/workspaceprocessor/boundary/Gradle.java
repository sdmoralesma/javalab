package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.request.Config;
import com.smorales.javalab.workspaceprocessor.control.FileManager;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class Gradle extends BuildTool {

    private static final String DEPENDENCIES = "/dependencies";

    @Inject
    Logger tracer;

    @Inject
    FileManager fileManager;

    @Override
    protected String buildRunCommand(Path tempDir) {
        return "gradle --build-file " + tempDir.toAbsolutePath() + "/build.gradle" + " run";
    }

    @Override
    protected String buildTestCommand(Path tempDir) {
        return "gradle --build-file " + tempDir.toAbsolutePath() + "/build.gradle" + " test";
    }

    @Override
    protected void createAuxFiles(Path tempDir, Config config, List<SimpleNode> simpleNodes) {
        Objects.requireNonNull(tempDir);
        Objects.requireNonNull(config);

        try {
            String pathByLang = LabPaths.pathByLanguage(Language.from(config.getLanguage())).asString();
            String template = new String(Files.readAllBytes(Paths.get(pathByLang, "build.template")));

            SimpleNode simpleNode = fileManager.findSimpleNode(new SimpleNode(config.getRunnable()), simpleNodes);
            Path path = fileManager.calculatePathForNode(simpleNode, simpleNodes);
            String runnableClassName = path.toString()
                    .replaceAll("src/main/java/", "")
                    .replaceAll("\\/", "\\.");
            String removedExtension = fileManager.removeExtension(runnableClassName);

            template = template.replace("{runnableClassPath}", removedExtension);
            template = template.replace("{dependenciesSet}", readDependencies(tempDir));
            Path buildGradleFile = Files.createFile(Paths.get(tempDir + "/build.gradle"));

            tracer.info("template: " + template);

            Files.write(buildGradleFile, template.getBytes());


            fileManager.printAllFilesInFolder(tempDir);
        } catch (IOException e) {
            tracer.severe(e::getMessage);
            throw new NotRunnableCodeException("Cannot create AUX files");
        }
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
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            tracer.severe(e::getMessage);
            throw new NotRunnableCodeException("Cannot read dependencies file");
        }
    }

    private void findInvalidDependencies(Set<String> deps) {
        String invalidDeps = deps.stream()
                .filter(dep -> !this.validateDependency(dep))
                .collect(Collectors.joining(", "));
        if (!invalidDeps.isEmpty()) {
            throw new NotRunnableCodeException("Invalid Dependendencies : \n" + invalidDeps);
        }
    }

    /**
     * Validates line structure, for example: {@code testCompile 'org.hibernate:hibernate-core:3.6.7.Final'}
     */
    private boolean validateDependency(String line) {
        return line.matches("(testCompile|compile)\\s('|\")[-_.\\w]*:[\\w_.-]*:[_\\[0-9_._+_,_\\)\\w-]*('|\")");
    }

}
