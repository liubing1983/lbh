package com.lb.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lb on 2014/11/11.
 */
public class Shell {

    public static void main(String[] args){
        String[] cmd1 = {"echo","123123123123123123",};
        try {
            Process proc = Runtime.getRuntime().exec(cmd1);
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "Error");
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "Output");
            errorGobbler.start();
            outputGobbler.start();
            proc.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class StreamGobbler extends Thread {

    InputStream is;
    String type;

    public StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (type.equals("Error")) {
                    System.out.println("Error   :" + line);
                } else {
                    System.out.println("Debug:" + line);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
