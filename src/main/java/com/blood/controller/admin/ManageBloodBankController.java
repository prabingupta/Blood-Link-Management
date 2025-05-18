package com.blood.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.blood.config.DbConfig;
import com.blood.model.BloodBankModel;

@WebServlet(asyncSupported = true, urlPatterns = { "/manageblood" })
public class ManageBloodBankController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManageBloodBankController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        List<BloodBankModel> bloodBankList = new ArrayList<>();

        if ("delete".equals(action)) {
            try {
                int bloodBankId = Integer.parseInt(request.getParameter("id"));
                deleteBloodBank(request, bloodBankId);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid blood bank ID format.");
            }
        }

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BloodBank");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                BloodBankModel bloodBank = new BloodBankModel();
                bloodBank.setBloodBankId(rs.getInt("BloodBank_ID"));
                bloodBank.setBloodBankName(rs.getString("BloodBank_Name"));
                bloodBank.setAddress(rs.getString("Address"));
                bloodBank.setEmail(rs.getString("Email"));
                bloodBank.setPhone(rs.getString("Phone"));
                bloodBankList.add(bloodBank);
            }
            request.setAttribute("bloodBankList", bloodBankList);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error fetching blood banks: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/pages/admin/managebloodbank.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addBloodBank(request);
        } else if ("update".equals(action)) {
            updateBloodBank(request);
        }

        response.sendRedirect(request.getContextPath() + "/manageblood");
    }

    private void addBloodBank(HttpServletRequest request) {
        String bloodBankName = request.getParameter("bloodBankName");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // Basic validation
        if (bloodBankName == null || bloodBankName.trim().isEmpty() ||
            address == null || address.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            return;
        }

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO BloodBank (BloodBank_Name, Address, Email, Phone) VALUES (?, ?, ?, ?)")) {
            
            pstmt.setString(1, bloodBankName.trim());
            pstmt.setString(2, address.trim());
            pstmt.setString(3, email.trim());
            pstmt.setString(4, phone.trim());

            int result = pstmt.executeUpdate();
            if (result > 0) {
                request.setAttribute("successMessage", "Blood bank added successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to add blood bank.");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("unique_email")) {
                request.setAttribute("errorMessage", "Email address already exists.");
            } else if (e.getMessage().contains("unique_phone")) {
                request.setAttribute("errorMessage", "Phone number already exists.");
            } else {
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error adding blood bank: " + e.getMessage());
        }
    }

    private void updateBloodBank(HttpServletRequest request) {
        try {
            int bloodBankId = Integer.parseInt(request.getParameter("bloodBankId"));
            String bloodBankName = request.getParameter("bloodBankName");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            // Basic validation
            if (bloodBankName == null || bloodBankName.trim().isEmpty() ||
                address == null || address.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty()) {
                request.setAttribute("errorMessage", "All fields are required.");
                return;
            }

            try (Connection conn = DbConfig.getDbConnection();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "UPDATE BloodBank SET BloodBank_Name=?, Address=?, Email=?, Phone=? WHERE BloodBank_ID=?")) {
                
                pstmt.setString(1, bloodBankName.trim());
                pstmt.setString(2, address.trim());
                pstmt.setString(3, email.trim());
                pstmt.setString(4, phone.trim());
                pstmt.setInt(5, bloodBankId);

                int result = pstmt.executeUpdate();
                if (result > 0) {
                    request.setAttribute("successMessage", "Blood bank updated successfully!");
                } else {
                    request.setAttribute("errorMessage", "Blood bank not found or no changes made.");
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("unique_email")) {
                    request.setAttribute("errorMessage", "Email address already exists.");
                } else if (e.getMessage().contains("unique_phone")) {
                    request.setAttribute("errorMessage", "Phone number already exists.");
                } else {
                    request.setAttribute("errorMessage", "Database error: " + e.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid blood bank ID format.");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error updating blood bank: " + e.getMessage());
        }
    }

    private void deleteBloodBank(HttpServletRequest request, int bloodBankId) {
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM BloodBank WHERE BloodBank_ID = ?")) {
            
            pstmt.setInt(1, bloodBankId);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                request.setAttribute("successMessage", "Blood bank deleted successfully!");
            } else {
                request.setAttribute("errorMessage", "Blood bank not found.");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key")) {
                request.setAttribute("errorMessage", "Cannot delete blood bank as it has associated records.");
            } else {
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error deleting blood bank: " + e.getMessage());
        }
    }
}