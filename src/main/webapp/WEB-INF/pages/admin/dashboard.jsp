<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                <li><a href="${contextPath}/dashboard"><span class="icon">🏠</span> Admin Dashboard</a></li>
                <li><a href="${contextPath}/managedonor"><span class="icon">👤</span> Manage Donor</a></li>
                <li><a href="${contextPath}/managepatient"><span class="icon">🩺</span> Manage Patient</a></li>
                <li><a href="${contextPath}/managerequest"><span class="icon">📋</span> Manage Blood Requests</a></li>
                <li><a href="${contextPath}/manageblood"><span class="icon">🏢</span> Manage Blood</a></li>
            </ul>
        </div>

        <div class="content">
            <!-- Dashboard Overview Section -->
            <div class="dashboard-section">
                <h2>Admin Dashboard</h2>
                <div class="overview">
                    <div class="card">
                        <h3>Total Donors</h3>
                        <p class="count">${totalDonors != null ? totalDonors : 150}</p>
                        <a href="${contextPath}/donors" class="view-link">View Donors</a>
                    </div>
                    <div class="card">
                        <h3>Total Patients</h3>
                        <p class="count">${totalPatients != null ? totalPatients : 80}</p>
                        <a href="${contextPath}/patients" class="view-link">View Patients</a>
                    </div>
                    <div class="card">
                        <h3>Total Donations</h3>
                        <p class="count">${totalDonations != null ? totalDonations : 200}</p>
                        <a href="${contextPath}/donations" class="view-link">View Donations</a>
                    </div>
                    <div class="card">
                        <h3>Pending Blood Requests</h3>
                        <p class="count">${pendingRequests != null ? pendingRequests : 10}</p>
                        <a href="${contextPath}/bloodRequests" class="view-link">View Requests</a>
                    </div>
                </div>
                <!-- Blood Stock Overview -->
                <div class="stock-overview">
                    <h3>Blood Stock Status</h3>
                    <table class="stock-table">
                        <thead>
                            <tr>
                                <th>Blood Group</th>
                                <th>Units Available</th>
                                <th>Last Updated</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="stock" items="${bloodStockList}">
                                <tr>
                                    <td>${stock.bloodGroup}</td>
                                    <td>${stock.unitsAvailable}</td>
                                    <td>${stock.lastUpdated}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty bloodStockList}">
                                <tr>
                                    <td>A+</td>
                                    <td>10</td>
                                    <td>2025-05-02</td>
                                </tr>
                                <tr>
                                    <td>B+</td>
                                    <td>15</td>
                                    <td>2025-05-02</td>
                                </tr>
                                <tr>
                                    <td>O+</td>
                                    <td>8</td>
                                    <td>2025-05-02</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                    <a href="${contextPath}/bloodBanks" class="view-link">Manage Blood Stock</a>
                </div>
                <!-- Recent Blood Requests -->
                <div class="recent-requests">
                    <h3>Recent Blood Requests</h3>
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
                            <c:forEach var="request" items="${recentRequests}">
                                <tr>
                                    <td>${request.requestId}</td>
                                    <td>${request.patientId}</td>
                                    <td>${request.bloodGroup}</td>
                                    <td>${request.unitRequested}</td>
                                    <td>${request.requestDate}</td>
                                    <td>${request.status}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty recentRequests}">
                                <tr>
                                    <td>1</td>
                                    <td>1</td>
                                    <td>A+</td>
                                    <td>2</td>
                                    <td>2025-05-01</td>
                                    <td>Pending</td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>2</td>
                                    <td>O+</td>
                                    <td>1</td>
                                    <td>2025-05-02</td>
                                    <td>Pending</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                    <a href="${contextPath}/bloodRequests" class="view-link">View All Requests</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>