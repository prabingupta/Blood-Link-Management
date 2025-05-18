package com.blood.controller;

import jakarta.servlet.ServletException;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.blood.config.DbConfig;
import com.blood.model.BloodRequestModel;

@WebServlet(asyncSupported = true, urlPatterns = { "/request" })
public class RequestController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RequestController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        int patientId = Integer.parseInt(request.getParameter("patientId").trim());
        int bloodBankId = Integer.parseInt(request.getParameter("bloodBankId").trim());
        String bloodGroup = request.getParameter("bloodGroup").trim();
        int unitRequested = Integer.parseInt(request.getParameter("unitRequested").trim());
        String requestDateStr = request.getParameter("requestDate").trim();
        String status = "Pending";

        // Basic Validations
        if (patientId <= 0) {
            request.setAttribute("patientIdError", "Patient ID must be a positive number.");
            request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
            return;
        }

        // Validate if Patient exists
        try (Connection conn = DbConfig.getDbConnection()) {
            String checkPatientQuery = "SELECT COUNT(*) FROM Patient WHERE Patient_ID = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkPatientQuery);
            checkStmt.setInt(1, patientId);
            var rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                request.setAttribute("patientIdError", "Patient ID does not exist. Please enter a valid Patient ID.");
                request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error validating Patient ID: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
            return;
        }

        // Validate if Blood Bank exists
        try (Connection conn = DbConfig.getDbConnection()) {
            String checkBankQuery = "SELECT COUNT(*) FROM BloodBank WHERE BloodBank_ID = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkBankQuery);
            checkStmt.setInt(1, bloodBankId);
            var rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                request.setAttribute("bloodBankIdError", "Blood Bank ID does not exist. Please enter a valid Blood Bank ID.");
                request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error validating Blood Bank ID: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
            return;
        }

        if (bloodBankId <= 0) {
            request.setAttribute("bloodBankIdError", "Blood Bank ID must be a positive number.");
            request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
            return;
        }

        if (!bloodGroup.matches("A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-")) {
            request.setAttribute("bloodGroupError", "Invalid blood group selection.");
            request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
            return;
        }

        if (unitRequested <= 0) {
            request.setAttribute("unitRequestedError", "Units requested must be a positive number.");
            request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
            return;
        }

        try {
            LocalDate requestDate = LocalDate.parse(requestDateStr);
            if (requestDate.isAfter(LocalDate.now())) {
                request.setAttribute("requestDateError", "Request date cannot be in the future.");
                request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
                return;
            }
        } catch (DateTimeParseException e) {
            request.setAttribute("requestDateError", "Invalid request date format.");
            request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
            return;
        }

        // Create BloodRequestModel object
        BloodRequestModel bloodRequest = new BloodRequestModel(patientId, bloodBankId, java.sql.Date.valueOf(requestDateStr), bloodGroup, unitRequested, status);

        // Insert into database using DbConfig
        try (Connection conn = DbConfig.getDbConnection()) {
            String query = "INSERT INTO BloodRequest (Patient_ID, BloodBank_ID, Request_Date, Blood_Group, Unit_Requested, Status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bloodRequest.getPatientId());
            stmt.setInt(2, bloodRequest.getBloodBankId());
            stmt.setDate(3, (Date) bloodRequest.getRequestDate());
            stmt.setString(4, bloodRequest.getBloodGroup());
            stmt.setInt(5, bloodRequest.getUnitRequested());
            stmt.setString(6, bloodRequest.getStatus());

            int result = stmt.executeUpdate();

            if (result > 0) {
                request.setAttribute("successMessage", "Blood request submitted successfully!");
                request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to submit blood request.");
                request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error here");
            e.printStackTrace();
            request.setAttribute("errorMessage", "JDBC Driver not found: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println("Error here 2");
            e.printStackTrace();
            String errorMessage = e.getMessage();
            request.setAttribute("errorMessage", "Database error: " + errorMessage);
            request.getRequestDispatcher("/WEB-INF/pages/bloodrequest.jsp").forward(request, response);
        }
    }
}