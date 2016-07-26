package com.smorales.javalab.business.build;

import com.smorales.javalab.business.NotRunnableCodeException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Executor {

    private static final int TIMEOUT_VALUE = 60;
    private static final String TIMEOUT_MESSAGE_ERROR = "The process took more time than the allowed: " + TIMEOUT_VALUE + " seconds";

    @Inject
    Logger tracer;

    public String execCommand(@NotNull String command, File folder) {
        try {
            String[] cmdAsTokens = command.split(" ");
            Process process;
            if (folder == null) {
                process = new ProcessBuilder(cmdAsTokens)
                        .redirectErrorStream(true)
                        .start();
            } else {
                process = new ProcessBuilder(cmdAsTokens)
                        .directory(folder)
                        .redirectErrorStream(true)
                        .start();
            }

            boolean finished = process.waitFor(TIMEOUT_VALUE, TimeUnit.SECONDS);
            if (finished) {
                String stdOut = getStreamAsString(process.getInputStream());
                tracer.info("OUT: \n" + stdOut);
                return stdOut;
            } else {
                process.destroyForcibly();
                return TIMEOUT_MESSAGE_ERROR;
            }
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
