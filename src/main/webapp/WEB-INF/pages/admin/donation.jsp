<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Donations</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/donation.css" />
</head>
<body>
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
                <li><a href="${contextPath}/donors"><span class="icon">👤</span> Donor</a></li>
                <li><a href="${contextPath}/patients"><span class="icon">🩺</span> Patient</a></li>
                <li><a href="${contextPath}/donations"><span class="icon">💉</span> Donations</a></li>
                <li><a href="${contextPath}/bloodRequests"><span class="icon">📋</span> Blood Requests</a></li>
                <li><a href="${contextPath}/bloodBanks"><span class="icon">🏢</span> Blood Bank</a></li>
            </ul>
        </div>

        <div class="content">
            <div class="donation-section">
                <h2>Manage Donations</h2>
                <div class="action-bar">
                    <a href="${contextPath}/donationRegistration" class="add-btn">Add New Donation</a>
                </div>
                <table class="donation-table">
                    <thead>
                        <tr>
                            <th>Donation ID</th>
                            <th>Donor ID</th>
                            <th>Donation Date</th>
                            <th>Blood Group</th>
                            <th>Units Donated</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="donation" items="${donationList}">
                            <tr>
                                <td>${donation.donationId}</td>
                                <td>${donation.donorId}</td>
                                <td>${donation.donationDate}</td>
                                <td>${donation.bloodGroup}</td>
                                <td>${donation.unitDonated}</td>
                                <td>
                                    <a href="${contextPath}/editDonation?id=${donation.donationId}" class="action-btn edit-btn">Edit</a>
                                    <a href="${contextPath}/deleteDonation?id=${donation.donationId}" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this donation?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty donationList}">
                            <tr>
                                <td>1</td>
                                <td>1</td>
                                <td>2025-05-01</td>
                                <td>A+</td>
                                <td>2</td>
                                <td>
                                    <a href="${contextPath}/editDonation?id=1" class="action-btn edit-btn">Edit</a>
                                    <a href="${contextPath}/deleteDonation?id=1" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this donation?')">Delete</a>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>