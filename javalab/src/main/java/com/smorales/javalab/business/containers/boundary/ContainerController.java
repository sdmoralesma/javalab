package com.smorales.javalab.business.containers.boundary;

import com.smorales.javalab.business.build.Executor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

@Singleton
public class ContainerController {

    private static final int DOCKER_HOST_IP = 4243;
    private static final String CMD_HOST_IP = "ip route show";

    @Inject
    Executor executor;

    @Inject
    Logger tracer;

    private Client client;
    private WebTarget target;

    @PostConstruct
    private void init() {
        client = ClientBuilder.newClient();
        String hostIp = extractIPHost(executor.execCommand(CMD_HOST_IP, null));
        target = client.target("http://" + hostIp + ":" + DOCKER_HOST_IP);
    }

    @PreDestroy
    private void destroy() {
        client.close();
    }

    public void createJavaContainer() {
//        client.target(uri).
    }

    public JsonObject createImage() {
        Response response = target.path("images/create")
                .queryParam("fromImage", "ubuntu")
                .request(MediaType.APPLICATION_JSON).post(Entity.text(""));
        return response.readEntity(JsonObject.class);
    }


    public JsonObject dockerVersion() {
        Response response = target.path("version").request(MediaType.APPLICATION_JSON).get();
        return response.readEntity(JsonObject.class);
    }

    private String extractIPHost(String route) {
        Optional<String> optional = Arrays.stream(route.split("\n"))
                .filter(s -> s.contains("default"))
                .findFirst();
        if (optional.isPresent()) {
            return optional.get().split(" ")[2];
        } else {
            throw new IllegalArgumentException("Can not detect host IP");
        }

    }

}
