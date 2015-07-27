package com.smorales.javalab.middleware.buildtool.boundary;

import com.smorales.javalab.middleware.buildtool.entity.Library;
import com.smorales.javalab.middleware.buildtool.entity.TreeData;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class BuildToolTest {

    @Test
    public void savingSuccessfulPrediction() {

        BuildTool bt = BuildTool.get(BuildTool.Type.JAVAC, Mockito.anyListOf(TreeData.class), Mockito.anyListOf(Library.class), null);

        assertThat()

    }
}
