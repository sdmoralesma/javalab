package com.smorales.javalab.middleware.buildtool.control;

import com.smorales.javalab.middleware.buildtool.boundary.LabPaths;
import com.smorales.javalab.middleware.buildtool.boundary.NotRunnableCodeException;
import com.smorales.javalab.middleware.buildtool.entity.TreeData;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.UUID;

public class FileHandler {

    public static Path createTempDir() {
        try {
            Path tempDir = Paths.get(LabPaths.HOME.getPathAsString(), FileHandler.generateUUID(), "/");
            System.out.println("Created temp dir: " + tempDir);
            return Files.createDirectory(tempDir);
        } catch (IOException e) {
            throw new NotRunnableCodeException(e);
        }
    }

    private static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static void createFileTree(Path parentPath, List<TreeData> treedata, List<Path> sourceFilesCollector) {

        for (TreeData node : treedata) {
            if (node.getType().equals("file")) {
                Path file = createFile(parentPath, node);
                writeCodeToFile(file, node);
                sourceFilesCollector.add(file.toAbsolutePath());
            } else if (node.getType().equals("folder")) {

                try {
                    String packagePathString = node.getName().replaceAll("\\.", "\\/");
                    Path path = Paths.get(parentPath.toString(), packagePathString);
                    Path directories = Files.createDirectories(path);
                    System.out.println("Created folder: " + packagePathString);

                    List<TreeData> children = node.getChildren();
                    if (!children.isEmpty()) {
                        Path parentPathForChildren = Paths.get(directories.toString());
                        createFileTree(parentPathForChildren, children, sourceFilesCollector);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new NotRunnableCodeException("Error creating package directories");
                }
            }
        }
    }

    private static Path createFile(Path parentPath, TreeData node) {

        try {
            Path path = Paths.get(parentPath.toString() + "/" + node.getName());
            Path file = Files.createFile(path);
            System.out.println("File created: " + file.toAbsolutePath().toString());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            throw new NotRunnableCodeException("Error creating file:" + node.getName());
        }
    }

    private static void writeCodeToFile(Path classFile, TreeData node) {
        try {
            Files.write(classFile, node.getCode().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException ioe) {
            throw new NotRunnableCodeException(ioe);
        }
    }

    public static void removeDir(Path file) {
        if (file == null) {
            return;
        }

        try {
            Files.walkFileTree(file, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.deleteIfExists(file);
                    System.out.println("Deleted file: " + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

                    if (exc == null) {
                        Files.deleteIfExists(dir);
                        System.out.println("Deleted file: " + dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        throw exc;
                    }
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new NotRunnableCodeException("Error deleting file: " + file.toString());
        }
    }
}
