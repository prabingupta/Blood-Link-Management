package com.blood.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(asyncSupported = true, urlPatterns = { "/manageblood" })
public class ManageBloodBankController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManageBloodBankController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodlink_nepal", "root", "");

            if ("delete".equals(action)) {
                int bloodBankId = Integer.parseInt(request.getParameter("id"));
                String sql = "DELETE FROM Blood_Bank WHERE blood_bank_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, bloodBankId);
                int deleted = pstmt.executeUpdate();
                if (deleted > 0) {
                    System.out.println("Blood bank deleted successfully.");
                }
            }

            String sql = "SELECT * FROM Blood_Bank";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            request.setAttribute("bloodBankList", rs);
            request.getRequestDispatcher("/managebloodbank.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String bloodBankName = request.getParameter("bloodBankName");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodlink_nepal", "root", "");
                String sql = "INSERT INTO Blood_Bank (blood_bank_name, address, email, phone) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, bloodBankName);
                pstmt.setString(2, address);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Blood bank added successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        } else if ("update".equals(action)) {
            int bloodBankId = Integer.parseInt(request.getParameter("bloodBankId"));
            String bloodBankName = request.getParameter("bloodBankName");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodlink_nepal", "root", "");
                String sql = "UPDATE Blood_Bank SET blood_bank_name = ?, address = ?, email = ?, phone = ? WHERE blood_bank_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, bloodBankName);
                pstmt.setString(2, address);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.setInt(5, bloodBankId);

                int updated = pstmt.executeUpdate();
                if (updated > 0) {
                    System.out.println("Blood bank updated successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        }
        response.sendRedirect(request.getContextPath() + "/manageblood");
    }
}