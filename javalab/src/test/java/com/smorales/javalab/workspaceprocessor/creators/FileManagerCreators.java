package com.smorales.javalab.workspaceprocessor.creators;

import com.smorales.javalab.workspaceprocessor.boundary.SimpleNode;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.TreeNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileManagerCreators {

    public static TreeNode createValidTreeNode(String id) {
        TreeNode node = new TreeNode(id);
        node.setParentId("0");
        node.setIcon("fa-file-text-o");
        return node;
    }

    public static SimpleNode createValidSimpleNode(String id, String label, String parent) {
        return new SimpleNode(id, SimpleNode.Type.FILE, label, "", parent);
    }

    public static List<TreeNode> createNestedHierarchyOfNodes(TreeNode toFind) {
        TreeNode parent = new TreeNode("0");
        parent.setLabel("com.company.project");
        parent.setIcon("fa fa-th-large");
        parent.setParentId("1");
        parent.setChildren(Collections.singletonList(toFind));

        TreeNode grandParent = new TreeNode("1");
        grandParent.setLabel("src/main/java");
        grandParent.setExpandedIcon("fa-folder-open");
        grandParent.setCollapsedIcon("fa-folder");
        grandParent.setChildren(Collections.singletonList(parent));

        return Arrays.asList(new TreeNode("2"), new TreeNode("3"), new TreeNode("4"), grandParent, new TreeNode("5"));
    }


    public static List<SimpleNode> createSimpleListOfNodes(SimpleNode two) {
        SimpleNode zero = new SimpleNode("0", SimpleNode.Type.FOLDER, "com.company.project", "", null);
        SimpleNode one = new SimpleNode("1", SimpleNode.Type.FOLDER, "src/main/java", "", "0");
        return Arrays.asList(zero, one, two);
    }
}
