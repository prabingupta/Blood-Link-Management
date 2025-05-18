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
import java.util.Date;

import com.blood.config.DbConfig;
import com.blood.model.BloodRequestModel;
import com.blood.model.BloodStockModel;

/**
 * Servlet implementation class DashBoardController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
public class DashBoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashBoardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection conn = DbConfig.getDbConnection()) {
			// Fetch total counts
			fetchTotalCounts(conn, request);
			
			// Fetch blood stock status
			fetchBloodStock(conn, request);
			
			// Fetch recent blood requests
			fetchRecentRequests(conn, request);
			
			request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Error loading dashboard data: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
		}
	}

	private void fetchTotalCounts(Connection conn, HttpServletRequest request) throws SQLException {
		// Total Donors
		try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Donor")) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				request.setAttribute("totalDonors", rs.getInt(1));
			}
		} catch (SQLException e) {
			request.setAttribute("totalDonors", 0);
		}

		// Total Patients
		try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM Patient")) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				request.setAttribute("totalPatients", rs.getInt(1));
			}
		} catch (SQLException e) {
			request.setAttribute("totalPatients", 0);
		}

		// Total Donations - Using BloodStock table
		try (PreparedStatement stmt = conn.prepareStatement("SELECT SUM(Units_Available) FROM BloodStock")) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				request.setAttribute("totalDonations", rs.getInt(1));
			}
		} catch (SQLException e) {
			request.setAttribute("totalDonations", 0);
		}

		// Pending Blood Requests
		try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM BloodRequest WHERE Status = 'Pending'")) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				request.setAttribute("pendingRequests", rs.getInt(1));
			}
		} catch (SQLException e) {
			request.setAttribute("pendingRequests", 0);
		}
	}

	private void fetchBloodStock(Connection conn, HttpServletRequest request) throws SQLException {
		List<BloodStockModel> bloodStockList = new ArrayList<>();
		String searchTerm = request.getParameter("search");
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT Blood_Group, SUM(Units_Available) as Units, MAX(Last_Updated) as Last_Updated ")
			   .append("FROM BloodStock ");
			
			if (searchTerm != null && !searchTerm.trim().isEmpty()) {
				sql.append("WHERE Blood_Group LIKE ? ");
			}
			
			sql.append("GROUP BY Blood_Group");
			
			try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
				if (searchTerm != null && !searchTerm.trim().isEmpty()) {
					stmt.setString(1, "%" + searchTerm.trim() + "%");
				}
				
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					BloodStockModel stock = new BloodStockModel(
						rs.getString("Blood_Group"),
						rs.getInt("Units"),
						rs.getTimestamp("Last_Updated")
					);
					bloodStockList.add(stock);
				}
			}
		} catch (SQLException e) {
			// If error occurs or no results found, check if we're searching
			if (searchTerm != null && !searchTerm.trim().isEmpty()) {
				// If searching, return empty list
				bloodStockList = new ArrayList<>();
			} else {
				// If not searching, show all blood groups with 0 units
				String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
				for (String group : bloodGroups) {
					BloodStockModel stock = new BloodStockModel(group, 0, new Date());
					bloodStockList.add(stock);
				}
			}
		}
		
		request.setAttribute("bloodStockList", bloodStockList);
		
		// Add search-related attributes for blood stock
		if (searchTerm != null && !searchTerm.trim().isEmpty()) {
			request.setAttribute("bloodStockSearchPerformed", true);
			request.setAttribute("bloodStockSearchCount", bloodStockList.size());
		}
	}

	private void fetchRecentRequests(Connection conn, HttpServletRequest request) throws SQLException {
		List<BloodRequestModel> recentRequests = new ArrayList<>();
		
		String searchTerm = request.getParameter("search");
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		
		sqlBuilder.append("SELECT * FROM BloodRequest");
		
		if (searchTerm != null && !searchTerm.trim().isEmpty()) {
			sqlBuilder.append(" WHERE (CAST(Request_ID AS CHAR) LIKE ? OR ")
					  .append("CAST(Patient_ID AS CHAR) LIKE ? OR ")
					  .append("Blood_Group LIKE ? OR ")
					  .append("Status LIKE ?)");
			
			String searchPattern = "%" + searchTerm.trim() + "%";
			params.add(searchPattern);
			params.add(searchPattern);
			params.add(searchPattern);
			params.add(searchPattern);
		}
		
		sqlBuilder.append(" ORDER BY Request_Date DESC");
		
		// If no search term, limit to 5 results
		if (searchTerm == null || searchTerm.trim().isEmpty()) {
			sqlBuilder.append(" LIMIT 5");
		}
		
		try (PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
			// Set parameters if search term exists
			for (int i = 0; i < params.size(); i++) {
				stmt.setString(i + 1, (String) params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BloodRequestModel bloodRequest = new BloodRequestModel();
				bloodRequest.setRequestId(rs.getInt("Request_ID"));
				bloodRequest.setPatientId(rs.getInt("Patient_ID"));
				bloodRequest.setBloodGroup(rs.getString("Blood_Group"));
				bloodRequest.setUnitRequested(rs.getInt("Unit_Requested"));
				bloodRequest.setRequestDate(rs.getDate("Request_Date"));
				bloodRequest.setStatus(rs.getString("Status"));
				recentRequests.add(bloodRequest);
			}
		}
		
		request.setAttribute("recentRequests", recentRequests);
		
		// Add search-related attributes
		if (searchTerm != null && !searchTerm.trim().isEmpty()) {
			request.setAttribute("searchPerformed", true);
			request.setAttribute("searchResultCount", recentRequests.size());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
