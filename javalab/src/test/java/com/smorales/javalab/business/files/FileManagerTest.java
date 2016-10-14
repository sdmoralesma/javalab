package com.smorales.javalab.business.files;

import com.smorales.javalab.business.processor.boundary.SimpleNode;
import com.smorales.javalab.business.processor.boundary.rest.request.TreeNode;
import com.smorales.javalab.business.processor.creators.FileManagerCreators;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class FileManagerTest {

    private static final String REGEX_UUID = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
    private FileManager sut;

    @Before
    public void setUp() {
        sut = new FileManager();
        sut.tracer = mock(Logger.class);
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
