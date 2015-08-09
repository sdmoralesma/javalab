package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import com.smorales.javalab.middleware.workspaceprocessor.boundary.rest.RunnableNode;
import com.smorales.javalab.middleware.workspaceprocessor.control.Executor;
import com.smorales.javalab.middleware.workspaceprocessor.control.FileHandler;
import com.smorales.javalab.middleware.workspaceprocessor.entity.Library;
import com.smorales.javalab.middleware.workspaceprocessor.entity.TreeData;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class BuildTool {

    protected abstract String buildCompileCommand(Path tempDir, List<Path> files, List<Library> libraries);

    protected abstract String buildRunCommand(Path tempDir, List<Path> files, List<Path> mainClass, List<Library> libraries);

    protected abstract String buildTestCommand(Path tempDir, List<Path> files, List<Path> testClass, List<Library> libraries);

    // implements template method pattern
    public String runCode(BuildToolData data) {
        List<TreeData> treedata = data.getTreedata();
        List<Path> mainclass = data.getMainclass();
        RunnableNode runnableNode = data.getRunnableNode();
        List<Path> testclass = data.getTestclass();
        List<Library> libraries = data.getLibraries();

        Path tempDir = FileHandler.createTempDir();
        try {
            final List<Path> filesCollector = new ArrayList<>();
            FileHandler.createFileTree(tempDir, treedata, filesCollector);
            compileFiles(tempDir, filesCollector, libraries);
            getRunnableClass(tempDir, treedata, runnableNode, mainclass, testclass);
            return runProject(tempDir, filesCollector, mainclass, libraries);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            FileHandler.removeDir(tempDir);
        }
    }

    // implements template method pattern
    public String testCode(BuildToolData data) {
        List<TreeData> treedata = data.getTreedata();
        List<Path> mainclass = data.getMainclass();
        RunnableNode runnableNode = data.getRunnableNode();
        List<Path> testclass = data.getTestclass();
        List<Library> libraries = data.getLibraries();

        Path tempDir = FileHandler.createTempDir();
        try {
            final List<Path> filesCollector = new ArrayList<>();
            FileHandler.createFileTree(tempDir, treedata, filesCollector);
            compileFiles(tempDir, filesCollector, libraries);
            getRunnableClass(tempDir, treedata, runnableNode, mainclass, testclass);
            return testProject(tempDir, filesCollector, testclass, libraries);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            FileHandler.removeDir(tempDir);
        }
    }

    private void getRunnableClass(Path parentPath, List<TreeData> treeDataList, RunnableNode runnableNode, List<Path> mainclass, List<Path> testclass) {

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
                    getRunnableClass(parentPathForChildren, children, runnableNode, mainclass, testclass);
                }
            }
        }
    }

    private String compileFiles(Path tempDir, List<Path> files, List<Library> libraries) {
        String command = buildCompileCommand(tempDir, files, libraries);
        System.out.println("Compiling with cmd: " + command);
        return Executor.execCommand(command);
    }

    private String runProject(Path tempDir, List<Path> files, List<Path> mainClass, List<Library> libraries) {
        String command = buildRunCommand(tempDir, files, mainClass, libraries);
        System.out.println("Running with cmd: " + command);
        return Executor.execCommand(command);
    }

    private String testProject(Path tempDir, List<Path> files, List<Path> testClass, List<Library> libraries) {
        String command = buildTestCommand(tempDir, files, testClass, libraries);
        System.out.println("Testing with cmd: " + command);
        return Executor.execCommand(command);
    }

}