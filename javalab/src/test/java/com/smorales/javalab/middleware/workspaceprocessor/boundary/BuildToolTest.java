package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BuildToolTest {

    private BuildTool buildTool;

    @Before
    public void setUp() {
        buildTool = BuildTool.get(BuildTool.Type.JAVAC, null, null, null);
    }

    @Test
    public void shouldInitializeWithJson() {

        assertThat(buildTool).isNotNull();

    }
}
