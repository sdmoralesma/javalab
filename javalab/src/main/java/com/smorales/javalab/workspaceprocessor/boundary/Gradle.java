package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.entity.Library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Gradle extends BuildTool {

    @Override
    protected String buildCompileCommand(Path tempDir, List<Path> files, List<Library> libraries) {
        return "gradle -b " + tempDir.toAbsolutePath() + "/build.gradle" + " compileJava";
    }

    @Override
    protected String buildRunCommand(Path tempDir, List<Path> mainClass, List<Library> libraries) {
        return "gradle  -b " + tempDir.toAbsolutePath() + "/build.gradle" + " run";
    }

    @Override
    protected String buildTestCommand(Path tempDir, List<Path> testClass, List<Library> libraries) {
        return "gradle  -b " + tempDir.toAbsolutePath() + "/build.gradle" + " test";
    }

    @Override
    protected void createAuxFiles(Path tempDir) {
        try {
            Path gradleFile = Files.createFile(Paths.get(tempDir + "/build.gradle"));
            String info = "apply plugin:'application'\n" +
                    "mainClassName = \"com.company.project.HelloWorld\"\n" +
                    "\n" +
                    "apply plugin: 'java'\n" +
                    "sourceCompatibility = '1.8'\n" +
                    "[compileJava, compileTestJava]*.options*.encoding = 'UTF-8'\n" +
                    "\n" +
                    "repositories {\n" +
                    "    mavenCentral()\n" +
                    "}\n" +
                    "\n" +
                    "dependencies {\n" +
                    "    compile 'joda-time:joda-time:2.8'\n" +
                    "    compile 'com.google.guava:guava:18.0'\n" +
                    "    compile 'org.apache.commons:commons-lang3:3.4'\n" +
                    "    testCompile 'junit:junit:4.12'\n" +
                    "}\n";
            Files.write(gradleFile, info.getBytes());
        } catch (IOException e) {
            throw new NotRunnableCodeException("can not create build.gradle file");
        }
    }
}
