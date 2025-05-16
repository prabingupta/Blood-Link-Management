<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Become a Donor</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/donor.css" />
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="content">
        <div class="donor-section">
            <h2>Become a Donor</h2>
            <div class="info">
                <h3>Eligibility Criteria</h3>
                <ul>
                    <li>Age: 18-65 years</li>
                    <li>Weight: Minimum 50 kg</li>
                    <li>Healthy with no chronic illnesses</li>
                    <li>No recent tattoos or piercings (within 6 months)</li>
                </ul>
                <h3>Donor Statistics</h3>
                <p>Total Donors: ${totalDonors != null ? totalDonors : 150}</p>
            </div>
            <div class="form-section">
                <h3>Register as a Donor</h3>
                <form action="${contextPath}/registerDonor" method="post">
                    <div class="form-group">
                        <label for="donorName">Name:</label>
                        <input type="text" id="donorName" name="donorName" required placeholder="Enter your name">
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
                        <label for="phone">Phone:</label>
                        <input type="tel" id="phone" name="phone" required placeholder="Enter phone number">
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required placeholder="Enter email">
                    </div>
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address" required placeholder="Enter address">
                    </div>
                    <div class="form-group">
                        <label for="gender">Gender:</label>
                        <select id="gender" name="gender" required>
                            <option value="" disabled selected>Select gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                    <button type="submit" class="submit-btn">Register</button>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>