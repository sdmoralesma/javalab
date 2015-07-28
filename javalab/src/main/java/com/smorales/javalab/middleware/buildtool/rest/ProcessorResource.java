package com.smorales.javalab.middleware.buildtool.rest;

import com.smorales.javalab.middleware.buildtool.boundary.BuildTool;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/process")
public class ProcessorResource {

    @GET
    @Path("/init")
    @Produces(MediaType.TEXT_PLAIN)
    public Response initialize() {

        JsonObject model = Json.createObjectBuilder()
                .add("console", "Welcome to javalab v0.1 !\\r\\n$ java -version > \\\"1.8.0_45\\\" Java HotSpot(TM) 64-Bit Server VM")
                .add("libraries", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("name", "a name")
                                .add("version", "version")
                                .add("link", "a link")
                                .add("checked", "false")
                                .add("visible", "true")
                                .add("jar", "a jar")))
                .add("treedata", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("id", "1")
                                .add("name", "src/main/java/")
                                .add("type", "folder")
                                .add("children", Json.createArrayBuilder()
                                        .add(Json.createObjectBuilder()
                                                        .add("id", "11")
                                                        .add("name", "com.company.project")
                                                        .add("type", "folder")
                                                        .add("children", Json.createArrayBuilder()
                                                                .add(Json.createObjectBuilder()
                                                                        .add("id", "111")
                                                                        .add("name", "src/main/java/")
                                                                        .add("type", "file")
                                                                        .add("code", "package com.company.project;\\r\\n\\r\\npublic class HelloWorld {\\r\\n\\r\\n\\tpublic static void main(String[] args) {\\r\\n\\t\\tHelloWorld greeter = new HelloWorld();\\r\\n\\t\\tSystem.out.println(greeter.sayHello());\\r\\n\\t}\\r\\n\\r\\n\\tpublic String sayHello() {\\r\\n\\t\\treturn \\\"hello world!\\\";\\r\\n\\t}\\r\\n}")
                                                                        .add("cursor", "")))
                                        ))))
                .add("runnableNode", Json.createObjectBuilder()
                        .add("id", "undefined")
                        .add("mainClass", "false")
                        .add("testClass", "false"))
                .build();

        return Response.ok().entity(model).build();
    }


    @POST
    @Path("/run")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response runCode(Request req) {
        BuildTool buildTool = BuildTool.get(BuildTool.Type.JAVAC, req.getTreedata(), req.getLibraries(), req.getRunnableNode());
        String out = buildTool.runCode();
        return Response.ok().entity(out).build();
    }


    @POST
    @Path("/tests")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response runTests(Request req) {
        BuildTool buildTool = BuildTool.get(BuildTool.Type.JAVAC, req.getTreedata(), req.getLibraries(), req.getRunnableNode());
        String out = buildTool.testCode();
        return Response.ok().entity(out).build();
    }

}
