package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.RunnableNode;
import com.smorales.javalab.workspaceprocessor.control.Executor;
import com.smorales.javalab.workspaceprocessor.control.FileHandler;
import com.smorales.javalab.workspaceprocessor.entity.Library;
import com.smorales.javalab.workspaceprocessor.entity.TreeData;
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
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

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
        BuildToolData data = createDataMainClass();

        Path tempDir = mock(Path.class);
        when(sut.fileHandler.createTempDir()).thenReturn(tempDir);

        String output = sut.runCode(data);

        assertThat(output).isEqualTo(null);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(sut.executor, times(2)).execCommand(argument.capture());
        assertThat(argument.getValue()).isEqualTo(null);
    }


    private BuildToolData createDataMainClass() {
        List<TreeData> treedata = new ArrayList<>();
        TreeData treeItem = new TreeData();
        treeItem.setId("1");
        treeItem.setChildren(new ArrayList<>());
        treeItem.setCode("hello world code");
        treeItem.setName("node name");
        treeItem.setType("file");

        List<Library> libraries = new ArrayList<>();


        RunnableNode node = new RunnableNode();
        node.setId("12345");
        node.setMainClass(true);
        node.setTestClass(false);
        return new BuildToolData(treedata, libraries, node);
    }


    @Test
    public void shouldTestCodeOk() throws Exception {
        mockStatic(Path.class, Paths.class, Files.class);
        BuildToolData data = createDataTestClass();

        Path tempDir = mock(Path.class);
        when(sut.fileHandler.createTempDir()).thenReturn(tempDir);

        sut.runCode(data);
    }

    private BuildToolData createDataTestClass() {
        ArrayList<TreeData> treedata = new ArrayList<>();
        ArrayList<Library> libraries = new ArrayList<>();


        RunnableNode node = new RunnableNode();
        node.setId("12345");
        node.setMainClass(true);
        node.setTestClass(false);

        return new BuildToolData(treedata, libraries, node);
    }
}
