<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>BloodLink Nepal - Home</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/home.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>
  <%@ include file="header.jsp" %>
  
  <section class="slider-section">
   
    <div class="slider">
      <div class="slides">
        <div class="slide">
          <img src="${pageContext.request.contextPath}/resources/images/home/donatingblood.jpg" alt="Slide 1">
          <div class="slide-content">
            <h2>Join Our Next Blood Drive!</h2>
            <p>April 25, 2025 in Kathmandu - Save Lives Today!</p>
            <a href="#" class="btn-action">Learn More</a>
          </div>
        </div>
        <div class="slide">
          <img src="${pageContext.request.contextPath}/resources/images/home/DonateBlood.jpg" alt="Slide 2">
          <div class="slide-content">
            <h2>Be a Hero - Donate Blood</h2>
            <p>Your donation can save up to 1000 lives!</p>
            <a href="#" class="btn-action">Register Now</a>
          </div>
        </div>
        <div class="slide">
          <img src="${pageContext.request.contextPath}/resources/images/home/BecomeDonor.jpg" alt="Slide 3">
          <div class="slide-content">
            <h2>Success Story: 1000+ Lives Saved</h2>
            <p>Thanks to our donors, we’ve made a difference!</p>
            <a href="#" class="btn-action">Read Stories</a>
          </div>
        </div>
      </div>
      <button class="prev" onclick="moveSlide(-1)">❮</button>
      <button class="next" onclick="moveSlide(1)">❯</button>
      <div class="dots">
        <span class="dot" onclick="currentSlide(0)"></span>
        <span class="dot" onclick="currentSlide(1)"></span>
        <span class="dot" onclick="currentSlide(2)"></span>
      </div>
    </div>
    <script>
      let slideIndex = 0;
      let slides = document.querySelectorAll('.slide');
      let dots = document.querySelectorAll('.dot');

      function showSlides(index) {
        if (index >= slides.length) slideIndex = 0;
        if (index < 0) slideIndex = slides.length - 1;
        slides.forEach(slide => slide.style.display = 'none');
        dots.forEach(dot => dot.classList.remove('active'));
        slides[slideIndex].style.display = 'flex';
        dots[slideIndex].classList.add('active');
      }

      function moveSlide(n) {
        slideIndex += n;
        showSlides(slideIndex);
      }

      function currentSlide(n) {
        slideIndex = n;
        showSlides(slideIndex);
      }

      setInterval(() => moveSlide(1), 5000);
      showSlides(slideIndex);
    </script>
  </section>

  <main class="banner-section" style="background: url('${pageContext.request.contextPath}/resources/images/home/background.jpg') no-repeat center center/cover;">
    <div class="banner-content">
      <h1 class="highlight-title">Be a Lifesaver Today!</h1>
      <p class="banner-desc">Join BloodLink Nepal to make a difference in Nepal’s healthcare system! Donate blood to save lives or request blood for those in urgent need, ensuring no patient goes without support during critical times.</p>
      <p>Up to 1000 lives saved monthly!</p>
      <a href="<%= request.getContextPath() %>/#" class="btn-action">Donate Now</a>
      <a href="<%= request.getContextPath() %>/#" class="btn-action">Request for Blood</a>
     
      
    </div>
    <div class="banner-side">
      <img src="${pageContext.request.contextPath}/resources/images/home/donatingblood.jpg" alt="Emergency Blood Donation">
      <p>Emergency Blood Donation</p>
    </div>
  </main>

  <section class="category-section">
    <h2 class="section-title">Explore Blood Donation Services</h2>
    <p class="section-desc">Discover how BloodLink Nepal empowers you to contribute to society. Register as a donor, request blood for patients, or learn about compatible blood types to help save lives across Nepal.</p>
    <div class="category-grid">
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/blood.jpg" alt="Donate Blood">
        <p>Donate Blood</p>
        <p>Register as a donor today</p>
      </div>
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/heartsymbol.jpg" alt="Request Blood">
        <p>Request Blood</p>
        <p>Find blood for patients in need</p>
      </div>
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/bloodbags.jpg" alt="Blood Types">
        <p>Blood Types</p>
        <p>Learn about compatible blood types</p>
      </div>
    </div>
  </section>

  <section class="category-section">
    <h2 class="section-title">Upcoming Blood Donation Events</h2>
    <p class="section-desc">Be part of life-changing blood drives with BloodLink Nepal! Join our upcoming events in cities like Kathmandu and Pokhara to donate blood and support Nepal’s growing need for blood donations.</p>
    <div class="category-grid">
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/eventsblood1.jpg" alt="Kathmandu Event">
        <p>Kathmandu Blood Drive</p>
        <p>April 25, 2025</p>
      </div>
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/bloodevents2.jpg" alt="Pokhara Event">
        <p>Pokhara Blood Drive</p>
        <p>May 1, 2025</p>
      </div>
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/bloodevents3.jpg" alt="Chitwan Event">
        <p>Chitwan Blood Drive</p>
        <p>May 5, 2025</p>
      </div>
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/bloodevents4.jpg" alt="Biratnagar Event">
        <p>Biratnagar Blood Drive</p>
        <p>May 10, 2025</p>
      </div>
    </div>
  </section>

  <section class="category-section">
    <h2 class="section-title">Success Stories</h2>
    <p class="section-desc">Read inspiring stories of lives saved through BloodLink Nepal. From emergency surgeries to rural outreach, see how donors and patients in Nepal are making a lasting impact.</p>
    <div class="category-grid">
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/child.jpg" alt="Story 1">
        <p>Saving a Child’s Life</p>
        <p>A+ blood donation success</p>
      </div>
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/surgery.jpg" alt="Story 2">
        <p>Emergency Surgery</p>
        <p>O- Blood saved a mother</p>
      </div>
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/donors.jpg" alt="Story 3">
        <p>Community Support</p>
        <p>50 Donors in one day</p>
      </div>
      <div class="category-item">
        <img src="${pageContext.request.contextPath}/resources/images/home/healthcheckup.jpg" alt="Story 4">
        <p>Rural Outreach</p>
        <p>AB+ blood for a farmer</p>
      </div>
    </div>
  </section>

  <%@ include file="footer.jsp" %>
</body>
</html>