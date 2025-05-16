<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header>
  <nav class="navbar">
    <div class="logo">
      <img src="${pageContext.request.contextPath}/resources/images/home/Logo-removebg-preview.png" alt="BloodLink Logo">
      <span>BloodLink Nepal</span>
    </div>
    
    <ul class="nav-links">
      <li><a href="<%= request.getContextPath() %>/home" class="active">Home</a></li>
      <li><a href="<%= request.getContextPath() %>/about" class="active">About Us</a></li>
      <li><a href="<%= request.getContextPath() %>/contact" class="active">Contact Us</a></li>
      <li><a href="<%= request.getContextPath() %>/services" class="active">Our Services</a></li>
      <li><a href="<%= request.getContextPath() %>/portfolio" class="active">Portfolio</a></li>
      <li><a href="<%= request.getContextPath() %>/myprofile" class="active">MyProfile</a></li>
    </ul>
    <div class="login-btn">
      <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
  </nav>
 
</header>