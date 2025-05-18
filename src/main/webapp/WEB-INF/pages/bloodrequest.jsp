<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Request Blood</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/header.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bloodrequest.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/footer.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="content">
        <div class="bloodrequest-section">
            <h2>Request Blood</h2>
            <c:if test="${not empty successMessage}">
                <div class="message success-message">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="message error-message">${errorMessage}</div>
            </c:if>
            <div class="form-section">
                <h3>Submit Blood Request</h3>
               <form action="${pageContext.request.contextPath}/request" method="post">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="patientId">Patient ID *</label>
                            <input type="text" id="patientId" name="patientId" value="${param.patientId}" required placeholder="Enter patient ID">
                            <c:if test="${not empty patientIdError}">
                                <span class="error">${patientIdError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="bloodBankId">Blood Bank ID *</label>
                            <input type="text" id="bloodBankId" name="bloodBankId" value="${param.bloodBankId}" required placeholder="Enter blood bank ID">
                            <c:if test="${not empty bloodBankIdError}">
                                <span class="error">${bloodBankIdError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="bloodGroup">Blood Group *</label>
                            <select id="bloodGroup" name="bloodGroup" required>
                                <option value="" disabled ${empty param.bloodGroup ? 'selected' : ''}>Select Blood Group</option>
                                <option value="A+" ${param.bloodGroup == 'A+' ? 'selected' : ''}>A+</option>
                                <option value="A-" ${param.bloodGroup == 'A-' ? 'selected' : ''}>A-</option>
                                <option value="B+" ${param.bloodGroup == 'B+' ? 'selected' : ''}>B+</option>
                                <option value="B-" ${param.bloodGroup == 'B-' ? 'selected' : ''}>B-</option>
                                <option value="O+" ${param.bloodGroup == 'O+' ? 'selected' : ''}>O+</option>
                                <option value="O-" ${param.bloodGroup == 'O-' ? 'selected' : ''}>O-</option>
                                <option value="AB+" ${param.bloodGroup == 'AB+' ? 'selected' : ''}>AB+</option>
                                <option value="AB-" ${param.bloodGroup == 'AB-' ? 'selected' : ''}>AB-</option>
                            </select>
                            <c:if test="${not empty bloodGroupError}">
                                <span class="error">${bloodGroupError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="unitRequested">Units Requested *</label>
                            <input type="number" id="unitRequested" name="unitRequested" value="${param.unitRequested}" required placeholder="Enter units needed" min="1">
                            <c:if test="${not empty unitRequestedError}">
                                <span class="error">${unitRequestedError}</span>
                            </c:if>
                        </div>
                        <div class="form-group full-width">
                            <label for="requestDate">Request Date *</label>
                            <input type="date" id="requestDate" name="requestDate" value="${param.requestDate}" required>
                            <c:if test="${not empty requestDateError}">
                                <span class="error">${requestDateError}</span>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="submit-btn">Submit Request</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>