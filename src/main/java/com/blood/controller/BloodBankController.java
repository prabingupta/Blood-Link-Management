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
        try (Connection conn = DbConfig.getDbConnection()) {
            String query = "SELECT * FROM Blood_Bank";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BloodBankModel bloodBank = new BloodBankModel();
                bloodBank.setBloodBankId(rs.getInt("bloodBankId"));
                bloodBank.setBloodBankName(rs.getString("bloodBankName"));
                bloodBank.setAddress(rs.getString("address"));
                bloodBank.setEmail(rs.getString("email"));
                bloodBank.setPhone(rs.getString("phone"));
                bloodBankList.add(bloodBank);
            }

            rs.close();
            stmt.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Error here");
            e.printStackTrace();
            request.setAttribute("errorMessage", "JDBC Driver not found: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        } catch (SQLException e) {
            System.out.println("Error here 2");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Set the bloodBankList attribute for the JSP
        request.setAttribute("bloodBankList", bloodBankList);
        request.getRequestDispatcher("/WEB-INF/pages/bloodbank.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Since the JSP only displays data, POST handling is not required.
        doGet(request, response);
    }
}