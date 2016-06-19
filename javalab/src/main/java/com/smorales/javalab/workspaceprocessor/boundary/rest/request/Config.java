package com.smorales.javalab.workspaceprocessor.boundary.rest.request;

import java.util.List;

public class Config {

    private List<String> javaClasses;
    private String language;
    private String languageMode;
    private String initialNode;
    private String runnable;
    private String action;

    public List<String> getJavaClasses() {
        return javaClasses;
    }

    public void setJavaClasses(List<String> javaClasses) {
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

    public String getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(String initialNode) {
        this.initialNode = initialNode;
    }

    public String getRunnable() {
        return runnable;
    }

    public void setRunnable(String runnable) {
        this.runnable = runnable;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Config{" +
                "javaClasses=" + javaClasses +
                ", language='" + language + '\'' +
                ", languageMode='" + languageMode + '\'' +
                '}';
    }
}
