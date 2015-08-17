package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Executor {

    public static final int OK = 0;

    @Inject
    Logger tracer;

    public String execCommand(String command) {
        try {
            Process proc = Runtime.getRuntime().exec(command);
            int status = proc.waitFor();
            if (status == OK) {
                return getStreamAsString(proc.getInputStream());
            } else {
                throw new NotRunnableCodeException(getStreamAsString(proc.getErrorStream()));
            }
        } catch (InterruptedException | IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException(e);
        }
    }

    private String getStreamAsString(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

}
