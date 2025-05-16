<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Patients</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/patient.css" />
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
            <div class="patient-section">
                <h2>Manage Patients</h2>
                <div class="action-bar">
                    <a href="${contextPath}/patientRegistration" class="add-btn">Add New Patient</a>
                </div>
                <table class="patient-table">
                    <thead>
                        <tr>
                            <th>Patient ID</th>
                            <th>Patient Name</th>
                            <th>Blood Group</th>
                            <th>Gender</th>
                            <th>Date of Birth</th>
                            <th>Request Date</th>
                            <th>Units Required</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="patient" items="${patientList}">
                            <tr>
                                <td>${patient.patientId}</td>
                                <td>${patient.patientName}</td>
                                <td>${patient.bloodGroup}</td>
                                <td>${patient.gender}</td>
                                <td>${patient.dateOfBirth}</td>
                                <td>${patient.requestDate}</td>
                                <td>${patient.unitRequired}</td>
                                <td>
                                    <a href="${contextPath}/editPatient?id=${patient.patientId}" class="action-btn edit-btn">Edit</a>
                                    <a href="${contextPath}/deletePatient?id=${patient.patientId}" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this patient?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty patientList}">
                            <tr>
                                <td>1</td>
                                <td>John Doe</td>
                                <td>A+</td>
                                <td>Male</td>
                                <td>1990-05-15</td>
                                <td>2025-05-01</td>
                                <td>2</td>
                                <td>
                                    <a href="${contextPath}/editPatient?id=1" class="action-btn edit-btn">Edit</a>
                                    <a href="${contextPath}/deletePatient?id=1" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this patient?')">Delete</a>
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