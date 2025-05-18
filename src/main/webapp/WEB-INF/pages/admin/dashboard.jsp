<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Admin Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/dashboard.css" />
</head>
<body>
    <!-- Admin Heading Bar -->
    <header class="admin-header">
        <div class="header-title">
            <span>BloodLink Nepal</span>
        </div>
        <div class="logout">
            <form action="${contextPath}/logout" method="post">
                <button type="submit" class="logout-btn">Logout <span class="logout-icon">👤</span></button>
            </form>
        </div>
    </header>

    <div class="container">
        <div class="sidebar">
            <ul class="nav">
                <li><a href="${contextPath}/dashboard" class="active"><span class="icon">🏠</span> Admin Dashboard</a></li>
                <li><a href="${contextPath}/managedonor"><span class="icon">👤</span> Manage Donor</a></li>
              
                <li><a href="${contextPath}/managerequest"><span class="icon">📋</span> Manage Blood Requests</a></li>
                <li><a href="${contextPath}/manageblood"><span class="icon">🏢</span> Manage Blood</a></li>
            </ul>
        </div>

        <div class="content">
            <!-- Dashboard Overview Section -->
            <div class="dashboard-section">
                <h2>Admin Dashboard</h2>
                
                <c:if test="${not empty errorMessage}">
                    <div class="error-message">
                        ${errorMessage}
                    </div>
                </c:if>

                <div class="overview">
                    <div class="card">
                        <h3>Total Donors</h3>
                        <p class="count">${totalDonors != null ? totalDonors : 0}</p>
                        <a href="${contextPath}/managedonor" class="view-link">View Donors</a>
                    </div>
                    <div class="card">
                        <h3>Total Patients</h3>
                        <p class="count">${totalPatients != null ? totalPatients : 0}</p>
                        <a href="${contextPath}/managepatient" class="view-link">View Patients</a>
                    </div>
                    <div class="card">
                        <h3>Total Donations</h3>
                        <p class="count">${totalDonations != null ? totalDonations : 0}</p>
                        <a href="${contextPath}/manageblood" class="view-link">View Donations</a>
                    </div>
                    <div class="card">
                        <h3>Pending Blood Requests</h3>
                        <p class="count">${pendingRequests != null ? pendingRequests : 0}</p>
                        <a href="${contextPath}/managerequest" class="view-link">View Requests</a>
                    </div>
                </div>
                <!-- Blood Stock Overview -->
                <div class="stock-overview">
                    <div class="header-with-search">
                        <h3>
                            <c:choose>
                                <c:when test="${bloodStockSearchPerformed}">
                                    Blood Stock Results (${bloodStockSearchCount} found)
                                </c:when>
                                <c:otherwise>
                                    Blood Stock Status
                                </c:otherwise>
                            </c:choose>
                        </h3>
                        <div class="search-section">
                            <form action="${contextPath}/dashboard" method="get" class="search-form">
                                <input type="text" name="search" placeholder="Search by blood group (e.g., A+, B-)" 
                                       value="${param.search}" class="search-input">
                                <button type="submit" class="search-btn">Search</button>
                                <c:if test="${not empty param.search}">
                                    <a href="${contextPath}/dashboard" class="clear-search">Clear</a>
                                </c:if>
                            </form>
                        </div>
                    </div>
                    
                    <c:if test="${bloodStockSearchPerformed && empty bloodStockList}">
                        <div class="no-results">
                            No blood stock found matching your search criteria.
                        </div>
                    </c:if>
                    
                    <table class="stock-table">
                        <thead>
                            <tr>
                                <th>Blood Group</th>
                                <th>Units Available</th>
                                <th>Last Updated</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty bloodStockList}">
                                    <c:forEach var="stock" items="${bloodStockList}">
                                        <tr>
                                            <td>${stock.bloodGroup}</td>
                                            <td>${stock.unitsAvailable}</td>
                                            <td><fmt:formatDate value="${stock.lastUpdated}" pattern="yyyy-MM-dd"/></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="3" class="no-data">No blood stock data available</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <a href="${contextPath}/manageblood" class="view-link">Manage Blood Stock</a>
                </div>
                <!-- Recent Blood Requests -->
                <div class="recent-requests">
                    <h3>
                        <c:choose>
                            <c:when test="${searchPerformed}">
                                Search Results (${searchResultCount} found)
                            </c:when>
                            <c:otherwise>
                                Recent Blood Requests
                            </c:otherwise>
                        </c:choose>
                    </h3>
                    
                    <c:if test="${searchPerformed && empty recentRequests}">
                        <div class="no-results">
                            No blood requests found matching your search criteria.
                        </div>
                    </c:if>
                    
                    <table class="request-table">
                        <thead>
                            <tr>
                                <th>Request ID</th>
                                <th>Patient ID</th>
                                <th>Blood Group</th>
                                <th>Units Required</th>
                                <th>Request Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty recentRequests}">
                                    <c:forEach var="request" items="${recentRequests}">
                                        <tr>
                                            <td>${request.requestId}</td>
                                            <td>${request.patientId}</td>
                                            <td>${request.bloodGroup}</td>
                                            <td>${request.unitRequested}</td>
                                            <td><fmt:formatDate value="${request.requestDate}" pattern="yyyy-MM-dd"/></td>
                                            <td>
                                                <span class="status-${fn:toLowerCase(request.status)}">${request.status}</span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6" class="no-data">No recent blood requests</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <a href="${contextPath}/managerequest" class="view-link">View All Requests</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>