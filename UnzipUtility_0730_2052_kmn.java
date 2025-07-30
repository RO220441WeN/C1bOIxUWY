// 代码生成时间: 2025-07-30 20:52:25
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility class for unzipping files using the Play Framework in Java.
 */
public class UnzipUtility {

    /**
     * Unzips a zip file to a specified directory.
     * 
     * @param zipFilePath The path of the zip file to unzip.
     * @param destDirectory The destination directory to unzip the file into.
     * @throws IOException If an I/O error occurs.
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        // Create the destination directory if it doesn't exist
        Files.createDirectories(Paths.get(destDirectory));

        // Use try-with-resources to ensure the ZipInputStream is closed
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            // Iterate through the ZIP entries
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // If the entry is a file, extract it
                    extractFile(zipIn, filePath);
                } else {
                    // If the entry is a directory, make the directory
                    Files.createDirectories(Paths.get(filePath));
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    /**
     * Extracts a file from a ZIP.
     * 
     * @param zipIn The ZipInputStream to read from.
     * @param filePath The path where the file will be written.
     * @throws IOException If an I/O error occurs.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            // Read from the zip and write to the file
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    // Main method for testing the UnzipUtility class
    public static void main(String[] args) {
        UnzipUtility unzipper = new UnzipUtility();
        try {
            unzipper.unzip("path/to/your.zip", "path/to/destination");
            System.out.println("Unzipping completed successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred during unzipping: " + e.getMessage());
        }
    }
}
