package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.control.Executor;
import com.smorales.javalab.workspaceprocessor.control.FileHandler;
import com.smorales.javalab.workspaceprocessor.entity.Library;
import com.smorales.javalab.workspaceprocessor.entity.TreeData;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class BuildTool {

    @Inject
    Executor executor;

    @Inject
    FileHandler fileHandler;

    @Inject
    Logger tracer;

    protected abstract String buildCompileCommand(Path tempDir, List<Path> files, List<Library> libraries);

    protected abstract String buildRunCommand(Path tempDir, List<Path> mainClass, List<Library> libraries);

    protected abstract String buildTestCommand(Path tempDir, List<Path> testClass, List<Library> libraries);

    // implements template method pattern
    public String runCode(BuildToolData data) {
        Path tempDir = null;
        try {
            tempDir = fileHandler.createTempDir();
            HashSet<FlattenNode> projectFiles = new LinkedHashSet<>();
            flatDirectoryTree(projectFiles, tempDir, data.getTreedata());
            fileHandler.createFileTree(projectFiles);
            compileFiles(tempDir, getJavaFiles(projectFiles), data.getLibraries());
            getRunnableClass(projectFiles, data);
            return runProject(tempDir, data.getMainclass(), data.getLibraries());
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
                data.getTestclass().add(node.getPath());
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

    private List<Path> getJavaFiles(Set<FlattenNode> files) {
        return files.stream()
                .map(FlattenNode::getPath)
                .filter(p -> p.getFileName().toString().endsWith(".java"))
                .collect(Collectors.toList());
    }

    // implements template method pattern
    public String testCode(BuildToolData data) {
        Path tempDir = fileHandler.createTempDir();
        try {
            Set<FlattenNode> projectFiles = new LinkedHashSet<>();
            flatDirectoryTree(projectFiles, tempDir, data.getTreedata());
            fileHandler.createFileTree(projectFiles);
            compileFiles(tempDir, getJavaFiles(projectFiles), data.getLibraries());
            getRunnableClass(projectFiles, data);
            return testProject(tempDir, data.getTestclass(), data.getLibraries());
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileHandler.removeDir(tempDir);
        }
    }


    private void compileFiles(Path tempDir, List<Path> files, List<Library> libraries) {
        String cmd = buildCompileCommand(tempDir, files, libraries);
        tracer.info(() -> "Compiling with cmd: " + cmd);
        executor.execCommand(cmd);
    }

    private String runProject(Path tempDir, List<Path> mainClass, List<Library> libraries) {
        String cmd = buildRunCommand(tempDir, mainClass, libraries);
        tracer.info(() -> "Running with cmd: " + cmd);
        return executor.execCommand(cmd);
    }

    private String testProject(Path tempDir, List<Path> testClass, List<Library> libraries) {
        String cmd = buildTestCommand(tempDir, testClass, libraries);
        tracer.info(() -> "Testing with cmd: " + cmd);
        return executor.execCommand(cmd);
    }

}