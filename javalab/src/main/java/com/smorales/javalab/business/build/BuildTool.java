package com.smorales.javalab.business.build;

import com.smorales.javalab.business.NotRunnableCodeException;
import com.smorales.javalab.business.processor.boundary.SimpleNode;
import com.smorales.javalab.business.processor.boundary.rest.request.Config;
import com.smorales.javalab.business.processor.boundary.rest.request.TreeNode;
import com.smorales.javalab.business.files.FileManager;
import com.smorales.javalab.business.files.Zipper;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class BuildTool {

    @Inject
    Executor executor;

    @Inject
    FileManager fileManager;

    @Inject
    Logger tracer;

    @Inject
    Zipper zipper;

    protected abstract String buildRunCommand(Path tempDir);

    protected abstract String buildTestCommand(Path tempDir);

    protected abstract void createAuxFiles(Path tempDir, Config config, List<SimpleNode> simpleNodes);

    protected abstract String cleanResultMessage(String toClean);

    // implements template method pattern
    public String runCode(List<TreeNode> treeNodes, Config config) {
        Path tempDir = fileManager.createTempDir();
        try {
            List<SimpleNode> simpleNodes = new ArrayList<>();
            fileManager.transformToSimpleList(treeNodes, simpleNodes);
            fileManager.createFiles(tempDir, simpleNodes);
            createAuxFiles(tempDir, config, simpleNodes);
            return runClass(tempDir);
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileManager.deleteFolderRecursively(tempDir);
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
            fileManager.deleteFolderRecursively(tempDir);
        }
    }

    public byte[] createZip(List<TreeNode> filesTree, Config config) {
        Path tempDir = fileManager.createTempDir();
        try {
            List<SimpleNode> simpleNodes = new ArrayList<>();
            fileManager.transformToSimpleList(filesTree, simpleNodes);
            fileManager.createFiles(tempDir, simpleNodes);
            createAuxFiles(tempDir, config, simpleNodes);
            return zipper.createZipFromFolder(tempDir);
        } catch (NotRunnableCodeException exc) {
            throw new NotRunnableCodeException("can not read zip file");
        } finally {
            fileManager.deleteFolderRecursively(tempDir);
        }
    }

    private String runClass(Path tempDir) {
        String cmd = buildRunCommand(tempDir);
        tracer.info(() -> "Running with cmd: " + cmd);
        String result = executor.execCommand(cmd, tempDir.toFile());
        tracer.info(() -> "Result: " + result);
        return cleanResultMessage(result);
    }

    private String testProject(Path tempDir) {
        String cmd = buildTestCommand(tempDir);
        tracer.info(() -> "Testing with cmd: " + cmd);
        String result = executor.execCommand(cmd, tempDir.toFile());
        tracer.info(() -> "Result: " + result);
        return cleanResultMessage(result);
    }

    protected void findInvalidDependencies(Set<String> deps) {
        String invalidDeps = deps.stream()
                .filter(s -> !"#".equals(String.valueOf(s.charAt(0))))
                .filter(dep -> !validateDependency(dep))
                .collect(Collectors.joining(", "));
        if (!invalidDeps.isEmpty()) {
            throw new NotRunnableCodeException("Invalid Dependendencies : \n" + invalidDeps);
        }
    }

    /**
     * Validates line structure, for example: {@code testCompile 'org.hibernate:hibernate-core:3.6.7.Final'}
     */
    protected boolean validateDependency(String line) {
        return line.matches("(testCompile|compile)\\s('|\")[-_.\\w]*:[\\w_.-]*:[_\\[0-9_._+_,_\\)\\w-]*('|\")");
    }
}