package com.smorales.javalab.middleware.config;

import com.smorales.javalab.middleware.buildtool.rest.ProcessorResource;

import javax.ws.rs.core.Application;
import java.util.Set;

@javax.ws.rs.ApplicationPath("rest")
public class JAXRSConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ProcessorResource.class);
        resources.add(CORSFilter.class);
    }
}
