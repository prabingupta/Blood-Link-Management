package com.blood.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(asyncSupported = true, urlPatterns = { "/managerequest" })
public class ManageRequestController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ManageRequestController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch and display request list
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Blood_Bank", "root", "");
            String sql = "SELECT * FROM Blood_Request";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            request.setAttribute("requestList", rs);
            request.getRequestDispatcher("/managerequest.jsp").forward(request, response);
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

        if ("approve".equals(action)) {
            int requestId = Integer.parseInt(request.getParameter("id"));

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodlink_nepal", "root", "");
                String sql = "UPDATE Blood_Request SET status = ? WHERE Request_Id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "Approved");
                pstmt.setInt(2, requestId);

                int updated = pstmt.executeUpdate();
                if (updated > 0) {
                    System.out.println("Request approved successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        } else if ("decline".equals(action)) {
            int requestId = Integer.parseInt(request.getParameter("id"));

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Blood_Bank", "root", "");
                String sql = "UPDATE Blood_Request SET status = ? WHERE Request_Id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "Declined");
                pstmt.setInt(2, requestId);

                int updated = pstmt.executeUpdate();
                if (updated > 0) {
                    System.out.println("Request declined successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        }
        response.sendRedirect(request.getContextPath() + "/managerequest");
    }
}