<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Blood Banks</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bloodbank.css" />
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
            <div class="bloodbank-section">
                <h2>Manage Blood Banks</h2>
                <div class="action-bar">
                    <a href="${contextPath}/bloodBankRegistration" class="add-btn">Add New Blood Bank</a>
                </div>
                <table class="bloodbank-table">
                    <thead>
                        <tr>
                            <th>Blood Bank ID</th>
                            <th>Blood Bank Name</th>
                            <th>Address</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bank" items="${bloodBankList}">
                            <tr>
                                <td>${bank.bloodBankId}</td>
                                <td>${bank.bloodBankName}</td>
                                <td>${bank.address}</td>
                                <td>${bank.email}</td>
                                <td>${bank.phone}</td>
                                <td>
                                    <a href="${contextPath}/editBloodBank?id=${bank.bloodBankId}" class="action-btn edit-btn">Edit</a>
                                    <a href="${contextPath}/deleteBloodBank?id=${bank.bloodBankId}" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this blood bank?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty bloodBankList}">
                            <tr>
                                <td>1</td>
                                <td>BloodLink Nepal Central</td>
                                <td>Kathmandu, Nepal</td>
                                <td>info@bloodlinknepal.org</td>
                                <td>+977-1-1234567</td>
                                <td>
                                    <a href="${contextPath}/editBloodBank?id=1" class="action-btn edit-btn">Edit</a>
                                    <a href="${contextPath}/deleteBloodBank?id=1" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this blood bank?')">Delete</a>
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