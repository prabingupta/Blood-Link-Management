<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>BloodLink Nepal - Portfolio</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/portfolio.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>
  <%@ include file="header.jsp" %>

  <main class="portfolio-section">
    <div class="container-box portfolio-header">
      <h1>Our Portfolio</h1>
      <p>Explore the impactful initiatives and achievements of BloodLink Nepal in transforming blood donation across the country. From life-saving drives to community outreach, our work has made a difference in countless lives.</p>
    </div>

    <div class="portfolio-grid">
      <div class="container-box portfolio-item">
        <img src="${pageContext.request.contextPath}/resources/images/portfolio/ktmblood.jpg" alt="Kathmandu Blood Drive">
        <h2>Kathmandu Blood Drive 2024</h2>
        <p>In collaboration with local hospitals and volunteers, we collected 500 units of blood in a single day, supporting patients undergoing surgeries and emergencies across Kathmandu. This event marked a significant step in addressing blood shortages in urban areas.</p>
      </div>
      
      <div class="container-box portfolio-item">
        <img src="${pageContext.request.contextPath}/resources/images/portfolio/outreach.jpg" alt="Rural Outreach">
        <h2>Rural Outreach Program</h2>
        <p>Our rural outreach program brought blood donation camps to remote areas of Nepal, such as Dolakha and Gorkha. These camps collected over 300 units of blood and raised awareness about the importance of voluntary donations in underserved communities.</p>
      </div>
      <div class="container-box portfolio-item">
        <img src="${pageContext.request.contextPath}/resources/images/portfolio/donorrecog.jpg" alt="Donor Recognition">
        <h2>Donor Recognition Event 2023</h2>
        <p>We honored 100 regular donors for their selfless contributions at our annual Donor Recognition Event. This event celebrated the generosity of our donors and inspired others to join the life-saving mission of blood donation.</p>
      </div>
      <div class="container-box portfolio-item">
        <img src="${pageContext.request.contextPath}/resources/images/portfolio/campaign.jpg" alt="Youth Engagement">
        <h2>Youth Engagement Campaign</h2>
        <p>Through our Youth Engagement Campaign, we collaborated with universities to encourage young people to donate blood. Over 1,000 students participated, contributing to a growing culture of voluntary blood donation among Nepal’s youth.</p>
      </div>
      <div class="container-box portfolio-item">
        <img src="${pageContext.request.contextPath}/resources/images/portfolio/emergencyservices.jpg" alt="Emergency Response">
        <h2>Emergency Response Initiative</h2>
        <p>During a natural disaster in 2022, our Emergency Response Initiative mobilized donors to provide 200 units of blood to affected areas. This rapid response helped save lives during a critical time of need.</p>
      </div>
    </div>

    <div class="container-box milestones-section">
      <h2>Our Milestones</h2>
      <div class="timeline">
        <div class="timeline-item">
          <h3>2015</h3>
          <p>BloodLink Nepal was founded with a mission to address blood shortages in Nepal.</p>
        </div>
        <div class="timeline-item">
          <h3>2017</h3>
          <p>Organized our first major blood donation drive, collecting 200 units of blood.</p>
        </div>
        <div class="timeline-item">
          <h3>2019</h3>
          <p>Expanded our network to connect with 10 hospitals across the country.</p>
        </div>
        <div class="timeline-item">
          <h3>2021</h3>
          <p>Launched our Donor App, enabling thousands of donors to schedule and track donations.</p>
        </div>
        <div class="timeline-item">
          <h3>2024</h3>
          <p>Reached a milestone of saving 10,000 lives through our initiatives.</p>
        </div>
      </div>
    </div>

    <div class="container-box impact-section">
      <h2>Our Impact</h2>
      <div class="impact-grid">
        <div class="impact-item">
          <h3>10,000+</h3>
          <p>Lives Saved</p>
        </div>
        <div class="impact-item">
          <h3>1,000+</h3>
          <p>Blood Donation Camps</p>
        </div>
        <div class="impact-item">
          <h3>50,000+</h3>
          <p>Units of Blood Collected</p>
        </div>
        <div class="impact-item">
          <h3>30+</h3>
          <p>Hospital Partners</p>
        </div>
      </div>
    </div>

    <div class="container-box cta-section">
      <h2>Join Our Mission</h2>
      <p>Be a part of BloodLink Nepal’s journey to save lives. Your contribution can make a lasting impact on communities across Nepal.</p>
      <a href="#" class="btn-action">Get Involved Today</a>
    </div>
  </main>

  <%@ include file="footer.jsp" %>
</body>
</html>