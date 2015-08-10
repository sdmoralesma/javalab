package com.smorales.javalab.middleware.workspaceprocessor.control;

import com.smorales.javalab.middleware.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.middleware.workspaceprocessor.boundary.NotRunnableCodeException;
import com.smorales.javalab.middleware.workspaceprocessor.entity.TreeData;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {

    @Inject
    Logger tracer;

    public Path createTempDir() {
        try {
            Path tempDir = Files.createDirectory(Paths.get(LabPaths.HOME.asString(), this.generateUUID(), "/"));
            tracer.info("Creating temp dir" + tempDir);
            return tempDir;
        } catch (IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException(e);
        }
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public void createFileTree(Path parentPath, List<TreeData> treedata, List<Path> sourceFilesCollector) {
        for (TreeData node : treedata) {
            if ("file".equals(node.getType())) {
                Path file = createFile(parentPath, node);
                writeCodeToFile(file, node);
                sourceFilesCollector.add(file.toAbsolutePath());
            } else if ("folder".equals(node.getType())) {
                try {
                    String packagePathString = node.getName().replaceAll("\\.", "\\/");
                    Path path = Paths.get(parentPath.toString(), packagePathString);
                    Path directories = Files.createDirectories(path);
                    tracer.info("Created folder: " + packagePathString);

                    List<TreeData> children = node.getChildren();
                    if (!children.isEmpty()) {
                        Path parentPathForChildren = Paths.get(directories.toString());
                        createFileTree(parentPathForChildren, children, sourceFilesCollector);
                    }
                } catch (IOException e) {
                    tracer.log(Level.SEVERE, e, e::getMessage);
                    throw new NotRunnableCodeException("Error creating package directories");
                }
            }
        }
    }

    private Path createFile(Path parentPath, TreeData node) {
        try {
            Path path = Paths.get(parentPath.toString() + "/" + node.getName());
            Path file = Files.createFile(path);
            tracer.info("File created: " + file.toAbsolutePath().toString());
            return file;
        } catch (IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException("Error creating file:" + node.getName());
        }
    }

    private void writeCodeToFile(Path classFile, TreeData node) {
        try {
            Files.write(classFile, node.getCode().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException(e);
        }
    }

    public void removeDir(Path file) {
        Objects.nonNull(file);
        try {
            Files.walkFileTree(file, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.deleteIfExists(file);
                    tracer.info("Deleted file: " + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc != null) {
                        throw exc;
                    }
                    Files.deleteIfExists(dir);
                    tracer.info("Deleted file: " + dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException("Error deleting file: " + file.toString());
        }
    }
}
