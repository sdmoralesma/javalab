package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.FlattenNode;
import com.smorales.javalab.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.TreeNode;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Adds all nodes to {@code flattenSet} to remove the hierarchy among the nodes, then the relation "parent -> children"
     * is flatten to a set of nodes.
     */
    public void flattenDirectoryTree(Set<FlattenNode> flattenSet, Path parentPath, List<TreeNode> treeNode) {
        for (TreeNode node : treeNode) {
            if ("file".equals(node.getIcon())) {
                Path path = Paths.get(parentPath.toString() + File.separator + node.getLabel());
                flattenSet.add(new FlattenNode(node.getId(), path, node.getIcon(), node.getData()));
            } else if ("folder".equals(node.getIcon())) {
                String packagePathAsString = node.getLabel().replaceAll("\\.", "\\/");
                Path path = Paths.get(parentPath.toString(), packagePathAsString);
                if (!node.getChildren().isEmpty()) {
                    flattenDirectoryTree(flattenSet, path, node.getChildren());
                }
            }
        }
    }

    public Path calculatePathForNode(TreeNode node, List<TreeNode> treeNode) {// TODO: use this method
        List<TreeNode> parents = findParentsForNode(node, treeNode);
        String path = "";
        for (TreeNode p : parents) {
            path += p.getLabel();
        }

        return Paths.get(path);
    }

    public List<TreeNode> findParentsForNode(TreeNode node, List<TreeNode> treeNode) {
        List<TreeNode> parentList = new ArrayList<>();

        String parentId = node.getParentId();
        if (parentId == null) {
            return parentList;
        }

        while (true) {
            TreeNode parentNode = findNode(new TreeNode(parentId), treeNode);
            parentId = parentNode.getParentId();
            parentList.add(parentNode);

            if (parentId == null) {
                return parentList;
            }
        }
    }

    public TreeNode findNode(TreeNode toFind, List<TreeNode> nodeList) {
        verifyId(toFind);

        for (TreeNode node : nodeList) {
            verifyId(node);
            if (node.getId().equals(toFind.getId())) {
                return node;
            }

            if (node.getChildren() != null && node.getChildren().size() > 0) {
                return findNode(toFind, node.getChildren());
            }
        }

        throw new NotRunnableCodeException("node not found " + toFind);
    }

    private void verifyId(TreeNode toFind) {
        Objects.requireNonNull(toFind);
        if (toFind.getId() == null || toFind.getId().trim().equals("")) {
            throw new NotRunnableCodeException("can not find node, Id not defined");
        }
    }

    public void createFileTree(Set<FlattenNode> flattenDirs) {
        for (FlattenNode node : flattenDirs) {
            try {
                Files.createDirectories(node.getPath().getParent());
                Path filePath = Files.createFile(node.getPath());
                Files.write(node.getPath(), node.getCode().getBytes(), StandardOpenOption.CREATE);
                tracer.info("Created file: " + filePath);
            } catch (IOException e) {
                tracer.log(Level.SEVERE, e, e::getMessage);
                throw new NotRunnableCodeException("Error creating file: " + node.getPath());
            }
        }
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
}
