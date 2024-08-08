package org.ouddom;

import org.ouddom.service.UserService;
import org.ouddom.util.Base64Hasher;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        //TODO: Register with Argon2
//        boolean registrationSuccessWithArgon2 = userService.registerUserWithArgon2("Test1234", "password1231");
//        System.out.println(STR."Registration success: \{registrationSuccessWithArgon2}");

        //TODO: Register with Base64
        boolean registrationSuccessWithBase64 = userService.registerUserWithBase64("Test2", "pass2");
        System.out.println(STR."Registration success: \{registrationSuccessWithBase64}");

        //TODO: Login
//        boolean loginSuccess = userService.loginUser("Test1", "pass1");
//        System.out.println(STR."Login success: \{loginSuccess}");

    }
}