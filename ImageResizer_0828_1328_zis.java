// 代码生成时间: 2025-08-28 13:28:52
package com.example.imageresizer;

import play.mvc.Controller;
import play.mvc.Result;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Image Resizer Controller
 * This controller handles image resizing requests.
 */
public class ImageResizer extends Controller {

    /**
     * Resizes images in a specified directory.
     *
     * @param directoryPath The path to the directory containing images to resize.
     * @param targetWidth The target width for resizing images.
     * @param targetHeight The target height for resizing images.
     * @return A Result object indicating the status of the operation.
     */
    public Result resizeImages(String directoryPath, int targetWidth, int targetHeight) {
        try {
            // Check if the directory exists
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                return badRequest("Directory does not exist or is not a directory.");
            }

            // Get all image files in the directory
            List<File> imageFiles = listImageFiles(directory);

            // Resize each image
            for (File file : imageFiles) {
                resizeImage(file, targetWidth, targetHeight);
            }

            // Return success response
            return ok("Images resized successfully.");
        } catch (IOException e) {
            // Handle IO exceptions
            return internalServerError("Error resizing images: " + e.getMessage());
        }
    }

    /**
     * Lists all image files in a directory.
     *
     * @param directory The directory to search for image files.
     * @return A list of image files.
     */
    private List<File> listImageFiles(File directory) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directory.getAbsolutePath()))) {
            return paths
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .filter(file -> file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))
                .collect(Collectors.toList());
        }
    }

    /**
     * Resizes an image.
     *
     * @param file The image file to resize.
     * @param targetWidth The target width for resizing.
     * @param targetHeight The target height for resizing.
     * @throws IOException If an I/O error occurs.
     */
    private void resizeImage(File file, int targetWidth, int targetHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(file);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
        ImageIO.write(resizedImage, getImageType(file), file);
    }

    /**
     * Determines the image type based on the file extension.
     *
     * @param file The image file.
     * @return The image type as a string.
     */
    private String getImageType(File file) {
        String fileName = file.getName();
        if (fileName.endsWith(".png")) {
            return "png";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "jpg";
        } else {
            throw new IllegalArgumentException("Unsupported image type: " + fileName);
        }
    }
}
