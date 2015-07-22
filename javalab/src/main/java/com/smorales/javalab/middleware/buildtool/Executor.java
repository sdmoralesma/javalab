package com.smorales.javalab.middleware.buildtool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Executor {

    public static String execCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            int status = p.waitFor();

            String procError = getStreamAsString(p.getErrorStream());
            if (!procError.equals("")) {
                throw new NotRunnableCodeException(procError);
            }

            String procOut = getStreamAsString(p.getInputStream());
            if (!procOut.equals("")) {
                return procOut;
            }

            return "";
        } catch (InterruptedException | IOException e) {
            throw new NotRunnableCodeException(e);
        }
    }

    private static String getStreamAsString(InputStream p) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(p));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

}
