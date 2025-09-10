// 代码生成时间: 2025-09-10 16:34:46
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryptionDecryption {

    // 使用AES加密算法和CBC模式
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";
    private static final byte[] salt = new byte[]{
        (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
        (byte)0x56, (byte)0x34, (byte)0xE3, (byte)0x03
    };

    public static SecretKeySpec generateKey(String password) throws Exception {
        // 根据密码生成密钥
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGen.init(128, SecureRandom.getInstanceStrong());
        SecretKey secretKey = keyGen.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
    }

    public static byte[] encrypt(String data, SecretKeySpec keySpec) throws Exception {
        // 加密数据
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] iv = new byte[cipher.getBlockSize()];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] encryptedData, SecretKeySpec keySpec) throws Exception {
        // 解密数据
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return new String(cipher.doFinal(encryptedData));
    }

    public static String encryptPassword(String password) throws Exception {
        // 加密密码
        SecretKeySpec keySpec = generateKey(password);
        byte[] encryptedData = encrypt(password, keySpec);
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decryptPassword(String encryptedPassword) throws Exception {
        // 解密密码
        byte[] encryptedData = Base64.getDecoder().decode(encryptedPassword);
        SecretKeySpec keySpec = generateKey(encryptedPassword);
        return decrypt(encryptedData, keySpec);
    }

    public static void main(String[] args) {
        try {
            String password = "myPassword123";
            String encryptedPassword = encryptPassword(password);
            System.out.println("Encrypted Password: " + encryptedPassword);
            String decryptedPassword = decryptPassword(encryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}