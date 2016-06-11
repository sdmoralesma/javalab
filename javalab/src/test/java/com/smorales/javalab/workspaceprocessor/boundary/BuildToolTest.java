package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.model.InitConfig;
import com.smorales.javalab.workspaceprocessor.boundary.rest.model.RunnableNode;
import com.smorales.javalab.workspaceprocessor.control.Executor;
import com.smorales.javalab.workspaceprocessor.control.FileHandler;
import com.smorales.javalab.workspaceprocessor.boundary.rest.model.TreeData;
import org.junit.Before;
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
        sut.fileHandler = mock(FileHandler.class);
        sut.executor = mock(Executor.class);
        sut.tracer = mock(Logger.class);
    }

    @Test
    public void shouldRunCodeOk() throws Exception {
        mockStatic(Path.class, Paths.class, Files.class);

        Path tempDir = mock(Path.class);
        when(tempDir.toString()).thenReturn("tempDir/");
        when(sut.fileHandler.createTempDir()).thenReturn(tempDir);

        List<TreeData> treeData = createTreeData();
        RunnableNode runnableNode = createRunnableNode();

        Path parentPath = mock(Path.class);
        when(parentPath.toString()).thenReturn(treeData.get(0).getName());
        when(Paths.get(anyString(), anyString())).thenReturn(parentPath);

        Path childPath = mock(Path.class);
        when(childPath.toString()).thenReturn(treeData.get(0).getChildren().get(0).getName());
        when(Paths.get(anyString())).thenReturn(childPath);

        String result = sut.runCode(treeData, runnableNode, new InitConfig());

        assertThat(result).isEqualTo(null);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(sut.executor).execCommand(argument.capture());
        assertThat(argument.getValue()).isEqualTo(null);
    }


    private RunnableNode createRunnableNode() {
        RunnableNode node = new RunnableNode();
        node.setId("11");
        node.setMainClass(true);
        node.setTestClass(false);
        return node;
    }

    private List<TreeData> createTreeData() {
        TreeData child = new TreeData();
        child.setId("11");
        child.setType("file");
        child.setCode("hello world code");
        child.setName("HelloWorld.java");
        child.setChildren(new ArrayList<>());

        TreeData parent = new TreeData();
        parent.setId("1");
        parent.setType("folder");
        parent.setName("folder");
        parent.setChildren(Collections.singletonList(child));

        return Collections.singletonList(parent);
    }


    @Test
    public void shouldTestCodeOk() throws Exception {
        mockStatic(Path.class, Paths.class, Files.class);

        List<TreeData> treeData = createTreeData();
        RunnableNode runnableNode = createRunnableNode();

        Path tempDir = mock(Path.class);
        when(tempDir.toString()).thenReturn("tempDir/");
        when(sut.fileHandler.createTempDir()).thenReturn(tempDir);

        Path parentPath = mock(Path.class);
        when(parentPath.toString()).thenReturn(treeData.get(0).getName());
        when(Paths.get(anyString(), anyString())).thenReturn(parentPath);

        Path childPath = mock(Path.class);
        when(childPath.toString()).thenReturn(treeData.get(0).getChildren().get(0).getName());
        when(Paths.get(anyString())).thenReturn(childPath);

        String result = sut.testCode(treeData, runnableNode, new InitConfig());

        assertThat(result).isEqualTo(null);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Executor.STD> argStd = ArgumentCaptor.forClass(Executor.STD.class);
        verify(sut.executor).execCommand(argString.capture(), argStd.capture());
        assertThat(argString.getValue()).isEqualTo(null);
        assertThat(argStd.getValue()).isEqualTo(Executor.STD.OUT);
    }

}
