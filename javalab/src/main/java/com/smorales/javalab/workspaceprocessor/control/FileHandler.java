package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.FlattenNode;
import com.smorales.javalab.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {

    @Inject
    Logger tracer;

    public Path createTempDir() {
        try {
            Path tempDir = Files.createDirectory(Paths.get(LabPaths.HOME.asString() + this.generateUUID() + "/"));
            tracer.info("Created temp dir: " + tempDir);
            return tempDir;
        } catch (IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException(e);
        }
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public void createFileTree(Set<FlattenNode> flattenDirs) {
        for (FlattenNode node : flattenDirs) {
            if ("file".equals(node.getType())) {
                Path filePath = createFile(node.getPath());
                writeCodeToFile(node);
                tracer.info("Created file: " + filePath);
            } else if ("folder".equals(node.getType())) {// TODO: is this REALLY necessary?
                try {
                    Path folderPath = Files.createDirectories(node.getPath());
                    tracer.info("Created folder: " + folderPath);
                } catch (IOException e) {
                    tracer.log(Level.SEVERE, e, e::getMessage);
                    throw new NotRunnableCodeException("Error creating package directories");
                }
            }
        }
    }

    private Path createFile(Path path) {
        try {
            return Files.createFile(path);
        } catch (IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException("Error creating file: " + path);
        }
    }

    private void writeCodeToFile(FlattenNode node) {
        try {
            Files.write(node.getPath(), node.getCode().getBytes(), StandardOpenOption.CREATE);
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
