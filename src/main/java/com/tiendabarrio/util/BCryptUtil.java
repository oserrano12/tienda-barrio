package com.tiendabarrio.util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {
    private static final int LOG_ROUNDS = 12;

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String password = "AdminDB123";
        String hash = hashPassword(password);

        System.out.println("Contraseña: " + password);
        System.out.println("Hash: " + hash);
        System.out.println("Verificación correcta: " + checkPassword(password, hash));
        System.out.println("Verificación incorrecta: " + checkPassword("claveMala", hash));
    }
}