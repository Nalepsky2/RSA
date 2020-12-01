package com.od.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void saveEncoded(String message) throws IOException {
        save(message, "src/main/resources/text.enc");
    }

    public static void saveDecoded(String message) throws IOException {
        save(message, "src/main/resources/text.dec");
    }

    private static void save(String message, String fileName) throws IOException {
        final BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(message);

        writer.close();
    }
}
