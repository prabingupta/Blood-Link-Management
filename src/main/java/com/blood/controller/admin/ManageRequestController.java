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
import com.blood.model.BloodRequestModel;

@WebServlet(asyncSupported = true, urlPatterns = { "/managerequest", "/approveRequest", "/declineRequest" })
public class ManageRequestController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManageRequestController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        fetchAndDisplayRequests(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        System.out.println("Processing request: " + servletPath);
        
        if ("/approveRequest".equals(servletPath)) {
            handleRequestAction(request, response, "Approved");
        } else if ("/declineRequest".equals(servletPath)) {
            handleRequestAction(request, response, "Declined");
        }
        
        fetchAndDisplayRequests(request, response);
    }

    private void fetchAndDisplayRequests(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BloodRequestModel> requestList = new ArrayList<>();

        try {
            conn = DbConfig.getDbConnection();
            String sql = "SELECT * FROM BloodRequest ORDER BY Request_Date DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BloodRequestModel bloodRequest = new BloodRequestModel();
                bloodRequest.setRequestId(rs.getInt("Request_ID"));
                bloodRequest.setPatientId(rs.getInt("Patient_ID"));
                bloodRequest.setBloodBankId(rs.getInt("BloodBank_ID"));
                bloodRequest.setBloodGroup(rs.getString("Blood_Group"));
                bloodRequest.setUnitRequested(rs.getInt("Unit_Requested"));
                bloodRequest.setRequestDate(rs.getDate("Request_Date"));
                bloodRequest.setStatus(rs.getString("Status"));
                requestList.add(bloodRequest);
            }

            request.setAttribute("requestList", requestList);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error fetching blood requests: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
            request.getRequestDispatcher("/WEB-INF/pages/admin/managerequest.jsp").forward(request, response);
        }
    }

    private void handleRequestAction(HttpServletRequest request, HttpServletResponse response, String status) 
            throws ServletException, IOException {
        String requestId = request.getParameter("id");
        System.out.println("Processing " + status + " for request ID: " + requestId);
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbConfig.getDbConnection();
            conn.setAutoCommit(false); // Start transaction
            
            // First check if the request exists and is in Pending status
            String checkSql = "SELECT br.Status, br.Blood_Group, br.Unit_Requested, br.BloodBank_ID " +
                            "FROM BloodRequest br WHERE br.Request_ID = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, Integer.parseInt(requestId));
            rs = pstmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("Request not found: " + requestId);
                request.setAttribute("errorMessage", "Blood request not found.");
                return;
            }
            
            String currentStatus = rs.getString("Status");
            System.out.println("Current status: " + currentStatus);
            
            if (!"Pending".equals(currentStatus)) {
                request.setAttribute("errorMessage", "Can only process pending requests. Current status: " + currentStatus);
                return;
            }
            
            // If status is being set to Approved, check blood availability
            if ("Approved".equals(status)) {
                String bloodGroup = rs.getString("Blood_Group");
                int unitsRequested = rs.getInt("Unit_Requested");
                int bloodBankId = rs.getInt("BloodBank_ID");
                
                System.out.println("Checking blood availability - Group: " + bloodGroup + ", Units: " + unitsRequested + ", Bank: " + bloodBankId);
                
                // Check if enough units are available
                String checkUnitsSql = "SELECT Units_Available FROM BloodStock WHERE Blood_Group = ? AND BloodBank_ID = ? FOR UPDATE";
                pstmt = conn.prepareStatement(checkUnitsSql);
                pstmt.setString(1, bloodGroup);
                pstmt.setInt(2, bloodBankId);
                ResultSet stockRs = pstmt.executeQuery();
                
                if (!stockRs.next()) {
                    System.out.println("No blood stock found for group: " + bloodGroup);
                    request.setAttribute("errorMessage", "No blood stock found for blood group: " + bloodGroup);
                    conn.rollback();
                    return;
                }
                
                int availableUnits = stockRs.getInt("Units_Available");
                System.out.println("Available units: " + availableUnits);
                
                if (availableUnits < unitsRequested) {
                    request.setAttribute("errorMessage", "Not enough blood units available. Requested: " + unitsRequested + ", Available: " + availableUnits);
                    conn.rollback();
                    return;
                }
                
                // Update blood stock
                String updateStockSql = "UPDATE BloodStock SET Units_Available = Units_Available - ? WHERE Blood_Group = ? AND BloodBank_ID = ?";
                pstmt = conn.prepareStatement(updateStockSql);
                pstmt.setInt(1, unitsRequested);
                pstmt.setString(2, bloodGroup);
                pstmt.setInt(3, bloodBankId);
                int stockUpdateResult = pstmt.executeUpdate();
                
                if (stockUpdateResult == 0) {
                    System.out.println("Failed to update blood stock");
                    request.setAttribute("errorMessage", "Failed to update blood stock.");
                    conn.rollback();
                    return;
                }
                
                System.out.println("Blood stock updated successfully");
            }
            
            // Update request status
            String updateSql = "UPDATE BloodRequest SET Status = ? WHERE Request_ID = ?";
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setString(1, status);
            pstmt.setInt(2, Integer.parseInt(requestId));
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                conn.commit(); // Commit transaction
                System.out.println("Request " + requestId + " " + status.toLowerCase() + " successfully");
                request.setAttribute("successMessage", "Blood request " + status.toLowerCase() + " successfully!");
            } else {
                conn.rollback(); // Rollback if update failed
                System.out.println("Failed to update request status");
                request.setAttribute("errorMessage", "Failed to " + status.toLowerCase() + " blood request.");
            }
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            System.out.println("Error processing request: " + e.getMessage());
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
        } finally {
            try { 
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {}
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
        }
    }
}