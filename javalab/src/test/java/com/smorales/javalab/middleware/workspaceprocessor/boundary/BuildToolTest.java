package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BuildToolTest {

    private BuildTool sut;

    @Before
    public void setUp() {
        sut = new JavaC();
    }

    @Test
    public void shouldInitializeWithJson() {

        assertThat(sut).isNotNull();

    }
}
