// 代码生成时间: 2025-08-31 07:43:27
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件备份和同步工具
 *
 * 这个类提供了文件备份和同步的功能。
 * 它能够复制文件或目录到一个备份位置，并确保备份是最新的。
 */
public class BackupAndSyncTool {

    /**
     * 备份单个文件到指定目录
     *
     * @param sourceFile 源文件路径
     * @param backupDir 备份目录
     * @return 操作成功返回true，否则返回false
     */
    public static boolean backupFile(String sourceFile, String backupDir) {
        try {
            Path sourcePath = Paths.get(sourceFile);
            Path backupPath = Paths.get(backupDir, sourcePath.getFileName().toString());
            Files.copy(sourcePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.err.println("Error backing up file: " + e.getMessage());
            return false;
        }
    }

    /**
     * 同步两个目录，确保目标目录中的文件与源目录中的文件保持一致
     *
     * @param sourceDir 源目录
     * @param targetDir 目标目录
     * @return 操作成功返回true，否则返回false
     */
    public static boolean syncDirectories(String sourceDir, String targetDir) {
        try {
            Files.walk(Paths.get(sourceDir))
                .filter(Files::isRegularFile)
                .forEach(sourcePath -> {
                    Path targetPath = Paths.get(targetDir, sourcePath.toString().replace(sourceDir, ""));
                    try {
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.err.println("Error syncing file: " + e.getMessage());
                    }
                });
            return true;
        } catch (IOException e) {
            System.err.println("Error syncing directories: " + e.getMessage());
            return false;
        }
    }

    /**
     * 主方法，用于执行备份和同步操作
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Usage: java BackupAndSyncTool <operation> <source> <target> <backupDir>");
            return;
        }

        String operation = args[0];
        String source = args[1];
        String target = args[2];
        String backupDir = args[3];

        switch (operation) {
            case "backup":
                boolean result = backupFile(source, backupDir);
                System.out.println("Backup " + (result ? "successful" : "failed"));
                break;
            case "sync":
                result = syncDirectories(source, target);
                System.out.println("Sync " + (result ? "successful" : "failed"));
                break;
            default:
                System.err.println("Invalid operation. Use 'backup' or 'sync'.");
                break;
        }
    }
}