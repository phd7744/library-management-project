package com.example.project_oop.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class PasswordUtil {
    private static final String PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private PasswordUtil() {
    }

    public static String hashPassword(String rawPassword) {
        if (rawPassword == null) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(hashedBytes.length * 2);
            for (byte hashedByte : hashedBytes) {
                hex.append(String.format("%02x", hashedByte));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    public static String generateTemporaryPassword(int length) {
        if (length < 3) {
            throw new IllegalArgumentException("Password length must be at least 3");
        }

        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";

        StringBuilder password = new StringBuilder(length);
        password.append(upperChars.charAt(SECURE_RANDOM.nextInt(upperChars.length())));
        password.append(lowerChars.charAt(SECURE_RANDOM.nextInt(lowerChars.length())));
        password.append(numberChars.charAt(SECURE_RANDOM.nextInt(numberChars.length())));

        for (int i = 3; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(PASSWORD_CHARACTERS.length());
            password.append(PASSWORD_CHARACTERS.charAt(index));
        }

        for (int i = password.length() - 1; i > 0; i--) {
            int j = SECURE_RANDOM.nextInt(i + 1);
            char current = password.charAt(i);
            password.setCharAt(i, password.charAt(j));
            password.setCharAt(j, current);
        }

        return password.toString();
    }
}
