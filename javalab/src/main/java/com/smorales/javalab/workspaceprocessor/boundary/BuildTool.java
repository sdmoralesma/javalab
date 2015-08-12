package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.RunnableNode;
import com.smorales.javalab.workspaceprocessor.control.Executor;
import com.smorales.javalab.workspaceprocessor.control.FileHandler;
import com.smorales.javalab.workspaceprocessor.entity.Library;
import com.smorales.javalab.workspaceprocessor.entity.TreeData;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
        Path tempDir = fileHandler.createTempDir();
        try {
            createFileTreeAndCompileFilesAndGetRunnableClasses(data, tempDir);
            return runProject(tempDir, data.getMainclass(), data.getLibraries());
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileHandler.removeDir(tempDir);
        }
    }

    // implements template method pattern
    public String testCode(BuildToolData data) {
        Path tempDir = fileHandler.createTempDir();
        try {
            createFileTreeAndCompileFilesAndGetRunnableClasses(data, tempDir);
            return testProject(tempDir, data.getTestclass(), data.getLibraries());
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileHandler.removeDir(tempDir);
        }
    }

    private void createFileTreeAndCompileFilesAndGetRunnableClasses(BuildToolData data, Path tempDir) { // TODO> make me simpler
        final List<Path> filesCollector = new ArrayList<>();
        fileHandler.createFileTree(tempDir, data.getTreedata(), filesCollector);
        compileFiles(tempDir, filesCollector, data.getLibraries());
        getRunnableClass(tempDir, data.getTreedata(), data.getRunnableNode(), data.getMainclass(), data.getTestclass());
    }

    private void getRunnableClass(Path parentPath, List<TreeData> treeDataList, RunnableNode runnableNode, List<Path> mainclass, List<Path> testclass) {
        for (TreeData node : treeDataList) {
            if ("file".equals(node.getType())) {
                if (runnableNode.getId().equals(node.getId())) {
                    Path path = Paths.get(parentPath.toString() + "/" + node.getName());
                    tracer.info(() -> "Found Runnable Class: " + path.toAbsolutePath());
                    mainclass.add(path);
                    testclass.add(path);
                }
            } else if ("folder".equals(node.getType())) {
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
        String cmd = buildCompileCommand(tempDir, files, libraries);
        tracer.info(() -> "Compiling with cmd: " + cmd);
        return executor.execCommand(cmd);
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