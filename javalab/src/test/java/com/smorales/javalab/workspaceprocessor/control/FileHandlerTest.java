package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.LabPaths;
import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThatThrownBy;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileHandler.class)
public class FileHandlerTest {

    public static final String REGEX_UUID = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
    private FileHandler sut;

    @Before
    public void setUp() {
        sut = new FileHandler();
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
        assertThat(argument.getValue()).matches(LabPaths.HOME.asString() + REGEX_UUID + "/");
    }

    @Test
    public void shouldThrowExceptionWhenCreateTempDirThrowsIOException() throws Exception {
        mockStatic(Paths.class, Files.class);

        when(Paths.get(anyString())).thenReturn(mock(Path.class));
        when(Files.createDirectory(any(Path.class))).thenThrow(new IOException());

        assertThatThrownBy(sut::createTempDir).isInstanceOf(NotRunnableCodeException.class);
    }

    @Test
    public void shouldCreateFileTreeOk() throws Exception {
//        sut.createFileTree();
    }

    @Test
    public void shouldRemoveDirOk() throws Exception {
//        sut.removeDir();
    }

}
