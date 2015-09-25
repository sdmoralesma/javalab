package com.smorales.javalab.workspaceprocessor.control;

public enum Language {
    JAVA, GROOVY, SCALA;

    public static Language from(String s) {
        return Language.valueOf(s.toUpperCase());
    }

}
