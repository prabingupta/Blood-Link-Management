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
import com.blood.model.DonorModel;

@WebServlet(asyncSupported = true, urlPatterns = { "/managedonor" })
public class ManageDonorController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManageDonorController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch and display donor list
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DonorModel> donorList = new ArrayList<>();

        try {
            conn = DbConfig.getDbConnection();
            String sql = "SELECT * FROM Donor";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DonorModel donor = new DonorModel(
                    rs.getString("Donor_Name"),
                    rs.getString("Blood_Group"),
                    rs.getString("Phone"),
                    rs.getString("Email"),
                    rs.getString("Address"),
                    rs.getString("Gender")
                );
                donor.setDonorId(rs.getInt("Donor_ID"));
                donorList.add(donor);
            }

            request.setAttribute("donorList", donorList);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to fetch donor list: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/admin/managedonor.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int donorId = Integer.parseInt(request.getParameter("id"));

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                conn = DbConfig.getDbConnection();
                String sql = "DELETE FROM Donor WHERE Donor_ID = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, donorId);

                int deleted = pstmt.executeUpdate();
                if (deleted > 0) {
                    request.setAttribute("successMessage", "Donor deleted successfully.");
                } else {
                    request.setAttribute("errorMessage", "Failed to delete donor.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error deleting donor: " + e.getMessage());
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        }
        
        // Refresh the donor list and show the page again
        doGet(request, response);
    }
}