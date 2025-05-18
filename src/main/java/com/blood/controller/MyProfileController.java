package com.blood.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import com.blood.config.DbConfig;


@WebServlet(asyncSupported = true, urlPatterns = { "/profile" })
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
        // Get form parameters
        int userId = Integer.parseInt(request.getParameter("userId").trim());
        String fullName = request.getParameter("fullName").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String address = request.getParameter("address").trim();
        String password = request.getParameter("password").trim();

        // Get uploaded file if present
        Part filePart = request.getPart("profilePicture"); // Assuming an input with this name in a future update
        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            fileName = processUploadedFile(filePart);
            if (fileName == null) {
                request.setAttribute("errorMessage", "Invalid file format. Only PNG or JPG allowed.");
                request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
                return;
            }
        }

        // Basic Validations
        if (!isValidName(fullName)) {
            request.setAttribute("fullNameError", "Invalid name format. Use letters and spaces only.");
            request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("emailError", "Invalid email format.");
            request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
            return;
        }

        if (!phone.matches("\\d{10}")) {
            request.setAttribute("phoneError", "Phone number must be 10 digits.");
            request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
            return;
        }

        if (address.length() < 5) {
            request.setAttribute("addressError", "Address must be at least 5 characters long.");
            request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
            return;
        }

        if (!password.isEmpty() && !isValidPassword(password)) {
            request.setAttribute("passwordError", "Password must be at least 7 characters, include uppercase, number, and special character.");
            request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
            return;
        }

        // Update database using DbConfig
        try (Connection conn = DbConfig.getDbConnection()) {
            String query = "UPDATE users SET fullName = ?, email = ?, phone = ?, address = ?, password = ? WHERE userId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fullName);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);
            String hashedPassword = password.isEmpty() ? getCurrentPassword(conn, userId) : BCrypt.hashpw(password, BCrypt.gensalt());
            stmt.setString(5, hashedPassword);
            stmt.setInt(6, userId);

            int result = stmt.executeUpdate();

            if (fileName != null) {
                String updatePictureQuery = "UPDATE users SET profilePicture = ? WHERE userId = ?";
                PreparedStatement picStmt = conn.prepareStatement(updatePictureQuery);
                picStmt.setString(1, fileName);
                picStmt.setInt(2, userId);
                picStmt.executeUpdate();
                picStmt.close();
            }

            if (result > 0) {
                request.setAttribute("successMessage", "Profile updated successfully!");
                request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to update profile.");
                request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error here");
            e.printStackTrace();
            request.setAttribute("errorMessage", "JDBC Driver not found: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println("Error here 2");
            e.printStackTrace();
            String errorMessage = e.getMessage();

            if (errorMessage.contains("Duplicate entry") && errorMessage.contains("email")) {
                request.setAttribute("emailError", "Email already exists.");
            } else if (errorMessage.contains("Duplicate entry") && errorMessage.contains("phone")) {
                request.setAttribute("phoneError", "Phone number already exists.");
            } else {
                request.setAttribute("errorMessage", "Database error: " + errorMessage);
            }

            request.getRequestDispatcher("/WEB-INF/pages/myprofile.jsp").forward(request, response);
        }
    }

    private String processUploadedFile(Part filePart) throws IOException {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        if (!"png".equals(fileExtension) && !"jpg".equals(fileExtension)) {
            return null;
        }

        fileName = System.currentTimeMillis() + "_" + fileName;
        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;
        Path uploadFile = Paths.get(uploadPath + fileName);

        Files.createDirectories(uploadFile.getParent());
        filePart.write(uploadFile.toString());

        return UPLOAD_DIR + fileName;
    }

    private String getCurrentPassword(Connection conn, int userId) throws SQLException {
        String query = "SELECT password FROM users WHERE userId = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        var rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("password");
        }
        return null;
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