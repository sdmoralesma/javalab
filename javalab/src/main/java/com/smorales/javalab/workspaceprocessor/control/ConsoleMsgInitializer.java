package com.smorales.javalab.workspaceprocessor.control;

public class ConsoleMsgInitializer {

    private static final String WELCOME_MSG_TEMPLATE = "Welcome to Javalab {javalabVersion} !\r\n$ java -version : {javaVersion} Java HotSpot(TM) 64-Bit Server VM";

    public String get() {
        String template = WELCOME_MSG_TEMPLATE;
        template = template.replace("{javalabVersion}", "0.4.0");
        template = template.replace("{javaVersion}", System.getProperty("java.version"));
        return template;
    }

}
