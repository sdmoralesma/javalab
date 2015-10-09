package com.smorales.javalab.workspaceprocessor.control;

public enum Language {
    GROOVY("groovy", ".groovy"),
    JAVA("java", ".java"),
    SCALA("scala", ".scala");

    private String description;
    private String extension;

    Language(String description, String extension) {
        this.description = description;
        this.extension = extension;
    }

    public static Language from(String s) {
        return Language.valueOf(s.toUpperCase());
    }

    public String getDescription() {
        return description;
    }

    public String getExtension() {
        return extension;
    }
}
