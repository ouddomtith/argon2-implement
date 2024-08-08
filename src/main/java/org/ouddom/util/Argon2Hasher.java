package org.ouddom.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Argon2Hasher {
    private static final Logger logger = Logger.getLogger(Argon2Hasher.class.getName());
    private static final int PARALLELISM = 2;
    private static final int MEMORY = 65536;
    private static final int ITERATIONS = 3;
    private static final int HASH_LENGTH = 32;
    private static final int SALT_LENGTH = 16;

    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, SALT_LENGTH, HASH_LENGTH);

    public static String hashPassword(String password) {
        char[] charArray = password.toCharArray();
        try {
            return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, charArray);
        } finally {
            java.util.Arrays.fill(charArray, '\0');
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) {
        char[] charArray = inputPassword.toCharArray();
        try {
            return argon2.verify(storedHash, charArray);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during password verification: {0}", e.getMessage());
            return false;
        } finally {
            java.util.Arrays.fill(charArray, '\0');
        }
    }
}
