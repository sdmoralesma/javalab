package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import com.smorales.javalab.middleware.workspaceprocessor.boundary.rest.RunnableNode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class JavacTest {

    private BuildTool sut;

    @Before
    public void setUp() {
        sut = new Javac();
    }

    @Test
    public void shouldRunCodeOk() {
    }
}
