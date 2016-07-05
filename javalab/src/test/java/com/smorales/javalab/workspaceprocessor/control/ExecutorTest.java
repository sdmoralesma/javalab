package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Executor.class)
public class ExecutorTest {

    public static final String ALL_OK = "all ok";
    public static final String UNEXPECTED_ERROR = "unexpected error";

    private Executor sut;

    @Before
    public void setUp() {
        sut = new Executor();
        sut.tracer = mock(Logger.class, RETURNS_DEEP_STUBS);
    }

    @Test
    @Ignore
    public void shouldRunCodeOk() throws Exception {
        String cmd = "validCommand";
        Process process = mock(Process.class);
        mockStatic(Runtime.class);
        when(Runtime.getRuntime().exec(cmd)).thenReturn(process);
        int ok = 0;
        when(process.waitFor()).thenReturn(ok);
        when(process.getInputStream()).thenReturn(createAllOkInputStream());

        String result = sut.execCommand(cmd);

        assertThat(result).isEqualTo(ALL_OK);
    }

    private InputStream createAllOkInputStream() {
        byte[] data = ALL_OK.getBytes();
        return new ByteArrayInputStream(data);
    }

    @Test
    @Ignore
    public void shouldThrowExceptionWhenRunCodeStatusIsNotZero() throws Exception {
        String cmd = "validCommand";
        Process process = mock(Process.class);
        mockStatic(Runtime.class);
        when(Runtime.getRuntime().exec(cmd)).thenReturn(process);
        int notOk = 1;
        when(process.waitFor()).thenReturn(notOk);
        when(process.getErrorStream()).thenReturn(createErrorStream());

        assertThatThrownBy(() -> sut.execCommand(cmd)).isInstanceOf(NotRunnableCodeException.class)
                .hasMessage(UNEXPECTED_ERROR);
    }

    private InputStream createErrorStream() {
        byte[] data = UNEXPECTED_ERROR.getBytes();
        return new ByteArrayInputStream(data);
    }

    @Test
    @Ignore
    public void shouldThrowExceptionWhenRunCodeThrowsIOException() throws Exception {
        mockStatic(Runtime.class);
        when(Runtime.getRuntime().exec(anyString())).thenThrow(new IOException());

        assertThatThrownBy(() -> sut.execCommand("cmd")).isInstanceOf(NotRunnableCodeException.class);
    }
}
