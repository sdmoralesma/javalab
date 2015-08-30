package com.smorales.javalab.workspaceprocessor.boundary;

import com.smorales.javalab.workspaceprocessor.boundary.rest.Request;
import com.smorales.javalab.workspaceprocessor.control.Base62;
import com.smorales.javalab.workspaceprocessor.entity.Workspace;
import com.smorales.javalab.workspaceprocessor.tracing.TimeLogger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.StringReader;

@Stateless
@Interceptors({TimeLogger.class})
public class WorkspaceProcessor {

    private static final int OFFSET = 1000000;

//    @PersistenceContext
    EntityManager em;

    @Inject
    Base62 base62;

    @Inject
    BuildTool buildTool;

    public JsonObject initialize() {
//        Workspace workspace = em.createNamedQuery(Workspace.findFirstRow, Workspace.class)
//                .setMaxResults(1)
//                .getResultList()
//                .get(0);

        Workspace workspace = new Workspace(1, "eeee", "{\"console\":\"Welcome to javalab v0.2.0 !\\r\\n$ java -version : 1.8.0_60 Java HotSpot(TM) 64-Bit Server VM\",\"treedata\":[{\"id\":1,\"name\":\"dependencies\",\"type\":\"file\",\"code\":\"testCompile 'junit:junit:4.+'\",\"children\":[]},{\"id\":2,\"name\":\"src/main/java/\",\"type\":\"folder\",\"children\":[{\"id\":21,\"name\":\"com.company.project\",\"type\":\"folder\",\"children\":[{\"id\":211,\"name\":\"HelloWorld.java\",\"type\":\"file\",\"code\":\"package com.company.project;\\r\\n\\r\\npublic class HelloWorld {\\r\\n\\r\\n\\tpublic static void main(String[] args) {\\r\\n\\t\\tHelloWorld greeter = new HelloWorld();\\r\\n\\t\\tSystem.out.println(greeter.sayHello());\\r\\n\\t}\\r\\n\\r\\n\\tpublic String sayHello() {\\r\\n\\t\\treturn \\\"hello world!\\\";\\r\\n\\t}\\r\\n}\",\"cursor\":\"\",\"children\":[]}]}]},{\"id\":3,\"name\":\"src/test/java/\",\"type\":\"folder\",\"children\":[{\"id\":31,\"name\":\"com.company.project\",\"type\":\"folder\",\"children\":[{\"id\":311,\"name\":\"HelloWorldTest.java\",\"type\":\"file\",\"code\":\"package com.company.project;\\r\\n\\r\\nimport org.junit.*;\\r\\n\\r\\npublic class HelloWorldTest {\\r\\n\\r\\n\\tprivate HelloWorld sut;\\r\\n\\r\\n\\t@Before\\r\\n\\tpublic void setUp() {\\r\\n\\t\\tsut = new HelloWorld();\\r\\n\\t}\\r\\n\\r\\n\\t@Test\\r\\n\\tpublic void testHello() {\\r\\n\\t\\tAssert.assertEquals(\\\"hello world!\\\", sut.sayHello());\\r\\n\\t}\\r\\n}\",\"cursor\":\"\",\"children\":[]}]}]}],\"runnableNode\":{\"id\":\"undefined\",\"mainClass\":false,\"testClass\":false}}");
        return Json.createReader(new StringReader(workspace.getJson())).readObject();
    }

    public JsonObject getByBase62(String base62) {
        try {
//            Workspace workspace = em.createNamedQuery(Workspace.findByBase62, Workspace.class)
//                    .setParameter("base62", base62)
//                    .getSingleResult();
            Workspace workspace = new Workspace();
            return Json.createReader(new StringReader(workspace.getJson())).readObject();
        } catch (NoResultException ex) {
            return Json.createObjectBuilder().add("output", "no workspace available").build();
        }
    }

    public String runCode(Request req) {
        return buildTool.runCode(req.getTreedata(), req.getRunnableNode());
    }

    public String runTests(Request req) {
        return buildTool.testCode(req.getTreedata(), req.getRunnableNode());
    }

    public String save(String data) {
        Workspace workspace = new Workspace();
        workspace.setId(null);
        workspace.setJson(data);
        workspace.setPath(generateBase62Number());
//        em.persist(workspace);
        return workspace.getPath();
    }

    private String generateBase62Number() {
//        int lastId = em.createNamedQuery(Workspace.findIdLastRow, Integer.class)
//                .setMaxResults(1)
//                .getResultList()
//                .get(0);
        int lastId = 0;
        return base62.fromBase10WithOffset(lastId + 1, OFFSET);
    }

    public String newWorkspace() {
        return "new workspace";
    }
}

