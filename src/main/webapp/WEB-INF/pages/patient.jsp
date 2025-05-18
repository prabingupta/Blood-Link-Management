<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Register Patient</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/header.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/patient.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/footer.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="content">
        <div class="patient-section">
            <h2>Register Patient</h2>
            <c:if test="${not empty successMessage}">
                <div class="message success-message">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="message error-message">${errorMessage}</div>
            </c:if>
            <div class="form-section">
                <h3>Patient Information</h3>
                <form action="${contextPath}/patient" method="post">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="patientName">Patient Name *</label>
                            <input type="text" id="patientName" name="patientName" value="${param.patientName}" required placeholder="Enter patient name">
                            <c:if test="${not empty patientNameError}">
                                <span class="error">${patientNameError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="username">Username *</label>
                            <input type="text" id="username" name="username" value="${param.username}" required placeholder="Choose a username">
                            <c:if test="${not empty usernameError}">
                                <span class="error">${usernameError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="email">Email *</label>
                            <input type="email" id="email" name="email" value="${param.email}" required placeholder="Enter your email">
                            <c:if test="${not empty emailError}">
                                <span class="error">${emailError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone *</label>
                            <input type="tel" id="phone" name="phone" value="${param.phone}" required placeholder="Enter phone number">
                            <c:if test="${not empty phoneError}">
                                <span class="error">${phoneError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="password">Password *</label>
                            <input type="password" id="password" name="password" required placeholder="Choose a password">
                            <c:if test="${not empty passwordError}">
                                <span class="error">${passwordError}</span>
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
                            <label for="gender">Gender *</label>
                            <select id="gender" name="gender" required>
                                <option value="" disabled ${empty param.gender ? 'selected' : ''}>Select Gender</option>
                                <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                                <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                                <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Other</option>
                            </select>
                            <c:if test="${not empty genderError}">
                                <span class="error">${genderError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="dateOfBirth">Date of Birth *</label>
                            <input type="date" id="dateOfBirth" name="dateOfBirth" value="${param.dateOfBirth}" required>
                            <c:if test="${not empty dateOfBirthError}">
                                <span class="error">${dateOfBirthError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="requestDate">Request Date *</label>
                            <input type="date" id="requestDate" name="requestDate" value="${param.requestDate}" required>
                            <c:if test="${not empty requestDateError}">
                                <span class="error">${requestDateError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="requiredUnit">Required Units *</label>
                            <input type="number" id="requiredUnit" name="requiredUnit" value="${param.requiredUnit}" required placeholder="Enter required units" min="1">
                            <c:if test="${not empty requiredUnitError}">
                                <span class="error">${requiredUnitError}</span>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="submit-btn">Register Patient</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html> 