<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Manage Blood Requests</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/managerequest.css" />
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
                <li><a href="${contextPath}/managerequest"><span class="icon">📋</span> Blood Requests</a></li>
                <li><a href="${contextPath}/manageblood"><span class="icon">🏢</span> Manage Blood</a></li>
            </ul>
        </div>

        <div class="content">
            <!-- Messages -->
            <c:if test="${not empty successMessage}">
                <div class="message success">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="message error">${errorMessage}</div>
            </c:if>

            <h2>Manage Blood Requests</h2>
            <div class="request-list">
                <h3>Blood Request List</h3>
                <table class="request-table">
                    <thead>
                        <tr>
                            <th>Request ID</th>
                            <th>Patient ID</th>
                            <th>Blood Bank ID</th>
                            <th>Request Date</th>
                            <th>Blood Group</th>
                            <th>Units Requested</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="request" items="${requestList}">
                            <tr>
                                <td>${request.requestId}</td>
                                <td>${request.patientId}</td>
                                <td>${request.bloodBankId}</td>
                                <td>${request.requestDate}</td>
                                <td>${request.bloodGroup}</td>
                                <td>${request.unitRequested}</td>
                                <td>${request.status}</td>
                                <td>
                                    <c:if test="${request.status == 'Pending'}">
                                        <form action="${contextPath}/approveRequest" method="post" style="display: inline;">
                                            <input type="hidden" name="id" value="${request.requestId}">
                                            <button type="submit" class="action-btn approve-btn">Approve</button>
                                        </form>
                                        <form action="${contextPath}/declineRequest" method="post" style="display: inline;">
                                            <input type="hidden" name="id" value="${request.requestId}">
                                            <button type="submit" class="action-btn decline-btn">Decline</button>
                                        </form>
                                    </c:if>
                                    <c:if test="${request.status != 'Pending'}">
                                        <span class="status-${fn:toLowerCase(request.status)}">${request.status}</span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        // Auto-hide messages after 5 seconds
        setTimeout(function() {
            var messages = document.getElementsByClassName('message');
            for(var i = 0; i < messages.length; i++) {
                messages[i].style.display = 'none';
            }
        }, 5000);
    </script>
</body>
</html>