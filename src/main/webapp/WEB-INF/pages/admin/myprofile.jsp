<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - My Profile</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/myprofile.css" />
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
                <li><a href="${contextPath}/profile"><span class="icon">👤</span> My Profile</a></li>
            </ul>
        </div>

        <div class="content">
            <div class="profile-section">
                <h2>My Profile</h2>
                <div class="profile-container">
                    <div class="profile-image">
                        <img src="${contextPath}/resources/images/dashboard/prabin.jpg" alt="Prabin Kumar Gupta Profile">
                    </div>
                    <div class="profile-details">
                        <h3>Prabin Kumar Gupta</h3>
                        <p><strong>Email:</strong> ${empty user.email ? 'prabin.gupta@example.com' : user.email}</p>
                        <p><strong>Phone Number:</strong> ${empty user.phone ? '+977-9876543210' : user.phone}</p>
                        <p><strong>Blood Group:</strong> ${empty user.bloodGroup ? 'A+' : user.bloodGroup}</p>
                        <p><strong>User Type:</strong> ${empty user.userType ? 'Donor' : user.userType}</p>
                    </div>
                </div>

                <div class="profile-info">
                    <h4>About Me</h4>
                    <p>I am Prabin Kumar Gupta, a passionate individual committed to making a difference through BloodLink Nepal. As a regular blood donor, I believe in the power of community support to save lives. Beyond my contributions to blood donation, I am a tech enthusiast with a keen interest in web development and community service.</p>
                </div>

                <div class="profile-info">
                    <h4>Experience</h4>
                    <ul>
                        <li><strong>Blood Donor (2018 - Present):</strong> Actively participated in blood donation drives with BloodLink Nepal, contributing to over 15 donation events and helping save numerous lives.</li>
                        <li><strong>Web Developer (2020 - Present):</strong> Worked on multiple projects, including the development of BloodLink Nepal's platform, focusing on user-friendly interfaces and efficient backend systems.</li>
                        <li><strong>Community Volunteer (2017 - Present):</strong> Organized awareness campaigns for blood donation in local communities, collaborating with NGOs to promote the importance of regular donations.</li>
                    </ul>
                </div>

                <div class="profile-info">
                    <h4>Skills</h4>
                    <ul>
                        <li><strong>Web Development:</strong> Proficient in HTML, CSS, JavaScript, JSP, and Java for building dynamic websites.</li>
                        <li><strong>Database Management:</strong> Experienced in MySQL for managing donor and patient data securely.</li>
                        <li><strong>Community Engagement:</strong> Skilled in organizing events, raising awareness, and mobilizing volunteers for social causes.</li>
                        <li><strong>Communication:</strong> Effective communicator, adept at coordinating between donors, patients, and healthcare professionals.</li>
                    </ul>
                </div>

                <div class="profile-info">
                    <h4>Contributions to BloodLink Nepal</h4>
                    <p>I have donated blood 2 times through the platform, supported 5 emergency blood requests, and helped onboard 20 new donors by spreading awareness in my community. Additionally, I contributed to the development of the platform's registration and login features, ensuring a seamless user experience.</p>
                </div>

                <div class="profile-info">
                    <h4>Hobbies and Interests</h4>
                    <p>I enjoy coding, hiking, and reading about healthcare innovations. In my free time, I volunteer at local health camps and explore ways to leverage technology for social good.</p>
                </div>

                <div class="profile-info">
                    <h4>Contact Preferences</h4>
                    <p>I prefer to be contacted via email for donation requests or platform updates. For urgent matters, feel free to reach out via phone during working hours (9 AM - 5 PM).</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>