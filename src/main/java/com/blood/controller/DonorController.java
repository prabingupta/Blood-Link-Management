package com.blood.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.blood.config.DbConfig;
import com.blood.model.DonorModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/donorregister" })
public class DonorController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DonorController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setTotalDonors(request);
        request.getRequestDispatcher("/WEB-INF/pages/donor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters with null checks
        String donorName = request.getParameter("donorName");
        String bloodGroup = request.getParameter("bloodGroup");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");

        // Log received parameters
        System.out.println("Received parameters: donorName=" + donorName + ", bloodGroup=" + bloodGroup + 
                          ", phone=" + phone + ", email=" + email + ", address=" + address + 
                          ", gender=" + gender);

        // Initialize error flag
        boolean hasError = false;

        // Validate inputs with explicit null checks
        if (donorName == null || donorName.trim().isEmpty() || !isValidName(donorName.trim())) {
            request.setAttribute("donorNameError", "Invalid name. Use letters and spaces only.");
            hasError = true;
        }

        if (bloodGroup == null || !bloodGroup.matches("A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-")) {
            request.setAttribute("bloodGroupError", "Please select a valid blood group.");
            hasError = true;
        }

        if (phone == null || !phone.matches("\\d{10}")) {
            request.setAttribute("phoneError", "Phone number must be exactly 10 digits.");
            hasError = true;
        }

        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("emailError", "Invalid email format.");
            hasError = true;
        }

        if (address == null || address.trim().isEmpty()) {
            request.setAttribute("addressError", "Address cannot be empty.");
            hasError = true;
        }

        if (gender == null || !gender.matches("Male|Female|Other")) {
            request.setAttribute("genderError", "Please select a valid gender.");
            hasError = true;
        }

        // Log validation result
        System.out.println("Validation hasError=" + hasError);

        // If validation fails, forward back to form
        if (hasError) {
            setTotalDonors(request);
            request.getRequestDispatcher("/WEB-INF/pages/donor.jsp").forward(request, response);
            return;
        }

        // Trim valid inputs (already checked for null)
        donorName = donorName.trim();
        bloodGroup = bloodGroup.trim();
        phone = phone.trim();
        email = email.trim();
        address = address.trim();
        gender = gender.trim();

        // Create DonorModel
        DonorModel donor = new DonorModel(donorName, bloodGroup, phone, email, address, gender);

        // Log trimmed inputs
        System.out.println("Trimmed inputs: donorName=" + donor.getDonorName() + ", bloodGroup=" + donor.getBloodGroup() + 
                          ", phone=" + donor.getPhone() + ", email=" + donor.getEmail() + ", address=" + donor.getAddress() + 
                          ", gender=" + donor.getGender());

        // Insert into Donor table
        Connection conn = null;
        try {
            conn = DbConfig.getDbConnection();
            System.out.println("Database connection established: " + (conn != null));

            // Ensure auto-commit
            conn.setAutoCommit(true);
            System.out.println("Auto-commit enabled: " + conn.getAutoCommit());

            String query = "INSERT INTO Donor (Donor_Name, Blood_Group, Phone, Email, Address, Gender) VALUES (?, ?, ?, ?, ?, ?)";
            System.out.println("Executing query: " + query);

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, donor.getDonorName());
                stmt.setString(2, donor.getBloodGroup());
                stmt.setString(3, donor.getPhone());
                stmt.setString(4, donor.getEmail());
                stmt.setString(5, donor.getAddress());
                stmt.setString(6, donor.getGender());

                System.out.println("Prepared statement parameters set");

                int result = stmt.executeUpdate();
                System.out.println("Rows affected: " + result);

                if (result > 0) {
                    request.setAttribute("successMessage", "Donor registered successfully!");
                    System.out.println("Success: Donor registered");
                } else {
                    request.setAttribute("errorMessage", "Failed to register donor: No rows affected. Verify table schema.");
                    hasError = true;
                    System.out.println("Failure: No rows affected by INSERT");
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database driver not found: " + e.getMessage());
            hasError = true;
            System.out.println("ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            int errorCode = e.getErrorCode();
            System.out.println("SQLException: " + errorMessage + ", ErrorCode: " + errorCode);
            if (errorMessage.contains("Duplicate entry") && errorMessage.contains("Email")) {
                request.setAttribute("emailError", "This email is already registered.");
            } else if (errorMessage.contains("Duplicate entry") && errorMessage.contains("Phone")) {
                request.setAttribute("phoneError", "This phone number is already registered.");
            } else if (errorMessage.contains("Unknown column")) {
                request.setAttribute("errorMessage", "Database error: Unknown column in table. Check schema (e.g., Donor_Name, Blood_Group).");
            } else if (errorMessage.contains("Column 'Donor_ID' cannot be null") || errorCode == 1048) {
                request.setAttribute("errorMessage", "Database error: Donor_ID is not auto-incremented. Set Donor_ID as AUTO_INCREMENT PRIMARY KEY.");
            } else {
                request.setAttribute("errorMessage", "Database error: " + errorMessage + " (ErrorCode: " + errorCode + ")");
            }
            hasError = true;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Database connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }

        // Log final state
        System.out.println("Forwarding to JSP, hasError=" + hasError);

        setTotalDonors(request);
        request.getRequestDispatcher("/WEB-INF/pages/donor.jsp").forward(request, response);
    }

    private boolean isValidName(String name) {
        return name != null && Pattern.matches("^[a-zA-Z\\s]+$", name) && !name.trim().isEmpty();
    }

    private void setTotalDonors(HttpServletRequest request) {
        int totalDonors = 0;
        Connection conn = null;
        try {
            conn = DbConfig.getDbConnection();
            String query = "SELECT COUNT(*) AS total FROM Donor";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        totalDonors = rs.getInt("total");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error counting donors: " + e.getMessage());
            System.out.println("Error in setTotalDonors: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        request.setAttribute("totalDonors", totalDonors);
        System.out.println("Total donors set: " + totalDonors);
    }
}