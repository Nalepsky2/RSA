package com.od;

import com.od.utils.Reader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        final RSA rsa = new RSA();

        try {
            rsa.encrypt(Reader.readPublicKey());
            rsa.decrypt(Reader.readPrivateKey());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
