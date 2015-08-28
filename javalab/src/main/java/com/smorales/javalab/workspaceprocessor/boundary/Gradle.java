package com.smorales.javalab.workspaceprocessor.boundary;


import com.smorales.javalab.workspaceprocessor.boundary.rest.RunnableNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


class Gradle extends BuildTool {

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
                    "}\n" +
                    "\n" +
                    "dependencies {\n" +
                    "    compile 'joda-time:joda-time:2.8'\n" +
                    "    compile 'com.google.guava:guava:18.0'\n" +
                    "    compile 'org.apache.commons:commons-lang3:3.4'\n" +
                    "    testCompile 'junit:junit:4.12'\n" +
                    "}\n";

            info = info.replace("{runnableClassPath}", runnableNode.getPath());


            Files.write(gradleFile, info.getBytes());
        } catch (IOException e) {
            throw new NotRunnableCodeException("can not create build.gradle file");
        }
    }
}
