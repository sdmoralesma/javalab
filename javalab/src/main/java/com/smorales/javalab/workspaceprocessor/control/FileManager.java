package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;
import com.smorales.javalab.workspaceprocessor.boundary.SimpleNode;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.TreeNode;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.smorales.javalab.workspaceprocessor.boundary.SimpleNode.Type.FILE;
import static com.smorales.javalab.workspaceprocessor.boundary.SimpleNode.Type.FOLDER;

public class FileManager {

    @Inject
    Logger tracer;

    public Path createTempDir() {
        try {
            Path tempDir = Files.createDirectory(Paths.get(LabPaths.HOME.asString() + generateUUID() + File.separator));
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

    public Path calculatePathForNode(SimpleNode node, List<SimpleNode> simpleNodes) {
        List<SimpleNode> parents = findParentsForNode(node, simpleNodes);
        String path = "";
        for (SimpleNode p : parents) {
            String label = p.getLabel();
            label = label.replaceAll("\\.", "\\/");
            if (p.getType() == FOLDER) {
                label = label + "/";
            }
            path += label;
        }

        String label = (node.getType() == FOLDER) ? node.getLabel().replaceAll("\\.", "\\/") : node.getLabel();
        return Paths.get(path, label);
    }

    private List<SimpleNode> findParentsForNode(SimpleNode node, List<SimpleNode> simpleNodes) {
        if (node.getParentId() == null) {
            return new ArrayList<>();
        }

        List<SimpleNode> parentList = new ArrayList<>();
        String parentId = node.getParentId();
        while (true) {
            SimpleNode parentNode = findSimpleNode(new SimpleNode(parentId), simpleNodes);
            parentList.add(parentNode);

            if (parentNode.getParentId() == null) {
                Collections.reverse(parentList);
                return parentList;
            } else {
                parentId = parentNode.getParentId();
            }
        }
    }

    public SimpleNode findSimpleNode(SimpleNode toFind, List<SimpleNode> simpleNodes) {
        return simpleNodes.stream()
                .filter(s -> s.getId().equals(toFind.getId()))
                .findFirst().orElseThrow(() -> new NotRunnableCodeException("Can not find node:" + toFind.getId()));
    }

    public void createFiles(Path tempDir, List<SimpleNode> simpleNodes) {
        for (SimpleNode node : simpleNodes) {
            try {
                Path pathForNode = this.calculatePathForNode(node, simpleNodes);
                Path path = Paths.get(tempDir.toString(), pathForNode.toString());
                if (node.getType() == FILE) {
                    Files.createDirectories(path.getParent());
                    Files.createFile(path);
                    Files.write(path, node.getData().getBytes(), StandardOpenOption.CREATE);
                    tracer.info("Created file: " + path);
                } else if (node.getType() == FOLDER) {
                    Files.createDirectories(path);
                    tracer.info("Created folder(s): " + path);
                }
            } catch (IOException e) {
                tracer.log(Level.SEVERE, e, e::getMessage);
                throw new NotRunnableCodeException("Error creating file: " + node);
            }
        }
    }

    public void transformToSimpleList(List<TreeNode> treeNodes, List<SimpleNode> simple) {
        for (TreeNode node : treeNodes) {

            String id = node.getId();
            SimpleNode.Type type = getTypeOfNode(node);
            String label = node.getLabel();
            String data = node.getData();
            String parentId = node.getParentId();

            SimpleNode simpleNode = new SimpleNode(id, type, label, data, parentId);
            simple.add(simpleNode);

            if (node.getChildren() != null && node.getChildren() != null) {
                transformToSimpleList(node.getChildren(), simple);
            }
        }
    }

    private SimpleNode.Type getTypeOfNode(TreeNode node) {
        return (node.getIcon() != null && node.getIcon().equals("fa-file-text-o")) ? FILE : FOLDER;
    }


    public void removeDirectoryRecursively(Path file) {
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

    public void printAllFilesInFolder(Path tempDir) {
        try {
            Files.walk(tempDir).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    System.out.println(filePath);
                }
            });
        } catch (IOException e) {
            throw new NotRunnableCodeException("Exception printing files in folder", e);
        }
    }


    public String removeExtension(String path) {
        return path.substring(0, path.lastIndexOf('.'));
    }

}
