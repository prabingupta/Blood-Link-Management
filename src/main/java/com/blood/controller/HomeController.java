package com.blood.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = { "/home"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HomeController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            request.setAttribute("errorMessage", "Please log in to access the homepage.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        String userType = (String) session.getAttribute("userType");
        if ("Admin".equalsIgnoreCase(userType)) {
            response.sendRedirect(request.getContextPath() + "/adminDashboard");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}