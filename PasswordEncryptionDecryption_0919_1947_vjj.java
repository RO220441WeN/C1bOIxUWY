// 代码生成时间: 2025-09-19 19:47:10
package com.example.tools;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import play.Logger;

public class PasswordEncryptionDecryption {

    // AES encryption/decryption key
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    private static final String CHARSET = "UTF-8";

    // Generates a new secret key for AES encryption/decryption
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE, SecureRandom.getInstanceStrong());
        return keyGenerator.generateKey();
    }

    // Encrypts a password using the provided key
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypts a password using the provided key
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, CHARSET);
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            SecretKey key = generateKey();
            String password = "my_secret_password";
            String encryptedPassword = encrypt(password, key);
            Logger.info("Encrypted password: " + encryptedPassword);

            String decryptedPassword = decrypt(encryptedPassword, key);
            Logger.info("Decrypted password: " + decryptedPassword);

            // Check if the decrypted password matches the original
            if (!password.equals(decryptedPassword)) {
                Logger.error("Decryption failed!");
            } else {
                Logger.info("Decryption successful!");
            }
        } catch (Exception e) {
            Logger.error("Error occurred during encryption/decryption: ", e);
        }
    }
}
