package com.smorales.javalab.middleware.workspaceprocessor.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileHandler.class)
public class FileHandlerTest {

    private FileHandler sut;

    @Before
    public void setUp() {
        sut = new FileHandler();
        sut.tracer = mock(Logger.class);
    }

    @Test
    public void shouldCreateTempDir() throws Exception {
        mockStatic(Paths.class, Files.class);

        when(Paths.get(anyString(), anyVararg())).thenReturn(mock(Path.class));
        when(Files.createDirectory(any(Path.class))).thenReturn(mock(Path.class));
        doNothing().when(sut.tracer).info(anyString());

        Path result = sut.createTempDir();

        assertThat(result).isNotNull();
    }

}
