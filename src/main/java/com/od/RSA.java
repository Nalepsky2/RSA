package com.od;

import com.od.model.Key;
import com.od.utils.Reader;
import com.od.utils.Writer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RSA {

    public void encrypt(Key key) {
        try {
            final String message = Reader.readPlainMessage();
            final List<String> result = new ArrayList<>();

            System.out.println("message to encode: " + message);

            final List<String> chars = message.chars()
                    .mapToObj(this::convertChar)
                    .collect(Collectors.toList());

            if (message.length() % 2 != 0) {
                chars.add("000");
            }

            for (int i = 0; i < chars.size(); i += 2) {
                final String m = chars.get(i) + chars.get(i + 1);
                final BigInteger r = new BigInteger(m).modPow(new BigInteger(key.getP()), new BigInteger(key.getQ()));
                result.add(modPow(m, key));
            }

            result.forEach(System.out::println);

            Writer.saveEncoded(String.join(",", result));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decrypt(Key key) {
        try {
            final String enc = Reader.readEncodedMessage();
            final List<String> message = new ArrayList<>();
            final List<String> encodedChars = List.of(enc.split(","));

            final List<String> decodedChars = encodedChars.stream()
                    .map(m -> modPow(m, key))
                    .map(this::normalizeDecodedChars)
                    .collect(Collectors.toList());

            decodedChars.forEach(el -> {
                message.add(el.substring(0, 3));
                message.add(el.substring(3));
            });

            String decodedMessage = message.stream()
                    .map(c -> (char) Integer.parseInt(c))
                    .map(c -> c + "")
                    .collect(Collectors.joining());

            if(decodedMessage.charAt(decodedMessage.length() - 1) == '\u0000'){
                decodedMessage = decodedMessage.substring(0, decodedMessage.length() - 1);
            }

            System.out.println("decoded message: " + decodedMessage);

            Writer.saveDecoded(decodedMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String modPow(String m, Key key) {
        return new BigInteger(m).modPow(new BigInteger(key.getP()), new BigInteger(key.getQ())).toString();
    }

    private String normalizeDecodedChars(String c) {
        if (c.length() == 5) {
            c = "0" + c;
        }
        return c;
    }

    private String convertChar(int c) {
        if (c < 100) {
            return "0" + c;
        }
        return c + "";
    }
}
