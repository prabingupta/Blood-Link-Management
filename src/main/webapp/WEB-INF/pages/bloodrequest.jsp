<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Blood Requests</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bloodrequest.css" />
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="content">
        <div class="bloodrequest-section">
            <h2>Active Blood Requests</h2>
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
                    <c:forEach var="request" items="${bloodRequestList}">
                        <c:if test="${request.status == 'Pending'}">
                            <tr>
                                <td>${request.requestId}</td>
                                <td>${request.patientId}</td>
                                <td>${request.bloodGroup}</td>
                                <td>${request.unitRequired}</td>
                                <td>${request.requestDate}</td>
                                <td>${request.status}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    <c:if test="${empty bloodRequestList}">
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
            <div class="form-section">
                <h3>Request Blood</h3>
                <form action="${contextPath}/submitBloodRequest" method="post">
                    <div class="form-group">
                        <label for="patientId">Patient ID:</label>
                        <input type="text" id="patientId" name="patientId" required placeholder="Enter patient ID">
                    </div>
                    <div class="form-group">
                        <label for="bloodGroup">Blood Group:</label>
                        <select id="bloodGroup" name="bloodGroup" required>
                            <option value="" disabled selected>Select blood group</option>
                            <option value="A+">A+</option>
                            <option value="A-">A-</option>
                            <option value="B+">B+</option>
                            <option value="B-">B-</option>
                            <option value="O+">O+</option>
                            <option value="O-">O-</option>
                            <option value="AB+">AB+</option>
                            <option value="AB-">AB-</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="unitRequired">Units Required:</label>
                        <input type="number" id="unitRequired" name="unitRequired" required placeholder="Enter units needed" min="1">
                    </div>
                    <div class="form-group">
                        <label for="requestDate">Request Date:</label>
                        <input type="date" id="requestDate" name="requestDate" required>
                    </div>
                    <button type="submit" class="submit-btn">Submit Request</button>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>