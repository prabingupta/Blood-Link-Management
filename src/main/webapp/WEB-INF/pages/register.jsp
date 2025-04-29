<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Registration</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/register.css">
</head>
<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/resources/images/register/Logo-removebg-preview.png" alt="BloodLink Nepal Logo">
         <span>BloodLink Nepal</span>
    </div>
    <nav>
        <a href="${pageContext.request.contextPath}/register">Register</a>
        <a href="${pageContext.request.contextPath}/login">Login</a>
    </nav>
</header>  

<img src="${pageContext.request.contextPath}/resources/images/register/bg.jpg" alt="Background Image" class="background-img">

<div class="registration-container">
    <div class="form-section">
        <h2>BloodLink Nepal Registration</h2>
        <p>Register as a Donor or Patient to Join Our Life-Saving Mission</p>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            String successMessage = (String) request.getAttribute("successMessage");
            if (errorMessage != null) {
        %>
            <p class="error-message"><%= errorMessage %></p>
        <% } else if (successMessage != null) { %>
            <p class="success-message"><%= successMessage %></p>
        <% } %>

        <form method="post" action="<%= request.getContextPath() %>/register">
            <div class="form-row">
                <div class="form-group">
                    <label for="fullName">Full Name:</label>
                    <input type="text" id="fullName" name="fullName" value="${param.fullName}" required>
                    <span class="error"><%= request.getAttribute("fullNameError") != null ? request.getAttribute("fullNameError") : "" %></span>
                </div>
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="${param.username}" required>
                    <span class="error"><%= request.getAttribute("usernameError") != null ? request.getAttribute("usernameError") : "" %></span>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${param.email}" required>
                    <span class="error"><%= request.getAttribute("emailError") != null ? request.getAttribute("emailError") : "" %></span>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="text" id="phone" name="phone" value="${param.phone}" required>
                    <span class="error"><%= request.getAttribute("phoneError") != null ? request.getAttribute("phoneError") : "" %></span>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="gender">Gender:</label>
                    <select id="gender" name="gender" required>
                        <option value="">Select Gender</option>
                        <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Other</option>
                    </select>
                    <span class="error"><%= request.getAttribute("genderError") != null ? request.getAttribute("genderError") : "" %></span>
                </div>
                <div class="form-group">
                    <label for="dob">Date of Birth:</label>
                    <input type="date" id="dob" name="dob" value="${param.dob}" required>
                    <span class="error"><%= request.getAttribute("dobError") != null ? request.getAttribute("dobError") : "" %></span>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="bloodGroup">Blood Group:</label>
                    <select id="bloodGroup" name="bloodGroup" required>
                        <option value="">Select Blood Group</option>
                        <option value="A+" ${param.bloodGroup == 'A+' ? 'selected' : ''}>A+</option>
                        <option value="A-" ${param.bloodGroup == 'A-' ? 'selected' : ''}>A-</option>
                        <option value="B+" ${param.bloodGroup == 'B+' ? 'selected' : ''}>B+</option>
                        <option value="B-" ${param.bloodGroup == 'B-' ? 'selected' : ''}>B-</option>
                        <option value="AB+" ${param.bloodGroup == 'AB+' ? 'selected' : ''}>AB+</option>
                        <option value="AB-" ${param.bloodGroup == 'AB-' ? 'selected' : ''}>AB-</option>
                        <option value="O+" ${param.bloodGroup == 'O+' ? 'selected' : ''}>O+</option>
                        <option value="O-" ${param.bloodGroup == 'O-' ? 'selected' : ''}>O-</option>
                    </select>
                    <span class="error"><%= request.getAttribute("bloodGroupError") != null ? request.getAttribute("bloodGroupError") : "" %></span>
                </div>
                <div class="form-group">
                    <label for="userType">User Type:</label>
                    <select id="userType" name="userType" required>
                        <option value="">Select Type</option>
                        <option value="Donor" ${param.userType == 'Donor' ? 'selected' : ''}>Donor</option>
                        <option value="Patient" ${param.userType == 'Patient' ? 'selected' : ''}>Patient</option>
                    </select>
                    <span class="error"><%= request.getAttribute("userTypeError") != null ? request.getAttribute("userTypeError") : "" %></span>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                    <span class="error"><%= request.getAttribute("passwordError") != null ? request.getAttribute("passwordError") : "" %></span>
                </div>
                <div class="form-group">
                    <label for="retypePassword">Retype Password:</label>
                    <input type="password" id="retypePassword" name="retypePassword" required>
                    <span class="error"><%= request.getAttribute("retypePasswordError") != null ? request.getAttribute("retypePasswordError") : "" %></span>
                </div>
            </div>

            <div class="form-group terms-container">
                <input type="checkbox" id="terms" name="terms" required>
                <label for="terms">I accept the <a href="#">Terms and Conditions</a> and <a href="#">Privacy Policy</a>.</label>
            </div>

            <p class="terms-text">Please take a moment to review our <a href="#">Terms and Conditions</a> and <a href="#">Privacy Policy</a>.</p>

            <button type="submit">Create Account</button>

            <div class="button-p">
                <h3>Already have an account? Please <a href="${pageContext.request.contextPath}/login">Sign In</a></h3>
            </div>
        </form>
    </div>
</div>

<footer>
    <p>@2025 BloodLink Nepal</p>
</footer>
</body>
</html>