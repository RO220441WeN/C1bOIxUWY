// 代码生成时间: 2025-09-06 17:23:25
import play.libs.Codec;
import play.mvc.Controller;
import play.mvc.Result;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

/**
 * Password Encryption/Decryption tool using Play Framework
 */
public class PasswordEncryptionDecryption extends Controller {

    // AES Encryption Key Generation
    private static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, SecureRandom.getInstanceStrong());
        return keyGenerator.generateKey();
    }

    // AES Encryption
    private static String encryptAES(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // AES Decryption
    private static String decryptAES(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, "UTF-8");
    }

    // Endpoint to handle encryption request
    public CompletableFuture<Result> encryptPassword(String password) {
        try {
            SecretKey key = generateAESKey();
            String encryptedPassword = encryptAES(password, key);
            return CompletableFuture.completedFuture(
                ok("Encrypted Password: " + encryptedPassword)
            );
        } catch (Exception e) {
            // Error handling
            return CompletableFuture.completedFuture(
                internalServerError("Error encrypting password: " + e.getMessage())
            );
        }
    }

    // Endpoint to handle decryption request
    public CompletableFuture<Result> decryptPassword(String encryptedPassword) {
        try {
            SecretKey key = generateAESKey();
            String decryptedPassword = decryptAES(encryptedPassword, key);
            return CompletableFuture.completedFuture(
                ok("Decrypted Password: " + decryptedPassword)
            );
        } catch (Exception e) {
            // Error handling
            return CompletableFuture.completedFuture(
                internalServerError("Error decrypting password: " + e.getMessage())
            );
        }
    }
}
