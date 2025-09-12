// 代码生成时间: 2025-09-12 15:57:59
package com.example.imageresizer;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.io.OutputStream;

public class ImageResizer extends Controller {

    /**
     * Resizes a list of images in a directory to a specified width and height.
     *
     * @param directoryPath Path to the directory containing the images.
     * @param targetWidth The target width for the resized images.
     * @param targetHeight The target height for the resized images.     *
     * @return A result indicating success or failure of the operation.
     */
    public Result resizeImages(String directoryPath, int targetWidth, int targetHeight) {
        try {
            Path dirPath = Paths.get(directoryPath);
            if (!Files.isDirectory(dirPath)) {
                return badRequest("Directory not found.");
            }

            Files.list(dirPath).forEach(imagePath -> {
                try {
                    resizeImage(imagePath, targetWidth, targetHeight);
                } catch (IOException e) {
                    // Log the exception and continue with the next image
                    System.err.println("Error resizing image: " + imagePath.toFile().getName() + " - " + e.getMessage());
                }
            });

            return ok("Images resized successfully.");
        } catch (IOException e) {
            return internalServerError("Error processing images.");
        }
    }

    /**
     * Resizes a single image to the specified width and height.
     *
     * @param imagePath Path to the image file.
     * @param targetWidth The target width for the resized image.
     * @param targetHeight The target height for the resized image.
     * @throws IOException If an I/O error occurs during the resizing process.
     */
    private void resizeImage(Path imagePath, int targetWidth, int targetHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(imagePath.toFile());
        if (originalImage == null) {
            throw new IOException("Unsupported image format: " + imagePath.toFile().getName());
        }

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        ImageIO.write(resizedImage, "jpg", imagePath.toFile());
    }

    // Additional methods or logic can be added here
}
