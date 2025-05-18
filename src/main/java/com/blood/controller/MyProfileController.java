package com.blood.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@WebServlet(asyncSupported = true, urlPatterns = { "/profile", "/updateProfile", "/deleteProfilePicture" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                maxFileSize = 1024 * 1024 * 10,      // 10MB
                maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class MyProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "resources/images/";

    public MyProfileController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        response.setContentType("text/plain");
        HttpSession session = request.getSession();

        if ("/updateProfile".equals(servletPath)) {
            Part filePart = request.getPart("profilePicture");
            if (filePart != null && filePart.getSize() > 0) {
                handleProfilePictureUpload(request, response, session, filePart);
            } else {
                handleProfileUpdate(request, response, session);
            }
        } else if ("/deleteProfilePicture".equals(servletPath)) {
            handleDeleteProfilePicture(request, response, session);
        }
    }

    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session) throws IOException {
        String userId = request.getParameter("userId");
        String fullName = request.getParameter("fullName").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String address = request.getParameter("address").trim();
        String password = request.getParameter("password").trim();

        // Validation
        Map<String, String> errors = new HashMap<>();
        if (!isValidName(fullName)) {
            errors.put("fullName", "Invalid name format. Use letters and spaces only.");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.put("email", "Invalid email format.");
        }
        if (!phone.matches("\\d{10}")) {
            errors.put("phone", "Phone number must be 10 digits.");
        }
        if (address.length() < 5) {
            errors.put("address", "Address must be at least 5 characters long.");
        }
        if (!password.isEmpty() && !isValidPassword(password)) {
            errors.put("password", "Password must be at least 7 characters, include uppercase, number, and special character.");
        }

        if (!errors.isEmpty()) {
            StringBuilder errorResponse = new StringBuilder("error:");
            for (Map.Entry<String, String> entry : errors.entrySet()) {
                errorResponse.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            }
            System.out.println("Sending error response: " + errorResponse);
            response.getWriter().write(errorResponse.toString());
            return;
        }

        // Store in session
        session.setAttribute("userId", userId);
        session.setAttribute("fullName", fullName);
        session.setAttribute("email", email);
        session.setAttribute("phone", phone);
        session.setAttribute("address", address);
        if (!password.isEmpty()) {
            session.setAttribute("password", password); // Plain password for demo; hash in production
        }

        String responseText = "success:Updated successfully";
        System.out.println("Sending success response: " + responseText);
        response.getWriter().write(responseText);
    }

    private void handleProfilePictureUpload(HttpServletRequest request, HttpServletResponse response,
                                           HttpSession session, Part filePart) throws IOException {
        String fileName = processUploadedFile(filePart);
        if (fileName == null) {
            String errorText = "error:Invalid file format. Only PNG or JPG allowed.";
            System.out.println("Sending error response: " + errorText);
            response.getWriter().write(errorText);
            return;
        }

        session.setAttribute("profilePicture", fileName);
        String responseText = "success:Profile picture uploaded successfully!:" + fileName;
        System.out.println("Sending upload response: " + responseText);
        response.getWriter().write(responseText);
    }

    private void handleDeleteProfilePicture(HttpServletRequest request, HttpServletResponse response,
                                           HttpSession session) throws IOException {
        String userId = request.getParameter("userId");
        if (userId == null || userId.trim().isEmpty()) {
            String errorText = "error:Invalid user ID.";
            System.out.println("Sending error response: " + errorText);
            response.getWriter().write(errorText);
            return;
        }

        String currentPicture = (String) session.getAttribute("profilePicture");
        if (currentPicture != null) {
            try {
                Path filePath = Paths.get(getServletContext().getRealPath("") + currentPicture);
                Files.deleteIfExists(filePath);
                System.out.println("Deleted image file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error deleting file: " + e.getMessage());
            }
            session.removeAttribute("profilePicture");
        }

        String responseText = "success:Profile picture deleted successfully!";
        System.out.println("Sending success response: " + responseText);
        response.getWriter().write(responseText);
    }

    private String processUploadedFile(Part filePart) throws IOException {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        if (!"png".equals(fileExtension) && !"jpg".equals(fileExtension)) {
            System.out.println("Invalid file extension: " + fileExtension);
            return null;
        }

        fileName = System.currentTimeMillis() + "_" + fileName;
        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;
        Path uploadFile = Paths.get(uploadPath, fileName);

        Files.createDirectories(uploadFile.getParent());
        filePart.write(uploadFile.toString());
        System.out.println("Saved image to: " + uploadFile);

        return UPLOAD_DIR + fileName;
    }

    private boolean isValidName(String name) {
        return Pattern.matches("^[a-zA-Z\\s]+$", name);
    }

    private boolean isValidPassword(String password) {
        return password.length() > 6 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
}