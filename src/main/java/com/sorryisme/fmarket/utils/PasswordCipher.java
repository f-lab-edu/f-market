package com.sorryisme.fmarket.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordCipher {

    public static String encrypt(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    public static String getSalt() {
        return BCrypt.gensalt();
    }

    public static boolean checkPassword(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }


}
