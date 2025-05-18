package com.blood.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

import com.blood.config.DbConfig;
import com.blood.model.PatientModel;

@WebServlet(asyncSupported = true, urlPatterns = { "/patient" })
public class PatientController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PatientController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        String patientName = request.getParameter("patientName").trim();
        String bloodGroup = request.getParameter("bloodGroup").trim();
        String gender = request.getParameter("gender").trim();
        String dateOfBirthStr = request.getParameter("dateOfBirth").trim();
        String requestDateStr = request.getParameter("requestDate").trim();
        int requiredUnit = Integer.parseInt(request.getParameter("requiredUnit").trim());
        
        // Get additional registration parameters
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        // Basic Validations
        if (patientName == null || patientName.isEmpty() || !isValidName(patientName)) {
            request.setAttribute("patientNameError", "Invalid name format. Use letters only.");
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
            return;
        }

        if (username == null || username.isEmpty() || username.length() <= 6 || !username.matches("[a-zA-Z0-9]+")) {
            request.setAttribute("usernameError", "Username must be at least 7 characters and alphanumeric.");
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("emailError", "Invalid email format.");
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
            return;
        }

        if (!phone.matches("\\d{10}")) {
            request.setAttribute("phoneError", "Phone number must be 10 digits.");
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
            return;
        }

        if (!bloodGroup.matches("A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-")) {
            request.setAttribute("bloodGroupError", "Invalid blood group selection.");
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
            return;
        }

        if (!gender.matches("Male|Female|Other")) {
            request.setAttribute("genderError", "Invalid gender selection.");
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
            return;
        }

        if (!isValidPassword(password)) {
            request.setAttribute("passwordError", "Password must be at least 7 characters, include uppercase, number, and special character.");
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
            return;
        }

        if (requiredUnit <= 0) {
            request.setAttribute("requiredUnitError", "Required units must be a positive number.");
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth;
        Date requestDate;

        try {
            dateOfBirth = dateFormat.parse(dateOfBirthStr);
            requestDate = dateFormat.parse(requestDateStr);

            if (dateOfBirth.after(new Date())) {
                request.setAttribute("dateOfBirthError", "Date of birth cannot be in the future.");
                request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
                return;
            }

            if (requestDate.after(new Date())) {
                request.setAttribute("requestDateError", "Request date cannot be in the future.");
                request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
                return;
            }
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "Invalid date format.");
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
            return;
        }

        // Create PatientModel object
        PatientModel patient = new PatientModel(patientName, bloodGroup, gender, dateOfBirth, requestDate, requiredUnit);

        // Insert into database
        try (Connection conn = DbConfig.getDbConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try {
                // First insert into Registration table
                String regQuery = "INSERT INTO Registration (fullName, username, email, phone, gender, dob, bloodGroup, userType, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement regStmt = conn.prepareStatement(regQuery);
                regStmt.setString(1, patientName);
                regStmt.setString(2, username);
                regStmt.setString(3, email);
                regStmt.setString(4, phone);
                regStmt.setString(5, gender);
                regStmt.setString(6, dateOfBirthStr);
                regStmt.setString(7, bloodGroup);
                regStmt.setString(8, "Patient");
                regStmt.setString(9, BCrypt.hashpw(password, BCrypt.gensalt()));

                int regResult = regStmt.executeUpdate();

                if (regResult <= 0) {
                    throw new SQLException("Failed to create registration entry");
                }

                // Then insert into Patient table
                String patientQuery = "INSERT INTO Patient (Patient_Name, Blood_Group, Gender, Date_Of_Birth, Request_Date, Required_Unit) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement patientStmt = conn.prepareStatement(patientQuery);
                patientStmt.setString(1, patient.getPatientName());
                patientStmt.setString(2, patient.getBloodGroup());
                patientStmt.setString(3, patient.getGender());
                patientStmt.setDate(4, new java.sql.Date(patient.getDateOfBirth().getTime()));
                patientStmt.setDate(5, new java.sql.Date(patient.getRequestDate().getTime()));
                patientStmt.setInt(6, patient.getRequiredUnit());

                int patientResult = patientStmt.executeUpdate();

                if (patientResult <= 0) {
                    throw new SQLException("Failed to create patient entry");
                }

                conn.commit(); // Commit transaction
                request.setAttribute("successMessage", "Patient registered successfully! Please login.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);

            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                throw e;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database driver not found: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
        } catch (SQLException e) {
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
            request.getRequestDispatcher("/WEB-INF/pages/patient.jsp").forward(request, response);
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