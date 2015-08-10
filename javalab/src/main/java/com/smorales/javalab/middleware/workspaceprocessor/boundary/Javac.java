package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import com.smorales.javalab.middleware.workspaceprocessor.entity.Library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;

class Javac extends BuildTool {

    private static final String JAVAC_EXEC = "javac";
    private static final String JAVA_EXEC = "java";
    private static final String JAVA_EXTENSION = ".java";

    private static final String SRC_MAIN_JAVA = "src/main/java/";
    private static final String SRC_TEST_JAVA = "src/test/java/";

    @Override
    protected String buildCompileCommand(Path tempDir, List<Path> files, List<Library> libraries) {
        String cmd = "{javacExec} {javaFiles} -d {buildPath} -cp {buildPath}:{libraries}";
        cmd = cmd.replace("{javacExec}", JAVAC_EXEC);
        cmd = cmd.replace("{javaFiles}", filesToCompileAsString(files));
        cmd = cmd.replace("{buildPath}", getBuildPath(tempDir));
        cmd = cmd.replace("{libraries}", dependenciesAsString(libraries));
        return cmd;
    }

    @Override
    protected String buildRunCommand(Path tempDir, List<Path> files, List<Path> mainClass, List<Library> libraries) {
        String cmd = "{javaExec} -cp {buildPath}:{libraries} {mainClass}";
        cmd = cmd.replace("{javaExec}", JAVA_EXEC);
        cmd = cmd.replace("{buildPath}", getBuildPath(tempDir));
        cmd = cmd.replace("{libraries}", dependenciesAsString(libraries));
        cmd = cmd.replace("{mainClass}", getMainClass(tempDir, mainClass));
        return cmd;
    }

    @Override
    protected String buildTestCommand(Path tempDir, List<Path> files, List<Path> testClass, List<Library> libraries) {
        String cmd = "{javaExec} -cp {buildPath}:{libraries} org.junit.runner.JUnitCore {testClass}";
        cmd = cmd.replace("{javaExec}", JAVA_EXEC);
        cmd = cmd.replace("{buildPath}", getBuildPath(tempDir));
        cmd = cmd.replace("{libraries}", dependenciesAsString(libraries));
        cmd = cmd.replace("{testClass}", getTestClass(tempDir, testClass));
        return cmd;
    }

    private String getBuildPath(Path tempDir) {
        Path buildDir = Paths.get(tempDir.toString(), "/", "build");
        try {
            Path directories = Files.createDirectories(buildDir);
            return directories.toString();
        } catch (IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException("Can not create {tempDir}/build dir");
        }
    }

    private String filesToCompileAsString(List<Path> files) {
        StringBuilder builder = new StringBuilder();
        files.stream()
                .filter(path -> path.getFileName().toString().contains(JAVA_EXTENSION))
                .forEach(path -> builder.append(path.toAbsolutePath().toString()).append(" "));
        return builder.toString();
    }

    private String dependenciesAsString(List<Library> libraries) {
        StringBuilder builder = new StringBuilder();
        for (Library library : libraries) {
            builder.append(LabPaths.REPOSITORY_DIR.asString())
                    .append(library.getJar())
                    .append(":");
        }
        return "".equals(builder.toString()) ? LabPaths.REPOSITORY_DIR.asString() : builder.toString();
    }

    private String getMainClass(Path tempDir, List<Path> classFiles) {
        StringBuilder builder = new StringBuilder();
        for (Path file : classFiles) {
            String relativePath = file.toString().replaceAll(tempDir.toString() + "/", "");
            relativePath = relativePath.replaceAll(SRC_MAIN_JAVA, "");
            String removedExtension = relativePath.replaceAll(JAVA_EXTENSION, "");
            String javaClass = removedExtension.replaceAll("/", "\\.");
            builder.append(javaClass).append(" ");
        }
        return builder.toString();
    }

    private String getTestClass(Path tempDir, List<Path> classFiles) {
        StringBuilder builder = new StringBuilder();
        for (Path file : classFiles) {
            String relativePath = file.toString().replaceAll(tempDir.toString() + "/", "");
            relativePath = relativePath.replaceAll(SRC_TEST_JAVA, "");
            String removedExtension = relativePath.replaceAll(JAVA_EXTENSION, "");
            String javaClass = removedExtension.replaceAll("/", "\\.");
            builder.append(javaClass).append(" ");
        }
        return builder.toString();
    }
}