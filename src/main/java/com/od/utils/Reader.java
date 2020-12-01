package com.od.utils;

import com.od.model.Key;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Reader {

    public static Key readPublicKey() throws IOException {
        return readKey("src/main/resources/keys/public.key");
    }

    public static Key readPrivateKey() throws IOException {
        return readKey("src/main/resources/keys/private.key");
    }

    private static Key readKey(String fileName) throws IOException {
        final List<String> lines;

        lines = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
        return new Key(lines.get(0), lines.get(1));
    }

    public static String readPlainMessage() throws IOException {
        return readMessage("src/main/resources/text.txt");
    }

    public static String readEncodedMessage() throws IOException {
        return readMessage("src/main/resources/text.enc");
    }

    private static String readMessage(String fileName) throws IOException {
        final Path path = Path.of(fileName);

        return Files.readString(path);
    }
}
