// 代码生成时间: 2025-09-13 10:30:13
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

public class BatchFileRenamer {
    
    /**
     * Batch renames files in the specified directory with a given prefix.
     *
     * @param directoryPath The path to the directory containing files to rename.
     * @param prefix The prefix to add to each file name.
     * @return A report of the renaming process.
     * @throws IOException If an I/O error occurs.
     */
    public static String batchRenameFiles(String directoryPath, String prefix) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The provided directory path does not exist or is not a directory.");
        }

        StringBuilder report = new StringBuilder();
        int fileCount = 0;

        // Iterate over all files in the directory
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                String oldName = file.getName();
                String newName = prefix + oldName;
                File renamedFile = new File(file.getParent(), newName);

                try {
                    if (Files.move(file.toPath(), renamedFile.toPath(), StandardCopyOption.REPLACE_EXISTING) != null) {
                        fileCount++;
                        report.append("Renamed file: ").append(oldName).append(" to ").append(newName).append("
");
                    }
                } catch (IOException e) {
                    report.append("Failed to rename file: ").append(oldName).append(". Reason: ").append(e.getMessage()).append("
");
                }
            }
        }

        report.append("Total files renamed: ").append(fileCount);
        return report.toString();
    }

    /**
     * Main method to run the batch renaming process.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: BatchFileRenamer <directory-path> <prefix>");
            System.exit(1);
        }

        try {
            String directoryPath = args[0];
            String prefix = args[1];
            String report = batchRenameFiles(directoryPath, prefix);
            System.out.println(report);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(2);
        }
    }
}
