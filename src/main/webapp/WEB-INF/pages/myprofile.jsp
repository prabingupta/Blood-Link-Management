<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - My Profile</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/header.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/myprofile.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/footer.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <main class="profile-section">
        <div class="container-box profile-header">
            <h1>My Profile</h1>
            <p>Enter and update your personal information to stay connected with BloodLink Nepal. Your details will be stored securely for future use.</p>
        </div>

        <!-- Profile Picture Section -->
        <div class="container-box profile-picture">
            <h2>Profile Picture</h2>
            <p>We only support PNG or JPG pictures.</p>
            <div class="image-wrapper">
                <img src="${contextPath}/resources/images/profile-placeholder.jpg" alt="Profile Picture" class="profile-img" onerror="this.src='${contextPath}/resources/images/default-profile.jpg';">
            </div>
            <div class="photo-actions">
                <button class="btn-action upload-btn">Upload Your Photo</button>
                <button class="btn-secondary delete-btn">Delete Image</button>
            </div>
        </div>

        <!-- Information Update/Edit Section -->
        <div class="container-box info-edit">
            <h2>Update Information</h2>
            <form action="${contextPath}/updateProfile" method="post" class="edit-form">
                <input type="hidden" name="userId" value="${user.userId}" />
                <div class="form-group">
                    <label for="fullName">Full Name:</label>
                    <input type="text" id="fullName" name="fullName" value="${user.fullName}" required />
                </div>
                <div class="form-group">
                    <label for="email">Email Address:</label>
                    <input type="email" id="email" name="email" value="${user.email}" required />
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="text" id="phone" name="phone" value="${user.phone}" required />
                </div>
                <div class="form-group">
                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address" value="${user.address}" required />
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" placeholder="Enter new password (leave blank to keep current)" />
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn-action">Save Changes</button>
                    <button type="reset" class="btn-secondary">Cancel</button>
                </div>
            </form>
        </div>
    </main>

    <%@ include file="footer.jsp" %>
</body>
</html>