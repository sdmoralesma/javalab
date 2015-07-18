package com.smorales.repl;

import java.io.PrintWriter;
import java.util.Scanner;

public class REPLExecutor {

    public static void main(String args[]) {

        try {
            String cmd = "java -jar /opt/archive/kulla-0.610-20150621005840.jar";
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);

            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

            // kick them off
            new Thread(errorGobbler).start();
            new Thread(outputGobbler).start();

            while (true) {
                System.out.println("Send a command: ");
                // preparing to read user input
//                Scanner scan = new Scanner(System.in);
//                String command = scan.nextLine();

                // creates a PrintWriter using the process output stream
                PrintWriter writer = new PrintWriter(proc.getOutputStream());

                // sends the command to the process
                // simulating an user input (note the \n)
                writer.write("System.out.println(\"heelo\")");
                writer.write("\n");
                writer.flush();

                // if the command is end, finalizes this app too
                if ("asdf".equals("end")) {
                    break;
                }
            }

            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
