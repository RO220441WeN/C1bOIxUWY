// 代码生成时间: 2025-08-05 08:31:28
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public class FileBackupAndSync {

    // Backup and sync method
    public static void backupAndSync(String sourceDir, String targetDir) {
        try {
            // Create the target directory if it does not exist
            Files.createDirectories(Paths.get(targetDir));

            // Walk through the directory tree
            Files.walk(Paths.get(sourceDir)).forEach(sourcePath -> {
                try {
                    // Get the relative path of the file from the source directory
                    String relativePath = sourceDir + sourcePath.subpath(1).toString();
                    File targetFile = new File(targetDir + relativePath);

                    // Create parent directories if needed
                    targetFile.getParentFile().mkdirs();

                    // Copy the file from source to target if it does not exist or is different
                    if (!targetFile.exists() || !Files.isSameFile(Paths.get(sourcePath), Paths.get(targetFile.getAbsolutePath()))) {
                        Files.copy(Paths.get(sourcePath), Paths.get(targetFile.getAbsolutePath()));
                    }
                } catch (IOException e) {
                    // Handle the exception
                    System.err.println("Error processing file: " + sourcePath + " - " + e.getMessage());
                }
            });

            // Print a success message
            System.out.println("Backup and synchronization complete.");
        } catch (IOException e) {
            // Handle the exception
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Define the source and target directories
        String sourceDir = "/path/to/source/directory";
        String targetDir = "/path/to/target/directory";

        // Call the backupAndSync method
        backupAndSync(sourceDir, targetDir);
    }
}
