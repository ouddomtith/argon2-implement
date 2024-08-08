package org.ouddom.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Hasher {
    public static String hashPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
        return encodedPassword.equals(hashedPassword);
    }
}
