package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;

import javax.json.Json;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ConsoleMsgInitializer {

    private static final String JAVALAB_LATEST_VERSION = "https://api.github.com/repos/sdmoralesma/javalab/releases/latest";
    private static final String WELCOME_MSG_TEMPLATE = "Welcome to Javalab {javalabVersion} !\r\n$ java -version : {javaVersion} Java HotSpot(TM) 64-Bit Server VM";

    public String get() {
        String template = WELCOME_MSG_TEMPLATE;
        template = template.replace("{javalabVersion}", readJavalabVersion());
        template = template.replace("{javaVersion}", System.getProperty("java.version"));
        return template;
    }

    private String readJavalabVersion() {
        try {
            URL url = new URL(JAVALAB_LATEST_VERSION);
            InputStream is = url.openStream();
            return Json.createReader(is)
                    .readObject()
                    .getString("tag_name");
        } catch (IOException ex) {
            throw new NotRunnableCodeException("Cannot read javalab version from URL: " + JAVALAB_LATEST_VERSION);
        }
    }

}
