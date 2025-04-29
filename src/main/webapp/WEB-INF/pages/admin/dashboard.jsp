<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Admin Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/dashboard.css" />
</head>
<body>
    <!-- Admin Heading Bar -->
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
                <li><a href="${contextPath}/requestHistory"><span class="icon">📜</span> Request History</a></li>
                <li><a href="${contextPath}/bloodStock"><span class="icon">🩺</span> Blood Stock</a></li>
                <li><a href="${contextPath}/bloodStock"><span class="icon">👤</span> My Profile</a></li>
            </ul>
        </div>

        <div class="content">
            <!-- Welcome Section -->
            <div class="welcome-section">
                <h2>Welcome to Admin Dashboard</h2>
                <p>Hello, Admin! You are at the heart of BloodLink Nepal, a platform dedicated to saving lives by connecting blood donors with those in need. As an admin, you have the critical responsibility of managing the entire blood donation ecosystem, ensuring that blood is available for emergencies and planned medical procedures.</p>
                <p>Your role includes overseeing donor and patient registrations, approving blood donation requests, monitoring blood stock levels, and maintaining accurate records of all transactions. Use this dashboard to track key metrics, manage requests efficiently, and ensure that BloodLink Nepal continues to serve as a lifeline for the community. Your efforts make a real difference in saving lives!</p>
            </div>

            <div class="header">
                <div class="info-box">
                    <h3>A+</h3>
                    <p>10</p>
                </div>
                <div class="info-box">
                    <h3>B+</h3>
                    <p>15</p>
                </div>
                <div class="info-box">
                    <h3>O+</h3>
                    <p>12</p>
                </div>
                <div class="info-box">
                    <h3>AB+</h3>
                    <p>9</p>
                </div>
            </div>

            <div class="header">
                <div class="info-box">
                    <h3>A-</h3>
                    <p>8</p>
                </div>
                <div class="info-box">
                    <h3>B-</h3>
                    <p>10</p>
                </div>
                <div class="info-box">
                    <h3>O-</h3>
                    <p>6</p>
                </div>
                <div class="info-box">
                    <h3>AB-</h3>
                    <p>14</p>
                </div>
            </div>

            <div class="header">
                <div class="info-box">
                    <h3>Total Donors</h3>
                    <p>109</p>
                </div>
                <div class="info-box">
                    <h3>Total Requests</h3>
                    <p>20</p>
                </div>
                <div class="info-box">
                    <h3>Approved Requests</h3>
                    <p>8</p>
                </div>
                <div class="info-box">
                    <h3>Total Blood Unit in ml</h3>
                    <p>25 units</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>