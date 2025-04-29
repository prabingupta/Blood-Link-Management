<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>BloodLink Nepal - About Us</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/aboutus.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>
  <%@ include file="header.jsp" %>

  <main class="about-section">
    <div class="container-box about-header">
      <h1>About BloodLink Nepal</h1>
      <p class="subtitle">Connecting Donors, Saving Lives – A Lifeline for Nepal</p>
    </div>

    <div class="container-box about-content">
      <p>BloodLink Nepal is a trusted platform dedicated to revolutionizing blood donation and management across Nepal. Established in 2015, we have been committed to addressing the critical need for a reliable blood supply system in a country where timely access to blood can mean the difference between life and death.</p>
      <p>Our platform seamlessly connects hospitals, blood banks, and voluntary donors, ensuring that patients in need receive safe and timely blood transfusions. Over the past decade, we have facilitated thousands of donations, saving countless lives and fostering a culture of voluntary blood donation.</p>
      <p>At BloodLink Nepal, our mission is to ensure that no patient suffers due to a lack of blood, and we strive to make blood donation a simple, accessible, and impactful act of humanity.</p>
    </div>

    <div class="mission-vision-values">
      <div class="container-box mission">
        <h2>Our Mission</h2>
        <p>We aim to create an efficient and accessible network for blood donation, linking hospitals, blood banks, and donors across Nepal.</p>
        <p>By streamlining the process, we ensure that blood is available to patients in critical need, saving lives through a reliable and transparent system.</p>
      </div>
      <div class="container-box vision">
        <h2>Our Vision</h2>
        <p>We envision a Nepal where every individual has access to safe blood during emergencies.</p>
        <p>By fostering a culture of voluntary blood donation, we aspire to eliminate blood shortages and build a robust blood donation ecosystem for future generations.</p>
      </div>
     
    </div>

    <div class="container-box achievements-section">
      <h2>Our Achievements</h2>
      <div class="achievements-grid">
        <div class="achievement-item">
          <h3>10,000+</h3>
          <p>Lives Saved</p>
        </div>
        <div class="achievement-item">
          <h3>500+</h3>
          <p>Blood Drives Organized</p>
        </div>
        <div class="achievement-item">
          <h3>50,000+</h3>
          <p>Registered Donors</p>
        </div>
      </div>
    </div>

    <div class="container-box team-section">
      <h2>Meet Our Team Members</h2>
      <p>Our dedicated team works tirelessly to ensure that blood reaches those in need.</p>
      <p>Get to know the passionate individuals driving this life-saving mission.</p>
      <div class="team-grid">
        <div class="team-member">
          <img src="${pageContext.request.contextPath}/resources/images/aboutus/prabin1.jpg" alt="Prabin Kumar Gupta">
          <h3>Prabin Kumar Gupta</h3>
          <p>Founder and CEO</p>
          <p class="team-desc">Prabin founded BloodLink Nepal with a vision to transform blood donation, making it a cornerstone of healthcare accessibility in Nepal.</p>
        </div>
        <div class="team-member">
          <img src="${pageContext.request.contextPath}/resources/images/aboutus/sudip.jpg" alt="Sudip Tamang">
          <h3>Sudip Tamang</h3>
          <p>Operations Head</p>
          <p class="team-desc">Sudip ensures the smooth execution of blood drives and coordinates efforts between donors and hospitals across the country.</p>
        </div>
        <div class="team-member">
          <img src="${pageContext.request.contextPath}/resources/images/aboutus/sameer.jpg" alt="Sameer Shrestha">
          <h3>Sameer Shrestha</h3>
          <p>Community Outreach Lead</p>
          <p class="team-desc">Sameer spearheads initiatives to raise awareness and encourage blood donation in communities throughout Nepal.</p>
        </div>
        <div class="team-member">
          <img src="${pageContext.request.contextPath}/resources/images/aboutus/Amod.jpg" alt="Dr. Amod Verma">
          <h3>Dr. Amod Verma</h3>
          <p>Medical Advisor</p>
          <p class="team-desc">Dr. Amod oversees medical protocols, ensuring the highest standards of safety and quality in blood donation processes.</p>
        </div>
      </div>
    </div>

    <div class="container-box cta-section">
      <h2>Join Our Life-Saving Mission</h2>
      <p>Be a part of BloodLink Nepal’s journey to ensure that no one in Nepal faces a blood shortage.</p>
      <p>Together, we can save more lives.</p>
      <a href="#" class="btn-action">Get Involved Today</a>
    </div>
  </main>

  <%@ include file="footer.jsp" %>
</body>
</html>