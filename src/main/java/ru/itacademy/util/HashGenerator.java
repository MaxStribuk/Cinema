package ru.itacademy.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    public String createSHAHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA3-512");
        byte[] messageDigest =
                md.digest(password.getBytes(StandardCharsets.UTF_8));
        return convertToHex(messageDigest);
    }

    private static String convertToHex(final byte[] messageDigest) {
        BigInteger bigInt = new BigInteger(1, messageDigest);
        String hexText = bigInt.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }
}