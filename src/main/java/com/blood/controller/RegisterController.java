package com.blood.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import com.blood.config.DbConfig;

@WebServlet(asyncSupported = true, urlPatterns = { "/register","/"})
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName").trim();
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String gender = request.getParameter("gender").trim();    
        String dob = request.getParameter("dob").trim();
        String bloodGroup = request.getParameter("bloodGroup").trim();
        String userType = request.getParameter("userType").trim();
        String password = request.getParameter("password").trim();

        // Basic Validations
        if (!isValidName(fullName)) {
            request.setAttribute("fullNameError", "Invalid name format. Use letters only.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (username.length() <= 6 || !username.matches("[a-zA-Z0-9]+")) {
            request.setAttribute("usernameError", "Username must be at least 7 characters and alphanumeric.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("emailError", "Invalid email format.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (!phone.matches("\\d{10}")) {
            request.setAttribute("phoneError", "Phone number must be 10 digits.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (!gender.matches("Male|Female|Other")) {
            request.setAttribute("genderError", "Invalid gender selection.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        try {
            LocalDate birthDate = LocalDate.parse(dob);
            if (birthDate.isAfter(LocalDate.now())) {
                request.setAttribute("dobError", "Date of birth cannot be in the future.");
                request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
                return;
            }
        } catch (DateTimeParseException e) {
            request.setAttribute("dobError", "Invalid date of birth format.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (!bloodGroup.matches("A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-")) {
            request.setAttribute("bloodGroupError", "Invalid blood group selection.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (!userType.matches("Donor|Patient")) {
            request.setAttribute("userTypeError", "Invalid user type selection.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (!isValidPassword(password)) {
            request.setAttribute("passwordError", "Password must be at least 7 characters, include uppercase, number, and special character.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        // Insert into database using DBConfig
        try (Connection conn = DbConfig.getDbConnection()) {
            String query = "INSERT INTO Registration (fullName, username, email, phone, gender, dob, bloodGroup, userType, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fullName);
            stmt.setString(2, username);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, gender);
            stmt.setString(6, dob);
            stmt.setString(7, bloodGroup);
            stmt.setString(8, userType);
            stmt.setString(9, hashedPassword);

            int result = stmt.executeUpdate();

            if (result > 0) {
                request.setAttribute("successMessage", "Registration successful! Please login.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to register user.");
                request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
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

            if (errorMessage.contains("Duplicate entry") && errorMessage.contains("username")) {
                request.setAttribute("usernameError", "Username already exists.");
            } else if (errorMessage.contains("Duplicate entry") && errorMessage.contains("email")) {
                request.setAttribute("emailError", "Email already exists.");
            } else if (errorMessage.contains("Duplicate entry") && errorMessage.contains("phone")) {
                request.setAttribute("phoneError", "Phone number already exists.");
            } else {
                request.setAttribute("errorMessage", "Database error: " + errorMessage);
            }

            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
        }
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