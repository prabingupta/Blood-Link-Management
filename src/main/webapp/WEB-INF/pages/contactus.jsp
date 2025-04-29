<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>BloodLink Nepal - Contact Us</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/contactus.css">
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>
  <%@ include file="header.jsp" %>

  <main class="contact-section">
    <div class="container-box contact-header">
      <h1>Contact BloodLink Nepal</h1>
      <p class="subtitle">We’re here to assist you with all your blood donation needs. Whether you have questions, need support, or want to get involved, reach out to us today!</p>
    </div>

    <div class="contact-container">
      <div class="container-box contact-info">
        <h2>Get in Touch</h2>
        <p>Our team is dedicated to providing prompt assistance for donors, patients, and partners across Nepal.</p>
        <div class="contact-details">
          <p><strong>Address:</strong> BloodLink Nepal Headquarters, Thamel, Kathmandu, Nepal</p>
          <p><strong>Phone:</strong> +977-1-2345678</p>
          <p><strong>Emergency Hotline:</strong> +977-1-8765432</p>
          <p><strong>Email:</strong> info@bloodlinknepal.org</p>
          <p><strong>WhatsApp:</strong> +977-9876-543210</p>
        </div>
        <div class="social-links">
          <h3>Follow Us</h3>
          <a href="https://facebook.com/bloodlinknepal" target="_blank">Facebook</a>
          <a href="https://twitter.com/bloodlinknepal" target="_blank">Twitter</a>
          <a href="https://instagram.com/bloodlinknepal" target="_blank">Instagram</a>
        </div>
      </div>

      <div class="container-box contact-form">
        <h2>Send Us a Message</h2>
        <p>Fill out the form below, and we’ll get back to you as soon as possible.</p>
        <form action="<%= request.getContextPath() %>/ContactServlet" method="post">
          <div class="form-group">
            <label for="name">Full Name:</label>
            <input type="text" id="name" name="name" placeholder="Enter your full name" required>
          </div>
          <div class="form-group">
            <label for="email">Email Address:</label>
            <input type="email" id="email" name="email" placeholder="Enter your email address" required>
          </div>
          <div class="form-group">
            <label for="phone">Phone Number (Optional):</label>
            <input type="tel" id="phone" name="phone" placeholder="Enter your phone number">
          </div>
          <div class="form-group">
            <label for="message">Your Message:</label>
            <textarea id="message" name="message" rows="6" placeholder="Write your message here" required></textarea>
          </div>
          <button type="submit">Send Message</button>
        </form>
      </div>
    </div>

    <div class="container-box contact-map">
      <h2>Find Us</h2>
      <p>Visit our headquarters in Kathmandu or connect with one of our regional offices across Nepal.</p>
      <div class="map-embed">
        <iframe
          src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3532.318111829143!2d85.31013931506216!3d27.717245982785257!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x39eb191f4f4d2d8f%3A0x9d6e5e5e5e5e5e5e!2sThamel%2C%20Kathmandu%2044600%2C%20Nepal!5e0!3m2!1sen!2sus!4v1698771234567!5m2!1sen!2sus"
          width="100%"
          height="400"
          style="border:0;"
          allowfullscreen
          loading="lazy"
          referrerpolicy="no-referrer-when-downgrade">
        </iframe>
      </div>
    </div>

    <div class="container-box cta-section">
      <h2>Join Our Mission</h2>
      <p>Be a part of BloodLink Nepal’s life-saving efforts. Whether you’re a donor, patient, or supporter, your involvement can make a difference!</p>
      <a href="<%= request.getContextPath() %>/register" class="btn-action">Get Involved</a>
    </div>
  </main>

  <%@ include file="footer.jsp" %>
</body>
</html>