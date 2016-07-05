package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.request.Config;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.TreeNode;
import com.smorales.javalab.workspaceprocessor.control.Executor;
import com.smorales.javalab.workspaceprocessor.control.FileManager;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BuildTool.class})
public class BuildToolTest {

    private BuildTool sut;

    @Before
    public void setUp() {
        sut = mock(BuildTool.class, CALLS_REAL_METHODS);
        sut.fileManager = mock(FileManager.class);
        sut.executor = mock(Executor.class);
        sut.tracer = mock(Logger.class);
    }

    @Test
    @Ignore
    public void shouldRunCodeOk() throws Exception {
        mockStatic(Path.class, Paths.class, Files.class);

        Path tempDir = mock(Path.class);
        when(tempDir.toString()).thenReturn("tempDir/");
        when(sut.fileManager.createTempDir()).thenReturn(tempDir);

        List<TreeNode> treeNode = createTreeData();

        Path parentPath = mock(Path.class);
        when(parentPath.toString()).thenReturn(treeNode.get(0).getLabel());
        when(Paths.get(anyString(), anyString())).thenReturn(parentPath);

        Path childPath = mock(Path.class);
        when(childPath.toString()).thenReturn(treeNode.get(0).getChildren().get(0).getLabel());
        when(Paths.get(anyString())).thenReturn(childPath);

        String result = sut.runCode(treeNode, new Config());

        assertThat(result).isEqualTo(null);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(sut.executor).execCommand(argument.capture());
        assertThat(argument.getValue()).isEqualTo(null);
    }

    private List<TreeNode> createTreeData() {
        TreeNode child = new TreeNode();
        child.setId("11");
        child.setIcon("file");
        child.setData("hello world code");
        child.setLabel("HelloWorld.java");
        child.setChildren(new ArrayList<>());

        TreeNode parent = new TreeNode();
        parent.setId("1");
        parent.setIcon("folder");
        parent.setLabel("folder");
        parent.setChildren(Collections.singletonList(child));

        return Collections.singletonList(parent);
    }


    @Test
    @Ignore
    public void shouldTestCodeOk() throws Exception {
        mockStatic(Path.class, Paths.class, Files.class);

        List<TreeNode> treeNode = createTreeData();

        Path tempDir = mock(Path.class);
        when(tempDir.toString()).thenReturn("tempDir/");
        when(sut.fileManager.createTempDir()).thenReturn(tempDir);

        Path parentPath = mock(Path.class);
        when(parentPath.toString()).thenReturn(treeNode.get(0).getLabel());
        when(Paths.get(anyString(), anyString())).thenReturn(parentPath);

        Path childPath = mock(Path.class);
        when(childPath.toString()).thenReturn(treeNode.get(0).getChildren().get(0).getLabel());
        when(Paths.get(anyString())).thenReturn(childPath);

        String result = sut.testCode(treeNode, new Config());

        assertThat(result).isEqualTo(null);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Executor.STD> argStd = ArgumentCaptor.forClass(Executor.STD.class);
        verify(sut.executor).execCommand(argString.capture(), null, argStd.capture());
        assertThat(argString.getValue()).isEqualTo(null);
        assertThat(argStd.getValue()).isEqualTo(Executor.STD.OUT);
    }

}
