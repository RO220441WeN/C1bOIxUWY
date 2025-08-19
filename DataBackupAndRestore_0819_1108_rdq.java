// 代码生成时间: 2025-08-19 11:08:11
package com.example;

import play.mvc.Controller;
import play.mvc.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DataBackupAndRestore extends Controller {

    private static final String BACKUP_DIRECTORY = "backups/";
    private static final String TEMP_DIRECTORY = "temp/";
    private static final String EXTENSION = ".zip";

    // Method to backup data to a zip file
    public Result backupData() {
        try {
            // Create backup directory if not exists
            File backupDir = new File(BACKUP_DIRECTORY);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            // Create a new zip file for backup
            Path backupFilePath = Paths.get(BACKUP_DIRECTORY + System.currentTimeMillis() + EXTENSION);
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(backupFilePath.toFile()))) {
                // Add data files to the zip
                addFileToZip(zos, Paths.get("data/"), "");
            } catch (IOException e) {
                return internalServerError("Failed to backup data: " + e.getMessage());
            }

            return ok("Data backed up successfully.");
        } catch (Exception e) {
            return internalServerError("Error during backup: " + e.getMessage());
        }
    }

    // Method to restore data from a zip file
    public Result restoreData(String backupFileName) {
        try {
            // Check if backup file exists
            Path backupFilePath = Paths.get(BACKUP_DIRECTORY + backupFileName);
            if (!Files.exists(backupFilePath)) {
                return badRequest("Backup file not found.");
            }

            // Create temp directory if not exists
            File tempDir = new File(TEMP_DIRECTORY);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            // Extract zip file to temp directory
            extractZipFile(backupFilePath, Paths.get(TEMP_DIRECTORY));

            // Copy data from temp directory to data directory
            copyDirectory(Paths.get(TEMP_DIRECTORY), Paths.get("data/"));

            return ok("Data restored successfully.");
        } catch (Exception e) {
            return internalServerError("Error during restore: " + e.getMessage());
        }
    }

    // Helper method to add a file to the zip
    private void addFileToZip(ZipOutputStream zos, Path folderPath, String parentPath) throws IOException {
        Files.walk(folderPath).forEach(path -> {
            try {
                String zipPath = parentPath + path.toString().substring(folderPath.toString().length() + 1);
                if (Files.isDirectory(path)) {
                    zos.putNextEntry(new ZipEntry(zipPath + "/"));
                } else {
                    zos.putNextEntry(new ZipEntry(zipPath));
                    Files.copy(path, zos);
                    zos.closeEntry();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Helper method to extract a zip file
    private void extractZipFile(Path zipFilePath, Path destinationPath) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFilePath))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                Path newPath = destinationPath.resolve(zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(newPath);
                } else {
                    Files.createDirectories(newPath.getParent());
                    try (FileOutputStream fos = new FileOutputStream(newPath.toFile())) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) >= 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
    }

    // Helper method to copy a directory
    private void copyDirectory(Path sourcePath, Path targetPath) throws IOException {
        Files.walk(sourcePath).forEach(source -> {
            try {
                Path target = targetPath.resolve(sourcePath.relativize(source));
                if (Files.isDirectory(source)) {
                    Files.createDirectories(target);
                } else {
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
