package com.smorales.javalab.middleware.workspaceprocessor.tracing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoggerExposer.class)
public class LoggerExposerTest {

    private LoggerExposer sut;

    @Before
    public void setUp() {
        sut = new LoggerExposer();
    }

    @Test
    public void testExposedLoggerOk() throws Exception {
        String expectedName = "com.project.MyClass";
        mockStatic(Class.class, RETURNS_DEEP_STUBS);
        when(Class.class.getName()).thenReturn(expectedName);
        InjectionPoint point = mock(InjectionPoint.class, RETURNS_DEEP_STUBS);

        Logger exposed = sut.expose(point);

        assertThat(exposed).isExactlyInstanceOf(Logger.class);
        assertThat(exposed.getName()).isEqualTo(expectedName);
    }
}
