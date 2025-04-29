package com.blood.controller;

import jakarta.servlet.ServletException;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.blood.config.DbConfig;

@WebServlet(asyncSupported = true, urlPatterns = { "/login"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        try (Connection conn = DbConfig.getDbConnection()) {
            String sql = "SELECT password, userType, fullName FROM Registration WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String userType = rs.getString("userType");
                String fullName = rs.getString("fullName");

                if (BCrypt.checkpw(password, storedPassword)) {
                    // Create session and store user details
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("userType", userType);
                    session.setAttribute("fullName", fullName);

                    // Redirect based on user type
                    if ("Admin".equalsIgnoreCase(userType)) {
                        response.sendRedirect(request.getContextPath() + "/adminDashboard");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/home");
                    }
                    return;
                }
            }

            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "JDBC Driver not found: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}