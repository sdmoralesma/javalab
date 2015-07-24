package com.smorales.javalab.middleware.buildtool.boundary;

import com.smorales.javalab.middleware.buildtool.entity.Library;
import com.smorales.javalab.middleware.buildtool.entity.TreeData;
import com.smorales.javalab.middleware.buildtool.rest.RunnableNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class JavaC extends BuildTool {

    private static final String JAVAC_EXEC = "javac";
    private static final String JAVA_EXEC = "java";
    private static final String JAVA_EXTENSION = ".java";

    JavaC(List<TreeData> treedata, List<Library> libraries, RunnableNode runnableNode) {
        super(treedata, libraries, runnableNode);
    }

    @Override
    protected String buildCompileCommand(Path tempDir, List<Path> files) {
        String cmd = "{javacExec} {javaFiles} -d {buildPath} -cp {buildPath}:{libraries}";
        cmd = cmd.replace("{javacExec}", JAVAC_EXEC);
        cmd = cmd.replace("{javaFiles}", filesToCompileAsString(files));
        cmd = cmd.replace("{buildPath}", getBuildPath(tempDir));
        cmd = cmd.replace("{libraries}", dependenciesAsString());
        return cmd;
    }

    @Override
    protected String buildRunCommand(Path tempDir, List<Path> files, List<Path> mainClass) {
        String cmd = "{javaExec} -cp {buildPath}:{libraries} {mainClass}";
        cmd = cmd.replace("{javaExec}", JAVA_EXEC);
        cmd = cmd.replace("{buildPath}", getBuildPath(tempDir));
        cmd = cmd.replace("{libraries}", dependenciesAsString());
        cmd = cmd.replace("{mainClass}", getMainClass(tempDir, mainClass));
        return cmd;
    }

    @Override
    protected String buildTestCommand(Path tempDir, List<Path> files, List<Path> testClass) {
        String cmd = "{javaExec} -cp {buildPath}:{libraries} org.junit.runner.JUnitCore {testClass}";
        cmd = cmd.replace("{javaExec}", JAVA_EXEC);
        cmd = cmd.replace("{buildPath}", getBuildPath(tempDir));
        cmd = cmd.replace("{libraries}", dependenciesAsString());
        cmd = cmd.replace("{testClass}", getTestClass(tempDir, testClass));
        return cmd;
    }

    private String getBuildPath(Path tempDir) {
        Path buildDir = Paths.get(tempDir.toString(), "/", "build");
        try {
            Path directories = Files.createDirectories(buildDir);
            return directories.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NotRunnableCodeException("Can not create {tempDir}/build dir");
        }
    }

    private String filesToCompileAsString(List<Path> files) {
        StringBuilder builder = new StringBuilder();
        for (Path path : files) {
            if (path.getFileName().toString().contains(JAVA_EXTENSION)) {
                builder.append(path.toAbsolutePath().toString())
                        .append(" ");
            }
        }

        return builder.toString();
    }

    private String dependenciesAsString() {
        StringBuilder builder = new StringBuilder();
        for (Library library : libraries) {
            builder.append(LabPaths.REPOSITORY_DIR.getPathAsString())
                    .append(library.getJar())
                    .append(":");
        }
        return builder.toString().equals("") ? LabPaths.REPOSITORY_DIR.getPathAsString() : builder.toString();
    }

    private String getMainClass(Path tempDir, List<Path> classFiles) {

        StringBuilder builder = new StringBuilder();
        for (Path file : classFiles) {
            String relativePath = file.toString().replaceAll(tempDir.toString() + "/", "");
            relativePath = relativePath.replaceAll("src/main/java/", "");
            String removedExtension = relativePath.replaceAll(JAVA_EXTENSION, "");
            String javaClass = removedExtension.replaceAll("\\/", "\\.");
            builder.append(javaClass).append(" ");
        }
        return builder.toString();
    }

    private String getTestClass(Path tempDir, List<Path> classFiles) {

        StringBuilder builder = new StringBuilder();
        for (Path file : classFiles) {
            String relativePath = file.toString().replaceAll(tempDir.toString() + "/", "");
            relativePath = relativePath.replaceAll("src/test/java/", "");
            String removedExtension = relativePath.replaceAll(JAVA_EXTENSION, "");
            String javaClass = removedExtension.replaceAll("\\/", "\\.");
            builder.append(javaClass).append(" ");
        }

        return builder.toString();
    }
}