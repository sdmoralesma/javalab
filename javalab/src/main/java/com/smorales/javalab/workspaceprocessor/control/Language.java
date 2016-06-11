package com.smorales.javalab.workspaceprocessor.control;

public enum Language {
    GROOVY("groovy", ".groovy", "text/x-groovy"),
    JAVA("java", ".java", "text/x-java"),
    SCALA("scala", ".scala", "text/x-scala");

    private String description;
    private String extension;
    private String mode;

    Language(String description, String extension, String mode) {
        this.description = description;
        this.extension = extension;
        this.mode = mode;
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

    public String getMode() {
        return mode;
    }
}
