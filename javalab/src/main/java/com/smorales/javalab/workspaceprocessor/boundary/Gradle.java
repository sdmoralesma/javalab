package com.smorales.javalab.workspaceprocessor.boundary;


import com.smorales.javalab.workspaceprocessor.boundary.rest.RunnableNode;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;


class Gradle extends BuildTool {

    private static final String DEPENDENCIES = "/dependencies";

    @Inject
    Logger tracer;

    @Override
    protected String buildRunCommand(Path tempDir) {
        return "gradle  -b " + tempDir.toAbsolutePath() + "/build.gradle" + " run";
    }

    @Override
    protected String buildTestCommand(Path tempDir) {
        return "gradle  -b " + tempDir.toAbsolutePath() + "/build.gradle" + " test";
    }

    @Override
    protected void createAuxFiles(Path tempDir, RunnableNode runnableNode) {
        try {
            Path gradleFile = Files.createFile(Paths.get(tempDir + "/build.gradle"));
            String info = "apply plugin:'application'\n" +
                    "mainClassName = \"{runnableClassPath}\"\n" +
                    "\n" +
                    "apply plugin: 'java'\n" +
                    "sourceCompatibility = '1.8'\n" +
                    "[compileJava, compileTestJava]*.options*.encoding = 'UTF-8'\n" +
                    "\n" +
                    "repositories {\n" +
                    "    mavenCentral()\n" +
                    "}\n" +
                    "\n" +
                    "test {\n" +
                    "    testLogging {\n" +
                    "        exceptionFormat = 'full'\n" +
                    "        showExceptions = true\n" +
                    "        showStackTraces = true\n" +
                    "    }\n" +
                    "}\n\n" +
                    "dependencies { {dependenciesSet} }";

            info = info.replace("{runnableClassPath}", runnableNode.getPath());
            info = info.replace("{dependenciesSet}", readDependencies(tempDir));

            Files.write(gradleFile, info.getBytes());
        } catch (IOException e) {
            throw new NotRunnableCodeException("can not create build.gradle file");
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
            throw new NotRunnableCodeException("can not read dependencies file");
        }
    }

    private void findInvalidDependencies(Set<String> deps) {
        String invalid = deps.stream()
                .filter(dep -> !this.validateDependency(dep))
                .collect(Collectors.joining(", "));
        if (!invalid.isEmpty()) {
            throw new NotRunnableCodeException("Invalid Dependendencies : \n" + invalid);
        }
    }

    /**
     * Validates line structure, should look like: {@code testCompile 'org.hibernate:hibernate-core:3.6.7.Final'}
     */
    private boolean validateDependency(String line) {
        return line.matches("(testCompile|compile)\\s('|\")[-_.\\w]*:[\\w_.-]*:[_\\[0-9_._+_,_\\)\\w-]*('|\")");
    }

}
