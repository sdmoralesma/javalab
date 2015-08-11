package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import com.smorales.javalab.middleware.workspaceprocessor.entity.Library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

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
        cmd = cmd.replace("{javaFiles}", javafilesAsString(files));
        cmd = cmd.replace("{buildPath}", getBuildPath(tempDir));
        cmd = cmd.replace("{libraries}", dependenciesAsString(libraries));
        return cmd;
    }

    @Override
    protected String buildRunCommand(Path tempDir, List<Path> mainClass, List<Library> libraries) {
        String cmd = "{javaExec} -cp {buildPath}:{libraries} {mainClass}";
        cmd = cmd.replace("{javaExec}", JAVA_EXEC);
        cmd = cmd.replace("{buildPath}", getBuildPath(tempDir));
        cmd = cmd.replace("{libraries}", dependenciesAsString(libraries));
        cmd = cmd.replace("{mainClass}", getMainClass(tempDir, mainClass));
        return cmd;
    }

    @Override
    protected String buildTestCommand(Path tempDir, List<Path> testClass, List<Library> libraries) {
        String cmd = "{javaExec} -cp {buildPath}:{libraries} org.junit.runner.JUnitCore {testClass}";
        cmd = cmd.replace("{javaExec}", JAVA_EXEC);
        cmd = cmd.replace("{buildPath}", getBuildPath(tempDir));
        cmd = cmd.replace("{libraries}", dependenciesAsString(libraries));
        cmd = cmd.replace("{testClass}", getTestClass(tempDir, testClass));
        return cmd;
    }

    private String getBuildPath(Path tempDir) {
        try {
            return Files.createDirectories(Paths.get(tempDir.toString() + "/build")).toString();
        } catch (IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException("Can not create {tempDir}/build dir");
        }
    }

    private String javafilesAsString(List<Path> files) {
        return files.stream()
                .map(file -> file.toAbsolutePath().toString())
                .filter(absPath -> absPath.contains(JAVA_EXTENSION))
                .collect(Collectors.joining(" "));
    }

    private String dependenciesAsString(List<Library> libraries) {
        String collect = libraries.stream()
                .map(Library::getJar)
                .map(lib -> LabPaths.REPOSITORY_DIR.asString() + lib)
                .collect(Collectors.joining(":"));
        return "".equals(collect) ? LabPaths.REPOSITORY_DIR.asString() : collect;
    }

    private String getMainClass(Path tempDir, List<Path> classFiles) {
        return classFiles.stream()
                .map(Path::toString)
                .map(path -> path.replaceAll(tempDir.toString() + "/", ""))
                .map(path -> path.replaceAll(SRC_MAIN_JAVA, ""))
                .map(path -> path.replaceAll(JAVA_EXTENSION, ""))
                .map(path -> path.replaceAll("/", "\\."))
                .collect(Collectors.joining(" "));
    }

    private String getTestClass(Path tempDir, List<Path> classFiles) {
        return classFiles.stream()
                .map(Path::toString)
                .map(path -> path.replaceAll(tempDir.toString() + "/", ""))
                .map(path -> path.replaceAll(SRC_TEST_JAVA, ""))
                .map(path -> path.replaceAll(JAVA_EXTENSION, ""))
                .map(path -> path.replaceAll("/", "\\."))
                .collect(Collectors.joining(" "));
    }
}