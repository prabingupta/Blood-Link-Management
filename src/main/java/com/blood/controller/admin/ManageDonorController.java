package com.blood.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

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

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Blood_Bank", "root", "");
            String sql = "SELECT * FROM Donor";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            request.setAttribute("donorList", rs);
            request.getRequestDispatcher("/managedonor.jsp").forward(request, response);
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
            String Donor_Name = request.getParameter("Donor_Name");
            String Blood_Group = request.getParameter("Blood_Group");
            String Phone = request.getParameter("Phone");
            String Email = request.getParameter("Email");
            String Address = request.getParameter("Address");
            String Gender = request.getParameter("Gender");

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Blood_Bank", "root", "");
                String sql = "INSERT INTO Donor (donor_name, blood_group, phone, email, address, gender) VALUES (?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, Donor_Name);
                pstmt.setString(2, Blood_Group);
                pstmt.setString(3, Phone);
                pstmt.setString(4, Email);
                pstmt.setString(5, Address);
                pstmt.setString(6, Gender);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Donor added successfully.");
                } else {
                    System.out.println("Donor addition failed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        }
        response.sendRedirect(request.getContextPath() + "/managedonor");
    }
}