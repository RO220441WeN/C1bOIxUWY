// 代码生成时间: 2025-09-22 14:11:20
 * It sorts files based on their extension and moves them into
 * subfolders named after the extension.
 * </p>
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FolderOrganizer {

    /**
     * Organize the contents of the provided directory.
     * 
     * @param directoryPath The path of the directory to organize.
     * @throws IOException If an error occurs during file operations.
     */
    public void organizeFolder(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Provided path is not a valid directory.");
        }
        
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                String fileName = file.getName();
                String fileExtension = getExtension(fileName);
                String targetDirectoryPath = directoryPath + File.separator + fileExtension;
                File targetDirectory = new File(targetDirectoryPath);
                
                // Create the target directory if it does not exist.
                if (!targetDirectory.exists() && !targetDirectory.mkdirs()) {
                    throw new IOException("Failed to create directory: " + targetDirectoryPath);
                }
                
                // Move the file to the target directory.
                Path sourcePath = file.toPath();
                Path targetPath = Paths.get(targetDirectoryPath, fileName);
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    /**
     * Extracts the file extension from a file name.
     * 
     * @param fileName The name of the file.
     * @return The file extension without the dot.
     */
    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index == -1 ? "" : fileName.substring(index + 1);
    }

    /**
     * Entry point for the application.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java FolderOrganizer <directory path>");
            System.exit(1);
        }
        try {
            FolderOrganizer organizer = new FolderOrganizer();
            organizer.organizeFolder(args[0]);
            System.out.println("Folder organization completed successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: 