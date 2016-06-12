package com.smorales.javalab.workspaceprocessor.boundary.rest.request;

import java.util.List;

public class InitConfig {

    private List<JavaClasses> javaClasses;
    private String language;
    private String languageMode;

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

    public String getLanguageMode() {
        return languageMode;
    }

    public void setLanguageMode(String languageMode) {
        this.languageMode = languageMode;
    }

    @Override
    public String toString() {
        return "InitConfig{" +
                "javaClasses=" + javaClasses +
                ", language='" + language + '\'' +
                ", languageMode='" + languageMode + '\'' +
                '}';
    }
}
