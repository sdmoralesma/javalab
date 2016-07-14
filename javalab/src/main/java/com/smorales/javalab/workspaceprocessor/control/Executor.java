package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;

import javax.inject.Inject;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Executor {

    private static final int EXIT_CODE_OK = 0;

    @Inject
    Logger tracer;

    public String execCommand(String command, File folder) {
        try {
            Process proc = Runtime.getRuntime().exec(command, null, folder);
            int status = proc.waitFor();
            if (status == EXIT_CODE_OK) {
                return getStreamAsString(proc.getInputStream());
            }

            String stdOut = getStreamAsString(proc.getInputStream());
            String stdErr = getStreamAsString(proc.getErrorStream());

            if (stdOut == null || stdOut.trim().isEmpty()) {
                stdOut = "";
            }

            if (stdErr == null || stdErr.trim().isEmpty()) {
                stdErr = "";
            }

            tracer.info("STD_OUT: \n" + stdOut);
            tracer.severe("STD_ERR: \n" + stdErr);
            return stdOut + "\n" + stdErr;
        } catch (InterruptedException | IOException e) {
            tracer.log(Level.SEVERE, e, e::getMessage);
            throw new NotRunnableCodeException(e);
        }
    }

    private String getStreamAsString(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

}
