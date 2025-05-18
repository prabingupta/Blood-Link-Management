package com.blood.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.blood.util.CookieUtil;
import com.blood.util.SessionUtil;

/**
 * Servlet implementation class LogoutController
 * Handles user logout by clearing cookies and invalidating the session.
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles POST requests to log out the user.
     * Deletes the user token cookie and invalidates the session, then redirects to the login page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CookieUtil.deleteCookie(response, "userToken");
        SessionUtil.invalidateSession(request);
        response.sendRedirect(request.getContextPath() + "/login");
    }

    /**
     * Handles GET requests by delegating to doPost for logout functionality.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}