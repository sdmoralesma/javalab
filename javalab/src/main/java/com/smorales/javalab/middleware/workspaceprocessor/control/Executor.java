package com.smorales.javalab.middleware.workspaceprocessor.control;

import com.smorales.javalab.middleware.workspaceprocessor.boundary.NotRunnableCodeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Executor {

    public static final int OK = 0;

    public String execCommand(String command) {
        try {
            Process proc = Runtime.getRuntime().exec(command);
            int status = proc.waitFor();
            if (status == OK) {
                return getStreamAsString(proc.getInputStream());
            } else {
                String procError = getStreamAsString(proc.getErrorStream());
                throw new NotRunnableCodeException(procError);
            }
        } catch (InterruptedException | IOException e) {
            throw new NotRunnableCodeException(e);
        }
    }

    private String getStreamAsString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

}
