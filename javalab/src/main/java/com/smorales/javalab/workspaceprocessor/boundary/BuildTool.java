package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.request.Config;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.TreeNode;
import com.smorales.javalab.workspaceprocessor.control.Executor;
import com.smorales.javalab.workspaceprocessor.control.FileManager;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class BuildTool {

    @Inject
    Executor executor;

    @Inject
    FileManager fileManager;

    @Inject
    Logger tracer;

    protected abstract String buildRunCommand(Path tempDir);

    protected abstract String buildTestCommand(Path tempDir);

    protected abstract void createAuxFiles(Path tempDir, Config config, List<SimpleNode> simpleNodes);

    // implements template method pattern
    public String runCode(List<TreeNode> treeNodes, Config config) {
        Path tempDir = null;
        try {
            tempDir = fileManager.createTempDir();
            List<SimpleNode> simpleNodes = new ArrayList<>();
            fileManager.transformToSimpleList(treeNodes, simpleNodes);
            fileManager.createFiles(tempDir, simpleNodes);
            createAuxFiles(tempDir, config, simpleNodes);
            return runClass(tempDir);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileManager.removeDirectoryRecursively(tempDir);
        }
    }

    // implements template method pattern
    public String testCode(List<TreeNode> treeNodes, Config config) {
        Path tempDir = fileManager.createTempDir();
        try {
            List<SimpleNode> simpleNodes = new ArrayList<>();
            fileManager.transformToSimpleList(treeNodes, simpleNodes);
            fileManager.createFiles(tempDir, simpleNodes);
            createAuxFiles(tempDir, config, simpleNodes);
            return testProject(tempDir);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileManager.removeDirectoryRecursively(tempDir);
        }
    }

    private String runClass(Path tempDir) {
        String cmd = buildRunCommand(tempDir);
        tracer.info(() -> "Running with cmd: " + cmd);
        String result = executor.execCommand(cmd);
        tracer.info(() -> "Result: " + result);
        return result;
    }

    private String testProject(Path tempDir) {
        String cmd = buildTestCommand(tempDir);
        tracer.info(() -> "Testing with cmd: " + cmd);
        return executor.execCommand(cmd, Executor.STD.OUT);
    }

}