package com.blood.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.blood.config.DbConfig;
import com.blood.util.CookieUtil;
import com.blood.util.SessionUtil;

/**
 * LoginController handles login requests for Blood Link Nepal.
 * It authenticates admin users with hardcoded credentials and Donor/Patient users via the database.
 * Manages session attributes and cookies for successful logins.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor initializes the LoginController.
     */
    public LoginController() {
        super();
    }

    /**
     * Handles GET requests to display the login page.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for user login.
     * Authenticates admin with hardcoded credentials (admin/admin) or Donor/Patient via the Registration table.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        // Check for admin credentials (hardcoded)
        if ("admin".equals(username) && "admin".equals(password)) {
            SessionUtil.setAttribute(request, "username", username);
            SessionUtil.setAttribute(request, "userType", "Admin");
            CookieUtil.addCookie(response, "role", "admin", 5 * 30); // 150 days
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // Authenticate Donor or Patient via database
        try (Connection conn = DbConfig.getDbConnection()) {
            String sql = "SELECT password, userType FROM Registration WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        String userType = rs.getString("userType");

                        if (BCrypt.checkpw(password, storedPassword)) {
                            SessionUtil.setAttribute(request, "username", username);
                            SessionUtil.setAttribute(request, "userType", userType);
                            CookieUtil.addCookie(response, "role", "user", 5 * 30); // 150 days
                            response.sendRedirect(request.getContextPath() + "/home");
                            return;
                        }
                    }
                }
            }
            handleLoginFailure(request, response, false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            handleLoginFailure(request, response, null);
        } catch (SQLException e) {
            e.printStackTrace();
            handleLoginFailure(request, response, null);
        }
    }

    /**
     * Handles login failures by setting an error attribute and forwarding to the login page.
     *
     * @param request      HttpServletRequest object
     * @param response     HttpServletResponse object
     * @param loginStatus  Boolean indicating the login status (null for server errors, false for credential mismatch)
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void handleLoginFailure(HttpServletRequest request, HttpServletResponse response, Boolean loginStatus)
            throws ServletException, IOException {
        String errorMessage;
        if (loginStatus == null) {
            errorMessage = "Our server is under maintenance. Please try again later!";
        } else {
            errorMessage = "User credential mismatch. Please try again!";
        }
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}