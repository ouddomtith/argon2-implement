package org.ouddom.service;

import org.ouddom.entity.User;
import org.ouddom.util.Argon2Hasher;
import org.ouddom.util.DatabaseConnection;
import org.ouddom.util.Base64Hasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    //TODO: Register user with a hashed password using Argon2
    public boolean registerUserWithArgon2(String username, String password) {
        String hashedPassword = Argon2Hasher.hashPassword(password);
        String sql = "INSERT INTO users (username, password, hash_type) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, "argon2");
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error registering user with Argon2", e);
            return false;
        }
    }

    //TODO: Register user with a hashed password using Base64
    public boolean registerUserWithBase64(String username, String password) {
        String hashedPassword = Base64Hasher.hashPassword(password);
        String sql = "INSERT INTO users (username, password, hash_type) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, "base64");
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error registering user with Base64", e);
            return false;
        }
    }

    //TODO: Update user's password hash to Argon2 if it's not already
    public boolean updateHashPassword(String username, String password) {
        String hashedPassword = Argon2Hasher.hashPassword(password);
        String sql = "UPDATE users SET password = ?, hash_type = ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hashedPassword);
            stmt.setString(2, "argon2");
            stmt.setString(3, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating password hash", e);
            return false;
        }
    }

    // Retrieve user by username
    public User getUserByUsername(String username) {
        String sql = "SELECT id, username, password, hash_type FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String passwordHash = rs.getString("password");
                    String hashType = rs.getString("hash_type");
                    return new User(id, username, passwordHash, hashType);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving user", e);
        }
        return null;
    }

    //TODO: Log in user
    public boolean loginUser(String username, String password) {
        //Retrieve user by username
        User user = getUserByUsername(username);

        //Check if user is found
        if (user == null) {
            return false;
        }

        boolean isPasswordValid;

        //TODO: Check the hash type of the user's password
        if ("argon2".equals(user.getHashType())) {
            //Verify password using Argon2
            isPasswordValid = Argon2Hasher.verifyPassword(password, user.getPassword());
        } else {
            //Verify password using Base64(normal process)
            isPasswordValid = Base64Hasher.verifyPassword(password, user.getPassword());

            //If password is valid, update the password hash to Argon2
            if (isPasswordValid) {
                updateHashPassword(username, password);
            }
        }

        //TODO: Return whether the password is valid
        return isPasswordValid;
    }

}
