package com.smorales.javalab.workspaceprocessor.boundary.rest;

import java.util.List;

public class InitConfig {

    private List<JavaClasses> javaClasses;
    private String language;

    public List<JavaClasses> getJavaClasses() {
        return javaClasses;
    }

    public void setJavaClasses(List<JavaClasses> javaClasses) {
        this.javaClasses = javaClasses;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
