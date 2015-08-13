package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.entity.Library;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Javac.class, BuildTool.class})
public class JavacTest {

    private static final String EXPECTED_COMPILE_CMD = "javac /home/wildfly/2908e8f4-0a95-4485-92aa-9b87d348dedd/src/main/java/com/company/project/HelloWorld.java -d /home/wildfly/2908e8f4-0a95-4485-92aa-9b87d348dedd/build -cp /home/wildfly/2908e8f4-0a95-4485-92aa-9b87d348dedd/build:/home/wildfly/.m2/repository/";
    private static final String EXPECTED_RUN_CMD = "java -cp /home/wildfly/2908e8f4-0a95-4485-92aa-9b87d348dedd/build:/home/wildfly/.m2/repository/ MyClass";
    private static final String EXPECTED_TEST_CMD = "java -cp /home/wildfly/2908e8f4-0a95-4485-92aa-9b87d348dedd/build:/home/wildfly/.m2/repository/ org.junit.runner.JUnitCore src.main.MyClass";

    private BuildTool sut;

    @Before
    public void setUp() {
        sut = new Javac();
    }

    @Test
    public void shouldBuildCompileCommandOk() throws Exception {
        mockStatic(Path.class, Paths.class, Files.class);

        Path tempDir = mock(Path.class);
        String tempDirPath = "/home/wildfly/2908e8f4-0a95-4485-92aa-9b87d348dedd";
        when(tempDir.toString()).thenReturn(tempDirPath);

        String buildPath = tempDirPath + "/build";
        Path build = mock(Path.class);
        when(build.toString()).thenReturn(buildPath);
        when(Paths.get(buildPath)).thenReturn(build);
        when(Files.createDirectories(build)).thenReturn(build);

        String cmd = sut.buildCompileCommand(tempDir, createFilesList(), createLibrariesList());

        assertThat(cmd).isEqualTo(EXPECTED_COMPILE_CMD);
    }

    private List<Path> createFilesList() {
        Path javaFile1 = mock(Path.class, RETURNS_DEEP_STUBS);
        String filePath1 = "/home/wildfly/2908e8f4-0a95-4485-92aa-9b87d348dedd/src/main/java/com/company/project/HelloWorld.java";
        when(javaFile1.toAbsolutePath().toString()).thenReturn(filePath1);
        return Collections.singletonList(javaFile1);
    }

    private List<Library> createLibrariesList() {
        return new ArrayList<>();
    }


    @Test
    public void shouldBuildRunCommandOk() throws Exception {
        mockStatic(Path.class, Paths.class, Files.class);

        Path tempDir = mock(Path.class);
        String tempDirPath = "/home/wildfly/2908e8f4-0a95-4485-92aa-9b87d348dedd";
        when(tempDir.toString()).thenReturn(tempDirPath);

        String buildPath = tempDirPath + "/build";
        Path build = mock(Path.class);
        when(build.toString()).thenReturn(buildPath);
        when(Paths.get(buildPath)).thenReturn(build);
        when(Files.createDirectories(build)).thenReturn(build);

        String cmd = sut.buildRunCommand(tempDir, createClassList(), createLibrariesList());

        assertThat(cmd).isEqualTo(EXPECTED_RUN_CMD);
    }

    private List<Path> createClassList() {
        Path javaFile1 = mock(Path.class, RETURNS_DEEP_STUBS);
        String filePath1 = "src/main/java/MyClass.java";
        when(javaFile1.toString()).thenReturn(filePath1);
        return Collections.singletonList(javaFile1);
    }

    @Test
    public void shouldBuildTestCommandOk() throws Exception {
        mockStatic(Path.class, Paths.class, Files.class);

        Path tempDir = mock(Path.class);
        String tempDirPath = "/home/wildfly/2908e8f4-0a95-4485-92aa-9b87d348dedd";
        when(tempDir.toString()).thenReturn(tempDirPath);

        String buildPath = tempDirPath + "/build";
        Path build = mock(Path.class);
        when(build.toString()).thenReturn(buildPath);
        when(Paths.get(buildPath)).thenReturn(build);
        when(Files.createDirectories(build)).thenReturn(build);

        String cmd = sut.buildTestCommand(tempDir, createClassList(), createLibrariesList());

        assertThat(cmd).isEqualTo(EXPECTED_TEST_CMD);
    }
}
