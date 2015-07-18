package com.smorales.repl;

import java.io.*;

public class StreamGobbler implements Runnable {
    private InputStream is;
    private String type;
    private FileWriter fw;

    public StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public StreamGobbler(InputStream is, String type, File file) throws IOException {
        this.is = is;
        this.type = type;
        this.fw = new FileWriter(file);
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (fw != null) {
                    fw.write(line + "\n");
                } else {
                    System.out.println(type + ">" + line);
                }
            }
            if (fw != null) {
                fw.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}