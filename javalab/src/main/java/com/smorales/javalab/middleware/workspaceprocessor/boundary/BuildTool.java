package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import com.smorales.javalab.middleware.workspaceprocessor.boundary.rest.RunnableNode;
import com.smorales.javalab.middleware.workspaceprocessor.control.Executor;
import com.smorales.javalab.middleware.workspaceprocessor.control.FileHandler;
import com.smorales.javalab.middleware.workspaceprocessor.entity.Library;
import com.smorales.javalab.middleware.workspaceprocessor.entity.TreeData;

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

    protected abstract String buildRunCommand(Path tempDir, List<Path> files, List<Path> mainClass, List<Library> libraries);

    protected abstract String buildTestCommand(Path tempDir, List<Path> files, List<Path> testClass, List<Library> libraries);

    // implements template method pattern
    public String runCode(BuildToolData data) {
        Path tempDir = fileHandler.createTempDir();
        try {
            final List<Path> filesCollector = getFilesCollector(data, tempDir);
            return runProject(tempDir, filesCollector, data.getMainclass(), data.getLibraries());
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
            final List<Path> filesCollector = getFilesCollector(data, tempDir);
            return testProject(tempDir, filesCollector, data.getTestclass(), data.getLibraries());
        } catch (NotRunnableCodeException exc) {
            return exc.getMessage();
        } finally {
            fileHandler.removeDir(tempDir);
        }
    }

    private List<Path> getFilesCollector(BuildToolData data, Path tempDir) {
        final List<Path> filesCollector = new ArrayList<>();
        fileHandler.createFileTree(tempDir, data.getTreedata(), filesCollector);
        compileFiles(tempDir, filesCollector, data.getLibraries());
        getRunnableClass(tempDir, data.getTreedata(), data.getRunnableNode(), data.getMainclass(), data.getTestclass());
        return filesCollector;
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
        String command = buildCompileCommand(tempDir, files, libraries);
        tracer.info(() -> "Compiling with cmd: " + command);
        return executor.execCommand(command);
    }

    private String runProject(Path tempDir, List<Path> files, List<Path> mainClass, List<Library> libraries) {
        String command = buildRunCommand(tempDir, files, mainClass, libraries);
        tracer.info(() -> "Running with cmd: " + command);
        return executor.execCommand(command);
    }

    private String testProject(Path tempDir, List<Path> files, List<Path> testClass, List<Library> libraries) {
        String command = buildTestCommand(tempDir, files, testClass, libraries);
        tracer.info(() -> "Testing with cmd: " + command);
        return executor.execCommand(command);
    }

}