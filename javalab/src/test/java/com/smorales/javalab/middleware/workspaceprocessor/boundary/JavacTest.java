package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import com.smorales.javalab.middleware.workspaceprocessor.entity.Library;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Javac.class, BuildTool.class})
public class JavacTest {

    private BuildTool sut;

    @Before
    public void setUp() {
        sut = new Javac();
    }

    @Test
    public void shouldBuildCompileCommandOk() throws Exception {
        mockStatic(Path.class, Paths.class, Files.class);

        Path tempDir = mock(Path.class);
        List<Path> files = createFilesList();
        List<Library> libraries = createLibrariesList();

        String tempDirPath = "home/wildfly/12312312312312";
        when(tempDir.toString()).thenReturn(tempDirPath);

        Path build = mock(Path.class);
        String buildPath = tempDirPath + "/" + "build";
        when(Paths.get(anyString())).thenReturn(build);
        when(Files.createDirectory(any(Path.class))).thenReturn(build);
        when(build.toString()).thenReturn(buildPath);

        String cmd = sut.buildCompileCommand(tempDir, files, libraries);

        assertThat(cmd).isNotEmpty();//todo: improve me!
    }

    private List<Path> createFilesList() {
        return new ArrayList<>();
    }

    private List<Library> createLibrariesList() {
        return new ArrayList<>();
    }

}
