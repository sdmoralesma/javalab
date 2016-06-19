package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.request.Config;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.Request;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.RunnableNode;
import com.smorales.javalab.workspaceprocessor.boundary.rest.request.TreeData;
import com.smorales.javalab.workspaceprocessor.entity.Workspace;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.json.JsonObject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WorkspaceProcessorTest {

    private WorkspaceProcessor sut;

    @Before
    public void setUp() {
        sut = new WorkspaceProcessor();
        sut.em = mock(EntityManager.class, RETURNS_DEEP_STUBS);
        sut.buildTool = mock(BuildTool.class);
        sut.tracer = mock(Logger.class);
    }

    @Test
    @Ignore
    public void shouldInitializeOk() {
        when(sut.em.find(anyObject(), anyInt())).thenReturn(getValidWorkspace());

        JsonObject result = sut.initialize("java");
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldRunCodeOk() {
        Request req = getValidRequest();
        String okMessage = "ok run message";

        when(sut.buildTool.runCode(anyListOf(TreeData.class), any(RunnableNode.class), any(Config.class))).thenReturn(okMessage);

        String result = sut.runCode(req);
        assertThat(result).isNotNull().isEqualTo(okMessage);
        verify(sut.buildTool).runCode(anyListOf(TreeData.class), any(RunnableNode.class), any(Config.class));
    }

    @Test
    public void shouldRunTestsOk() {
        Request req = getValidRequest();
        String okMessage = "ok test message";

        when(sut.buildTool.testCode(anyListOf(TreeData.class), any(RunnableNode.class), any(Config.class))).thenReturn(okMessage);

        String result = sut.runTests(req);
        assertThat(result).isNotNull().isEqualTo(okMessage);
        verify(sut.buildTool).testCode(anyListOf(TreeData.class), any(RunnableNode.class), any(Config.class));
    }

    @Test
    @Ignore
    public void shouldSave() {
        Workspace validWorkspace = getValidWorkspace();
        int lastId = 5;
        when(sut.em.find(anyObject(), anyInt())).thenReturn(lastId);

        Integer result = sut.save(validWorkspace.getJson());
        assertThat(result).isNotNull().isEqualTo("emji");
        verify(sut.em).persist(any(Workspace.class));
    }

    @Test
    public void shouldGetNewWorkspaceOk() {
        assertThat(sut.newWorkspace()).isNotNull();
    }

    private Workspace getValidWorkspace() {
        return new Workspace(1, "{\"exampleJson\":\"a simple example\"}");
    }

    private Request getValidRequest() {
        return new Request();
    }
}
