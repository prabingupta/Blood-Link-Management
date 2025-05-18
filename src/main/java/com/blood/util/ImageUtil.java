package com.blood.util;

import jakarta.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class ImageUtil {

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final int PROFILE_IMAGE_SIZE = 150; // 150x150 pixels
    private static final String[] ALLOWED_EXTENSIONS = {"png", "jpg", "jpeg"};

    /**
     * Validates an uploaded image file.
     * @param filePart The uploaded file part
     * @return Error message if validation fails, null if valid
     */
    public static String validateImage(Part filePart) {
        if (filePart == null || filePart.getSize() == 0) {
            return "No file uploaded.";
        }

        if (filePart.getSize() > MAX_FILE_SIZE) {
            return "File size exceeds 10MB limit.";
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        boolean isValidExtension = false;
        for (String ext : ALLOWED_EXTENSIONS) {
            if (ext.equals(fileExtension)) {
                isValidExtension = true;
                break;
            }
        }
        if (!isValidExtension) {
            return "Invalid file format. Only PNG or JPG allowed.";
        }

        return null;
    }

    /**
     * Processes and saves an uploaded image by resizing it.
     * @param filePart The uploaded file part
     * @param uploadPath The directory to save the file
     * @return The relative path to the saved file, or null if failed
     * @throws IOException If an error occurs during file processing
     */
    public static String processAndSaveImage(Part filePart, String uploadPath) throws IOException {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        fileName = System.currentTimeMillis() + "_" + fileName;

        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path tempFile = uploadDir.resolve("temp_" + fileName);
        filePart.write(tempFile.toString());

        // Read and resize the image
        BufferedImage originalImage = ImageIO.read(tempFile.toFile());
        if (originalImage == null) {
            Files.deleteIfExists(tempFile);
            return null;
        }

        BufferedImage resizedImage = new BufferedImage(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(
            originalImage.getScaledInstance(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, java.awt.Image.SCALE_SMOOTH),
            0, 0, null
        );

        // Save the resized image
        Path finalFile = uploadDir.resolve(fileName);
        ImageIO.write(resizedImage, fileExtension, finalFile.toFile());

        // Clean up the temporary file
        Files.deleteIfExists(tempFile);

        return finalFile.getFileName().toString();
    }

    /**
     * Deletes an image file from the specified path.
     * @param filePath The path to the file relative to the web app root
     * @param servletContextPath The real path of the servlet context
     */
    public static void deleteImage(String filePath, String servletContextPath) {
        if (filePath != null && !filePath.isEmpty()) {
            Path path = Paths.get(servletContextPath + filePath);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                System.err.println("Failed to delete image: " + e.getMessage());
            }
        }
    }

    /**
     * Builds the relative path for an image.
     * @param fileName The name of the file
     * @return The relative path
     */
    public static String buildImagePath(String fileName) {
        return "resources/images/" + fileName;
    }
}