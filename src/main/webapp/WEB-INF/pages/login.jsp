<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blood Bank Login</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/login.css">
</head>
<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/resources/images/login/Logo-removebg-preview.png" alt="Logo Image">
         <span>BloodLink Nepal</span>
    </div>
    <nav>
        <a href="${pageContext.request.contextPath}/register">Register</a>
        <a href="${pageContext.request.contextPath}/login">Login</a>
        <div class="menu-icon">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </nav>
</header>   

<img src="${pageContext.request.contextPath}/resources/images/login/bg.jpg" alt="Background Image" class="background-img">

<div class="registration-container">
    <div class="form-section">
        <h2>Blood Bank Login</h2>
        <p>Access your account as Donor, Patient, or Admin</p>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            String successMessage = (String) request.getAttribute("successMessage");
            if (errorMessage != null) {
        %>
            <p class="error-message"><%= errorMessage %></p>
        <% } else if (successMessage != null) { %>
            <p class="success-message"><%= successMessage %></p>
        <% } %>

        <form method="post" action="<%= request.getContextPath() %>/login">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${param.username}" required>
                <span class="error"><%= request.getAttribute("usernameError") != null ? request.getAttribute("usernameError") : "" %></span>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <span class="error"><%= request.getAttribute("passwordError") != null ? request.getAttribute("passwordError") : "" %></span>
            </div>
            <button type="submit">Login</button>

            <div class="button-p">
                <h3>Don't have an account? <a href="<%= request.getContextPath() %>/register">Create an Account</a> Now!</h3>
                
            </div>
        </form>
    </div>
</div>
<footer>
    <p>@2025 Blood Bank Company</p>
</footer>
</body>
</html>