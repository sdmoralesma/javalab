package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.request.InitConfig;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.RunnableNode;
import com.smorales.javalab.workspaceprocessor.control.Executor;
import com.smorales.javalab.workspaceprocessor.control.FileHandler;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.TreeData;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public abstract class BuildTool {

    @Inject
    Executor executor;

    @Inject
    FileHandler fileHandler;

    @Inject
    Logger tracer;

    protected abstract String buildRunCommand(Path tempDir);

    protected abstract String buildTestCommand(Path tempDir);

    protected abstract void createAuxFiles(Path tempDir, RunnableNode runnableNode, InitConfig initConfig);

    // implements template method pattern
    public String runCode(List<TreeData> treeData, RunnableNode runnableNode, InitConfig initConfig) {
        Path tempDir = null;
        try {
            tempDir = fileHandler.createTempDir();
            Set<FlattenNode> projectFiles = new LinkedHashSet<>();
            flattenDirectoryTree(projectFiles, tempDir, treeData);
            fileHandler.createFileTree(projectFiles);
            createAuxFiles(tempDir, runnableNode, initConfig);
            return runClass(tempDir);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileHandler.removeDirectoryRecursively(tempDir);
        }
    }

    private void flattenDirectoryTree(Set<FlattenNode> flattenNodeSet, Path parentPath, List<TreeData> treeData) {
        for (TreeData node : treeData) {
            if ("file".equals(node.getType())) {
                Path path = Paths.get(parentPath.toString() + "/" + node.getName());
                flattenNodeSet.add(new FlattenNode(node.getId(), path, node.getType(), node.getCode()));
            } else if ("folder".equals(node.getType())) {
                String packagePathAsString = node.getName().replaceAll("\\.", "\\/");
                Path path = Paths.get(parentPath.toString(), packagePathAsString);
                if (!node.getChildren().isEmpty()) {
                    flattenDirectoryTree(flattenNodeSet, path, node.getChildren());
                }
            }
        }
    }

    // implements template method pattern
    public String testCode(List<TreeData> treeData, RunnableNode runnableNode, InitConfig initConfig) {
        Path tempDir = fileHandler.createTempDir();
        try {
            Set<FlattenNode> projectFiles = new LinkedHashSet<>();
            flattenDirectoryTree(projectFiles, tempDir, treeData);
            fileHandler.createFileTree(projectFiles);
            createAuxFiles(tempDir, runnableNode, initConfig);
            return testProject(tempDir);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileHandler.removeDirectoryRecursively(tempDir);
        }
    }

    private String runClass(Path tempDir) {
        String cmd = buildRunCommand(tempDir);
        tracer.info(() -> "Running with cmd: " + cmd);
        return executor.execCommand(cmd);
    }

    private String testProject(Path tempDir) {
        String cmd = buildTestCommand(tempDir);
        tracer.info(() -> "Testing with cmd: " + cmd);
        return executor.execCommand(cmd, Executor.STD.OUT);
    }

}