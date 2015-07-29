package com.smorales.javalab.middleware.buildtool.boundary;

import com.smorales.javalab.middleware.buildtool.control.Executor;
import com.smorales.javalab.middleware.buildtool.entity.Library;
import com.smorales.javalab.middleware.buildtool.entity.TreeData;
import com.smorales.javalab.middleware.buildtool.rest.RunnableNode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class BuildTool {

    public enum Type {
        JAVAC
    }

    protected final List<TreeData> treedata;
    protected final List<Library> libraries;
    protected final RunnableNode runnableNode;

    private List<Path> mainclass;
    private List<Path> testclass;

    public BuildTool(List<TreeData> treedata, List<Library> libraries, RunnableNode runnableNode) {
        this.treedata = treedata;
        this.libraries = libraries;
        this.runnableNode = runnableNode;
        this.mainclass = new ArrayList<>();
        this.testclass = new ArrayList<>();
    }

    public static BuildTool get(Type type, List<TreeData> treedata, List<Library> libraries, RunnableNode runnableNode) {
        switch (type) {
            case JAVAC:
                return new JavaC(treedata, libraries, runnableNode);
            default:
                throw new IllegalArgumentException("Type not supported: " + type);
        }
    }

    // implements template method pattern
    public String runCode() {
        Path tempDir = FileHandler.createTempDir();
        try {
            final List<Path> filesCollector = new ArrayList<>();
            FileHandler.createFileTree(tempDir, treedata, filesCollector);
            compileFiles(tempDir, filesCollector);
            getRunnableClass(tempDir, treedata);
            return runProject(tempDir, filesCollector, mainclass);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            FileHandler.removeDir(tempDir);
        }
    }

    // implements template method pattern
    public String testCode() {
        Path tempDir = FileHandler.createTempDir();
        try {
            final List<Path> filesCollector = new ArrayList<>();
            FileHandler.createFileTree(tempDir, treedata, filesCollector);
            compileFiles(tempDir, filesCollector);
            getRunnableClass(tempDir, treedata);
            return testProject(tempDir, filesCollector, testclass);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            FileHandler.removeDir(tempDir);
        }
    }

    private void getRunnableClass(Path parentPath, List<TreeData> treeDataList) {

        for (TreeData node : treeDataList) {
            if (node.getType().equals("file")) {

                if (runnableNode.getId().equals(node.getId())) {
                    Path path = Paths.get(parentPath.toString() + "/" + node.getName());
                    System.out.println("Found Runnable Class: " + path.toAbsolutePath());
                    mainclass.add(path);
                    testclass.add(path);
                }

            } else if (node.getType().equals("folder")) {

                String packagePathString = node.getName().replaceAll("\\.", "\\/");
                Path path = Paths.get(parentPath.toString(), packagePathString);
                List<TreeData> children = node.getChildren();
                if (!children.isEmpty()) {
                    Path parentPathForChildren = Paths.get(path.toString());
                    getRunnableClass(parentPathForChildren, children);
                }
            }
        }
    }

    protected abstract String buildCompileCommand(Path tempDir, List<Path> files);

    protected abstract String buildRunCommand(Path tempDir, List<Path> files, List<Path> mainClass);

    protected abstract String buildTestCommand(Path tempDir, List<Path> files, List<Path> testClass);

    private String compileFiles(Path tempDir, List<Path> files) {
        String command = buildCompileCommand(tempDir, files);
        System.out.println("Compiling with cmd: " + command);
        return Executor.execCommand(command);
    }

    private String runProject(Path tempDir, List<Path> files, List<Path> mainClass) {
        String command = buildRunCommand(tempDir, files, mainClass);
        System.out.println("Running with cmd: " + command);
        return Executor.execCommand(command);
    }

    private String testProject(Path tempDir, List<Path> files, List<Path> testClass) {
        String command = buildTestCommand(tempDir, files, testClass);
        System.out.println("Testing with cmd: " + command);
        return Executor.execCommand(command);
    }

}