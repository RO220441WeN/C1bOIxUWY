// 代码生成时间: 2025-09-02 02:01:37
 * A simple utility class for calculating hash values of strings using Java.
 * It demonstrates the usage of the PlayFramework framework and Java's built-in security features
 * for hashing operations.
 */

import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import play.libs.concurrent.HttpExecutionContext;

public class HashCalculator extends Controller {
    
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public HashCalculator(HttpExecutionContext httpExecutionContext) {
        this.httpExecutionContext = httpExecutionContext;
    }
    
    /**
     * Action method to calculate hash of a provided string.
     * @param algorithm The name of the hash algorithm to use (e.g., "SHA-256")
     * @return A Result with the calculated hash value.
     */
    public Result calculateHash(String algorithm) {
        try {
            // Get the hash algorithm instance
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            
            // Simulate some data to hash (for example purposes, a hardcoded string)
            String dataToHash = "Hello, PlayFramework!";
            
            // Perform the hash calculation
            byte[] hash = digest.digest(dataToHash.getBytes());
            
            // Convert the byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            // Return the hash result as a JSON object
            return ok(toJson(new HashResult(algorithm, hexString.toString())));
        } catch (NoSuchAlgorithmException e) {
            // Handle the case when the provided algorithm is not available
            return badRequest("Unsupported hash algorithm: " + algorithm);
        }
    }
    
    /**
     * Helper method to convert an object to JSON format.
     * @param object The object to convert to JSON.
     * @return A JSON string representing the object.
     */
    private String toJson(Object object) {
        return new com.fasterxml.jackson.databind.ObjectMapper()
                .writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
    
    /**
     * A simple DTO to hold the result of the hash calculation.
     */
    public static class HashResult {
        private String algorithm;
        private String hash;
        
        public HashResult(String algorithm, String hash) {
            this.algorithm = algorithm;
            this.hash = hash;
        }
        
        // Getters and setters
        public String getAlgorithm() { return algorithm; }
        public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
        public String getHash() { return hash; }
        public void setHash(String hash) { this.hash = hash; }
    }
}
