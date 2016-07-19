package com.smorales.javalab.business.files;

import com.smorales.javalab.business.processor.boundary.LabPaths;
import com.smorales.javalab.business.NotRunnableCodeException;
import com.smorales.javalab.business.processor.boundary.SimpleNode;
import com.smorales.javalab.business.processor.boundary.rest.request.TreeNode;
import com.smorales.javalab.business.processor.creators.FileManagerCreators;
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
import java.util.ArrayList;
import java.util.Arrays;
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

    private static final String REGEX_UUID = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
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
    public void shouldCalculatePath() {
        //arrange
        SimpleNode toFind = FileManagerCreators.createValidSimpleNode("asdf", "myfile.java", "0");
        List<SimpleNode> arrayOfNodes = FileManagerCreators.createSimpleListOfNodes(toFind);

        //act
        Path path = sut.calculatePathForNode(toFind, arrayOfNodes);

        //assert
        assertThat(path.toString()).isEqualTo("src/main/java/com/company/project/myfile.java");
    }

    @Test
    public void shouldTraverseTheTreeNode() {
        //arrange
        TreeNode toFind = FileManagerCreators.createValidTreeNode("asdf");
        List<SimpleNode> expected = Arrays.asList(new SimpleNode("2"), new SimpleNode("3"), new SimpleNode("4"),
                new SimpleNode("1"), new SimpleNode("0"), new SimpleNode("asdf"), new SimpleNode("5"));
        List<TreeNode> arrayOfNodes = FileManagerCreators.createNestedHierarchyOfNodes(toFind);
        List<SimpleNode> simpleNodes = new ArrayList<>();

        //act
        sut.transformToSimpleList(arrayOfNodes, simpleNodes);

        //assert
        assertThat(simpleNodes).isEqualTo(expected);
    }

}
