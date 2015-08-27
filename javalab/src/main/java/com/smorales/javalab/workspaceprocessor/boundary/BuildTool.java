package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.control.Executor;
import com.smorales.javalab.workspaceprocessor.control.FileHandler;
import com.smorales.javalab.workspaceprocessor.entity.TreeData;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
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

    protected abstract String buildCompileCommand(Path tempDir);

    protected abstract String buildRunCommand(Path tempDir);

    protected abstract String buildTestCommand(Path tempDir);

    protected abstract void createAuxFiles(Path tempDir);

    // implements template method pattern
    public String runCode(BuildToolData data) {
        Path tempDir = null;
        try {
            tempDir = fileHandler.createTempDir();
            HashSet<FlattenNode> projectFiles = new LinkedHashSet<>();
            flatDirectoryTree(projectFiles, tempDir, data.getTreedata());
            fileHandler.createFileTree(projectFiles);
            createAuxFiles(tempDir);
            compileFiles(tempDir);
            getRunnableClass(projectFiles, data);
            return runProject(tempDir);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileHandler.removeDir(tempDir);
        }
    }


    private void getRunnableClass(Set<FlattenNode> flattenNodes, BuildToolData data) {
        for (FlattenNode node : flattenNodes) {
            if (data.getRunnableNode().getId().equals(node.getId())) {
                data.getMainclass().add(node.getPath());
            }
        }
    }

    private void flatDirectoryTree(Set<FlattenNode> flattenNodeSet, Path parentPath, List<TreeData> treeData) {
        for (TreeData node : treeData) {
            if ("file".equals(node.getType())) {
                Path path = Paths.get(parentPath.toString() + "/" + node.getName());
                flattenNodeSet.add(new FlattenNode(node.getId(), path, node.getType(), node.getCode()));
            } else if ("folder".equals(node.getType())) {
                String packagePathAsString = node.getName().replaceAll("\\.", "\\/");
                Path path = Paths.get(parentPath.toString(), packagePathAsString);
                if (!node.getChildren().isEmpty()) {
                    flatDirectoryTree(flattenNodeSet, path, node.getChildren());
                }
            }
        }
    }

    // implements template method pattern
    public String testCode(BuildToolData data) {
        Path tempDir = fileHandler.createTempDir();
        try {
            Set<FlattenNode> projectFiles = new LinkedHashSet<>();
            flatDirectoryTree(projectFiles, tempDir, data.getTreedata());
            fileHandler.createFileTree(projectFiles);
            createAuxFiles(tempDir);
            compileFiles(tempDir);
            getRunnableClass(projectFiles, data);
            return testProject(tempDir);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileHandler.removeDir(tempDir);
        }
    }


    private void compileFiles(Path tempDir) {
        String cmd = buildCompileCommand(tempDir);
        tracer.info(() -> "Compiling with cmd: " + cmd);
        executor.execCommand(cmd);
    }

    private String runProject(Path tempDir) {
        String cmd = buildRunCommand(tempDir);
        tracer.info(() -> "Running with cmd: " + cmd);
        return executor.execCommand(cmd);
    }

    private String testProject(Path tempDir) {
        String cmd = buildTestCommand(tempDir);
        tracer.info(() -> "Testing with cmd: " + cmd);
        return executor.execCommand(cmd);
    }

}