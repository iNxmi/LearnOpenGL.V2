package com.nami.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NLogger {

    private static boolean printOrigin;
    private static BufferedWriter outWriter;

    public static void init(String outPath) throws IOException {
        File folder = new File(outPath);
        if (!folder.exists())
            folder.mkdirs();
        File file = new File(folder.getAbsolutePath().concat("/opengl.log"));
        if (!file.exists())
            file.createNewFile();
        NLogger.outWriter = new BufferedWriter(new FileWriter(file));
    }

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    public static void out(Object tis, Object obj) {
        System.out.println(log(tis, "OUT: " + obj));
    }

    public static void err(Object tis, Object obj) {
        System.err.println(log(tis, "ERR: " + obj));
    }

    private static String log(Object tis, Object obj) {
        StringBuilder sb = new StringBuilder();
        if (printOrigin)
            sb.append(tis.getClass().getName() + " - ");
        sb.append(format.format(new Date()) + " | " + obj);

        String line = sb.toString();
        try {
            outWriter.append(line);
            outWriter.newLine();
            outWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return line;
    }

    public static void close() throws IOException {
        outWriter.close();
    }

    public void setPrintOrigin(boolean value) {
        NLogger.printOrigin = value;
    }

}
