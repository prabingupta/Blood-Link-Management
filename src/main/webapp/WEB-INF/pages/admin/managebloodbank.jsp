<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Manage Blood Bank</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bankdetail.css" />
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
                <li><a href="${contextPath}/managedonor"><span class="icon">👤</span> Manage Donor</a></li>
                <li><a href="${contextPath}/managepatient"><span class="icon">🩺</span> Manage Patient</a></li>
                <li><a href="${contextPath}/managerequest"><span class="icon">📋</span> Manage Blood Requests</a></li>
                <li><a href="${contextPath}/manageblood"><span class="icon">🏢</span> Manage Blood</a></li>
            </ul>
        </div>

        <div class="content">
            <h2>Manage Blood Bank</h2>
            <div class="bloodbank-form">
                <h3>Add New Blood Bank</h3>
                <form action="${contextPath}/addBloodBank" method="post">
                    <div class="form-group">
                        <label for="bloodBankName">Blood Bank Name:</label>
                        <input type="text" id="bloodBankName" name="bloodBankName" required>
                    </div>
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <textarea id="address" name="address" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone:</label>
                        <input type="text" id="phone" name="phone" required>
                    </div>
                    <button type="submit" class="submit-btn">Add Blood Bank</button>
                </form>
            </div>
            <div class="bloodbank-list">
                <h3>Blood Bank List</h3>
                <table class="bloodbank-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bloodBank" items="${bloodBankList}">
                            <tr>
                                <td>${bloodBank.bloodBankId}</td>
                                <td>${bloodBank.bloodBankName}</td>
                                <td>${bloodBank.address}</td>
                                <td>${bloodBank.email}</td>
                                <td>${bloodBank.phone}</td>
                                <td>
                                    <a href="${contextPath}/editBloodBank?id=${bloodBank.bloodBankId}" class="action-btn edit-btn">Edit</a>
                                    <a href="${contextPath}/deleteBloodBank?id=${bloodBank.bloodBankId}" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this blood bank?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty bloodBankList}">
                            <tr>
                                <td>1</td>
                                <td>Central Blood Bank</td>
                                <td>Kathmandu</td>
                                <td>central@bloodbank.com</td>
                                <td>1234567890</td>
                                <td>
                                    <a href="#" class="action-btn edit-btn">Edit</a>
                                    <a href="#" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this blood bank?')">Delete</a>
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