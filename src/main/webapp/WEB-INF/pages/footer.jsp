<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<footer>
  <div class="footer-content">
    <div class="footer-section">
      <h3>BloodLink Nepal</h3>
      <p>Connecting lives through blood donation. Registered with the Ministry of Health, Nepal.</p>
    </div>
    <div class="footer-section">
      <h3>Quick Links</h3>
      <ul>
      <li><a href="<%= request.getContextPath() %>/home" class="active">Home</a></li>
      <li><a href="<%= request.getContextPath() %>/about" class="active">ABOUT US</a></li>
      <li><a href="<%= request.getContextPath() %>/contact" class="active">CONTACT US</a></li>
      <li><a href="<%= request.getContextPath() %>/services" class="active">OUR SERVICES</a></li>
      <li><a href="<%= request.getContextPath() %>/portfolio" class="active">PORTFOLIO</a></li>
      </ul>
    </div>
    <div class="footer-section">
      <h3>Contact Us</h3>
      <p>Email: info@bloodlinknepal.org</p>
      <p>Phone: +977-1-2345678</p>
      <p>Address: Kathmandu, Nepal</p>
    </div>
    <div class="footer-section">
      <h3>Follow Us</h3>
      <p>Stay updated with our latest events and news.</p>
      <div class="social-links">
        <a href="#">Facebook</a> | <a href="#">Twitter</a> | <a href="#">Instagram</a>
      </div>
    </div>
  </div>
  <div class="footer-bottom">
    <p>© 2025 BloodLink Nepal. All rights reserved.</p>
  </div>
</footer>