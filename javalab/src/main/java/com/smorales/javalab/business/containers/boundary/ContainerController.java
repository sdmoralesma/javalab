package com.smorales.javalab.business.containers.boundary;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
public class ContainerController {

    private static final String DOCKER_HOST = "http://172.17.0.1:4243";

    private Client client;
    private WebTarget target;

    @PostConstruct
    private void init() {
        client = ClientBuilder.newClient();
        target = client.target(DOCKER_HOST);
    }

    @PreDestroy
    private void destroy() {
        client.close();
    }

    public void createJavaContainer() {
    }

    public void dockerVersion() {
        Response response = target.path("version").request(MediaType.APPLICATION_JSON).get();
        JsonObject result = response.readEntity(JsonObject.class);
        System.out.println(result);
    }

}
