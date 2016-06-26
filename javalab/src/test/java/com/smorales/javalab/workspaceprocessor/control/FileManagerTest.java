package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.TreeNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileManager.class)
public class FileManagerTest {

    public static final String REGEX_UUID = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
    private FileManager sut;

    @Before
    public void setUp() {
        sut = new FileManager();
        sut.tracer = mock(Logger.class);
    }

    @Test
    public void shouldCreateTempDir() throws Exception {
        mockStatic(Paths.class, Files.class);

        when(Paths.get(anyString())).thenReturn(mock(Path.class));
        when(Files.createDirectory(any(Path.class))).thenReturn(mock(Path.class));
        doNothing().when(sut.tracer).info(Matchers::anyString);

        Path result = sut.createTempDir();

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verifyStatic();
        Paths.get(argument.capture());

        assertThat(result).isNotNull();
        assertThat(argument.getValue()).matches(LabPaths.HOME.asString() + REGEX_UUID + File.separator);
    }

    @Test
    public void shouldThrowExceptionWhenCreateTempDirThrowsIOException() throws Exception {
        mockStatic(Paths.class, Files.class);

        when(Paths.get(anyString())).thenReturn(mock(Path.class));
        when(Files.createDirectory(any(Path.class))).thenThrow(new IOException());

        assertThatThrownBy(sut::createTempDir).isInstanceOf(NotRunnableCodeException.class);
    }


    @Test
    public void shouldThrowExceptionWhenNoIdDefined() {
        //arrange
        TreeNode toFind = new TreeNode("asdf");
        List<TreeNode> arrayOfNodes = Arrays.asList(new TreeNode(), toFind);

        //act, assert
        assertThatThrownBy(() -> sut.findNode(toFind, arrayOfNodes)).isInstanceOf(NotRunnableCodeException.class)
                .hasMessageContaining("Id not defined");
    }

    @Test
    public void shouldFindNodeWhenPlainHierarchy() {
        //arrange
        TreeNode toFind = new TreeNode("asdf");

        int i = 0;
        List<TreeNode> arrayOfNodes = Arrays.asList(
                new TreeNode("" + i++), new TreeNode("" + i++),
                new TreeNode("" + i++), toFind, new TreeNode("" + i++)
        );

        //act
        TreeNode node = sut.findNode(toFind, arrayOfNodes);

        //assert
        assertThat(node).isEqualTo(toFind);
    }


    @Test
    public void shouldFindNodeWhenNestedHierarchy() {
        //arrange
        TreeNode toFind = new TreeNode("asdf");
        List<TreeNode> arrayOfNodes = createNestedHierarchyOfNodes(toFind);

        //act
        TreeNode node = sut.findNode(toFind, arrayOfNodes);

        //assert
        assertThat(node).isEqualTo(toFind);
    }

    @Test
    public void shouldReturnParentList() {
        //arrange
        TreeNode toFind = new TreeNode("asdf");
        toFind.setParentId("0");
        List<TreeNode> arrayOfNodes = createNestedHierarchyOfNodes(toFind);

        List<TreeNode> expectedParents = Arrays.asList(new TreeNode("0"), new TreeNode("1"));
        //act
        List<TreeNode> parentsForNode = sut.findParentsForNode(toFind, arrayOfNodes);

        //assert

        assertThat(parentsForNode).isEqualTo(expectedParents);
    }


    private List<TreeNode> createNestedHierarchyOfNodes(TreeNode toFind) {
        TreeNode parent = new TreeNode("0");
        parent.setParentId("1");
        parent.setChildren(Collections.singletonList(toFind));

        TreeNode grandParent = new TreeNode("1");
        grandParent.setChildren(Collections.singletonList(parent));

        return Arrays.asList(
                new TreeNode("2"), new TreeNode("3"), new TreeNode("4"), grandParent, new TreeNode("5")
        );
    }

}
