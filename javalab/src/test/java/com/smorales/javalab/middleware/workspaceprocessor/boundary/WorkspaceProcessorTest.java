package com.smorales.javalab.middleware.workspaceprocessor.boundary;

import com.smorales.javalab.middleware.workspaceprocessor.boundary.rest.Request;
import com.smorales.javalab.middleware.workspaceprocessor.control.Base62;
import com.smorales.javalab.middleware.workspaceprocessor.entity.Workspace;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;
import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WorkspaceProcessorTest {

    private WorkspaceProcessor sut;

    @Before
    public void setUp() {
        sut = new WorkspaceProcessor();
        sut.em = mock(EntityManager.class, RETURNS_DEEP_STUBS);
        sut.base62 = mock(Base62.class);
        sut.buildTool = mock(BuildTool.class);
    }

    @Test
    public void shouldInitializeOk() {
        when(sut.em.createNamedQuery(Workspace.findFirstRow, Workspace.class).setMaxResults(1).getResultList().get(0))
                .thenReturn(getValidWorkspace());

        JsonObject result = sut.initialize();
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldGetByBase62Ok() {
        Workspace validWorkspace = getValidWorkspace();
        when(sut.em.createNamedQuery(Workspace.findByBase62, Workspace.class).setParameter("base62", validWorkspace.getBase62()).getSingleResult())
                .thenReturn(validWorkspace);

        JsonObject result = sut.getByBase62(validWorkspace.getBase62());
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldRunCodeOk() {
        Request req = getValidRequest();
        String okMessage = "ok run message";

        when(sut.buildTool.runCode(any(BuildToolData.class))).thenReturn(okMessage);

        String result = sut.runCode(req);
        assertThat(result).isNotNull().isEqualTo(okMessage);
        verify(sut.buildTool).runCode(any());
    }

    @Test
    public void shouldRunTestsOk() {
        Request req = getValidRequest();
        String okMessage = "ok test message";

        when(sut.buildTool.testCode(any(BuildToolData.class))).thenReturn(okMessage);

        String result = sut.runTests(req);
        assertThat(result).isNotNull().isEqualTo(okMessage);
        verify(sut.buildTool).testCode(any());
    }

    @Test
    public void shouldSave() {
        Workspace validWorkspace = getValidWorkspace();
        int lastId = 5;
        sut.base62 = new Base62();
        when(sut.em.createNamedQuery(Workspace.findIdLastRow, Integer.class).setMaxResults(1).getResultList().get(0))
                .thenReturn(lastId);

        String result = sut.save(validWorkspace.getWorkspace());
        assertThat(result).isNotNull().isEqualTo("emji");
        verify(sut.em).persist(any(Workspace.class));
    }

    @Test
    public void shouldGetNewWorkspaceOk() {
        assertThat(sut.newWorkspace(getValidRequest())).isNotNull();
    }

    private Workspace getValidWorkspace() {
        return new Workspace(1, "ejkl", "{\"exampleJson\":\"a simple example\"}");
    }

    private Request getValidRequest() {
        return new Request();
    }
}
