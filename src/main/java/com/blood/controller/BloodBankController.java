package com.blood.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.blood.config.DbConfig;
import com.blood.model.BloodBankModel;

@WebServlet(asyncSupported = true, urlPatterns = { "/bloodbank" })
public class BloodBankController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BloodBankController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<BloodBankModel> bloodBankList = new ArrayList<>();

        // Fetch blood banks from the database
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BloodBank");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BloodBankModel bloodBank = new BloodBankModel();
                bloodBank.setBloodBankId(rs.getInt("BloodBank_ID"));
                bloodBank.setBloodBankName(rs.getString("BloodBank_Name"));
                bloodBank.setAddress(rs.getString("Address"));
                bloodBank.setEmail(rs.getString("Email"));
                bloodBank.setPhone(rs.getString("Phone"));
                bloodBankList.add(bloodBank);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error here");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to connect to database. Please try again later.");
        } catch (SQLException e) {
            System.out.println("Error here 2");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to fetch blood bank information. Please try again later.");
        }

        // Set the bloodBankList attribute for the JSP
        request.setAttribute("bloodBankList", bloodBankList);
        request.getRequestDispatcher("/WEB-INF/pages/bloodbank.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "add":
                    addBloodBank(request, response);
                    break;
                case "edit":
                    editBloodBank(request, response);
                    break;
                case "delete":
                    deleteBloodBank(request, response);
                    break;
                default:
                    doGet(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing your request.");
            doGet(request, response);
        }
    }

    private void addBloodBank(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bloodBankName = request.getParameter("bloodBankName");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO BloodBank (BloodBank_Name, Address, Email, Phone) VALUES (?, ?, ?, ?)")) {
            
            stmt.setString(1, bloodBankName);
            stmt.setString(2, address);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                request.setAttribute("successMessage", "Blood bank added successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to add blood bank.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error adding blood bank: " + e.getMessage());
        }
        
        doGet(request, response);
    }

    private void editBloodBank(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bloodBankId = Integer.parseInt(request.getParameter("bloodBankId"));
        String bloodBankName = request.getParameter("bloodBankName");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE BloodBank SET BloodBank_Name=?, Address=?, Email=?, Phone=? WHERE BloodBank_ID=?")) {
            
            stmt.setString(1, bloodBankName);
            stmt.setString(2, address);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setInt(5, bloodBankId);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                request.setAttribute("successMessage", "Blood bank updated successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to update blood bank.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating blood bank: " + e.getMessage());
        }
        
        doGet(request, response);
    }

    private void deleteBloodBank(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bloodBankId = Integer.parseInt(request.getParameter("bloodBankId"));

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM BloodBank WHERE BloodBank_ID=?")) {
            
            stmt.setInt(1, bloodBankId);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                request.setAttribute("successMessage", "Blood bank deleted successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to delete blood bank.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error deleting blood bank: " + e.getMessage());
        }
        
        doGet(request, response);
    }
}