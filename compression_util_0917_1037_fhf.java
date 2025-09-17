// 代码生成时间: 2025-09-17 10:37:52
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;
# 增强安全性

public class CompressionUtil {

    /**
     * Unzips the contents of a ZIP file to a target directory.
# 扩展功能模块
     * 
     * @param zipFilePath The path to the ZIP file to unzip.
     * @param targetDirectory The directory to which the ZIP contents will be extracted.
     * @throws IOException If an I/O error occurs.
     */
    public static void unzip(String zipFilePath, String targetDirectory) throws IOException {
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            throw new FileNotFoundException("ZIP file not found: " + zipFilePath);
        }

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
# 改进用户体验
            ZipEntry entry = zipIn.getNextEntry();
            // Iterate over the entries in the ZIP file
            while (entry != null) {
# NOTE: 重要实现细节
                String filePath = targetDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // If the entry is a file, extract it
                    extractFile(zipIn, filePath);
                } else {
                    // If the entry is a directory, make the directory
# 扩展功能模块
                    (new File(filePath)).mkdirs();
                }
# 增强安全性
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
# 增强安全性
    }

    /**
     * Extracts a file from the ZIP input stream.
     * 
     * @param zipIn The ZIP input stream to read from.
     * @param filePath The path to extract the file to.
     * @throws IOException If an I/O error occurs.
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
# 添加错误处理
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    /**
# TODO: 优化性能
     * Main method to test the unzip functionality.
     * 
     * @param args The command-line arguments.
# FIXME: 处理边界情况
     */
    public static void main(String[] args) {
        String zipFilePath = "path/to/your/file.zip"; // Replace with the actual ZIP file path
        String targetDirectory = "path/to/extracted/files"; // Replace with the target directory path

        try {
            unzip(zipFilePath, targetDirectory);
            System.out.println("Unzipping completed successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred during unzipping: " + e.getMessage());
        }
    }
}