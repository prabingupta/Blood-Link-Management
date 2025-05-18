<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header>
  <nav class="navbar">
    <div class="logo">
      <img src="${pageContext.request.contextPath}/resources/images/home/Logo-removebg-preview.png" alt="BloodLink Logo">
      <span>BloodLink Nepal</span>
    </div>
    
   

    <ul class="nav-links">
      <li><a href="${pageContext.request.contextPath}/home" class="active">Home</a></li>
      <li><a href="${pageContext.request.contextPath}/about" class="active">About Us</a></li>
      <li><a href="${pageContext.request.contextPath}/contact" class="active">Contact Us</a></li>
      <li><a href="${pageContext.request.contextPath}/services" class="active">Our Services</a></li>
      <li><a href="${pageContext.request.contextPath}/portfolio" class="active">Portfolio</a></li>
      <li><a href="${pageContext.request.contextPath}/profile" class="active"> 👤 UserProfile</a></li>
      
      
    </ul>
    <div class="login-btn">
      <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
  </nav>
</header>