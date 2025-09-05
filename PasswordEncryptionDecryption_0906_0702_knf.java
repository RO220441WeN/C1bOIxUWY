// 代码生成时间: 2025-09-06 07:02:53
package com.example.tools;

import play.libs.Codec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryptionDecryption {

    // Generate a secret key for encryption and decryption
    private static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // 128-bit AES key
        return keyGenerator.generateKey();
    }

    // Encrypt the password
    public static String encrypt(String password) throws Exception {
        SecretKey key = generateSecretKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the password
    public static String decrypt(String encryptedPassword) throws Exception {
        SecretKey key = generateSecretKey(); // In a real-world scenario, you should store the key securely and reuse it
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
        return new String(cipher.doFinal(encryptedBytes));
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            String originalPassword = "password123";
            String encryptedPassword = encrypt(originalPassword);
            System.out.println("Encrypted: " + encryptedPassword);

            String decryptedPassword = decrypt(encryptedPassword);
            System.out.println("Decrypted: " + decryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}