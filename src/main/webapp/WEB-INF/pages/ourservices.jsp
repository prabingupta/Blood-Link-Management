<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>BloodLink Nepal - Our Services</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/ourservices.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>
  <%@ include file="header.jsp" %>

  <main class="services-section">
    <div class="container-box services-header">
      <h1>Our Services</h1>
      <p>At BloodLink Nepal, we offer a comprehensive range of services to support blood donation, management, and emergency response across Nepal, ensuring a reliable and efficient blood supply for those in need.</p>
    </div>

    <div class="services-grid">
      <div class="container-box service-item">
        <img src="${pageContext.request.contextPath}/resources/images/services/BloodDrive.jpg" alt="Blood Donation Drives">
        <h2>Blood Donation Drives</h2>
        <p>We organize blood donation camps across cities and rural areas to encourage voluntary donations. These drives are conducted in collaboration with local communities, schools, and organizations to ensure a steady blood supply for hospitals and patients.</p>
      </div>
      <div class="container-box service-item">
        <img src="${pageContext.request.contextPath}/resources/images/services/BloodNetwork.jpg" alt="Blood Bank Network">
        <h2>Blood Bank Network</h2>
        <p>Our platform connects blood banks with hospitals to streamline blood distribution. We ensure efficient allocation of blood units, reducing shortages and enabling timely access to safe blood for patients in need.</p>
      </div>
      
      <div class="container-box service-item">
        <img src="${pageContext.request.contextPath}/resources/images/services/emergency.jpg" alt="Emergency Services">
        <h2>Emergency Services</h2>
        <p>We offer 24/7 support to connect donors with patients in critical need of blood transfusions. Our emergency response team ensures rapid coordination during life-threatening situations.</p>
      </div>
      <div class="container-box service-item">
        <img src="${pageContext.request.contextPath}/resources/images/services/bloodtest.jpg" alt="Blood Testing & Screening">
        <h2>Blood Testing and Screening</h2>
        <p>We ensure all donated blood undergoes rigorous testing and screening for safety. Our processes meet the highest medical standards, guaranteeing that every unit of blood is safe for transfusion.</p>
      </div>
      <div class="container-box service-item">
        <img src="${pageContext.request.contextPath}/resources/images/services/bloodCamp.jpg" alt="Community Outreach">
        <h2>Community Outreach</h2>
        <p>We conduct awareness campaigns to educate the public about the importance of blood donation. Our outreach programs aim to dispel myths, encourage voluntary donations, and build a supportive community for blood donation.</p>
      </div>
    </div>

    <div class="container-box cta-section">
      <h2>Ready to Make a Difference?</h2>
      <p>Join us in our mission to save lives through blood donation. Whether you’re a donor, a hospital, or a community partner, your support can help ensure no one in Nepal faces a blood shortage.</p>
      <a href="#" class="btn-action">Get Involved Today</a>
    </div>
  </main>

  <%@ include file="footer.jsp" %>
</body>
</html>