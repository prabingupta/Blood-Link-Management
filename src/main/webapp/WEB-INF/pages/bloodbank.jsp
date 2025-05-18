<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Blood Banks</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/bloodbank.css" />
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="content">
        <div class="bloodbank-section">
            <h2 class="section-title">Blood Bank Management</h2>
            
            <c:if test="${not empty errorMessage}">
                <div class="error-message">
                    ${errorMessage}
                </div>
            </c:if>
            
            <c:if test="${not empty successMessage}">
                <div class="success-message">
                    ${successMessage}
                </div>
            </c:if>

            <div class="add-bank-section">
                <h3 class="section-title">Add New Blood Bank</h3>
                <form action="${contextPath}/bloodbank" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="bloodBankName">Blood Bank Name:</label>
                        <input type="text" id="bloodBankName" name="bloodBankName" required 
                               placeholder="Enter blood bank name">
                    </div>
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address" required 
                               placeholder="Enter complete address">
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required 
                               placeholder="Enter email address">
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone:</label>
                        <input type="tel" id="phone" name="phone" required 
                               placeholder="Enter phone number" pattern="[0-9+\-\s]{10,}">
                    </div>
                    <button type="submit" class="submit-btn">Add Blood Bank</button>
                </form>
            </div>

            <h3 class="section-title">Registered Blood Banks</h3>
            <div class="bank-details">
                <c:forEach var="bank" items="${bloodBankList}">
                    <div class="bank-card">
                        <h3>${bank.bloodBankName}</h3>
                        <p><strong>Address:</strong> ${bank.address}</p>
                        <p><strong>Email:</strong> ${bank.email}</p>
                        <p><strong>Phone:</strong> ${bank.phone}</p>
                        <div style="display: flex; gap: 10px; margin-top: 15px;">
                            <form action="${contextPath}/bloodbank" method="post" style="flex: 1;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="bloodBankId" value="${bank.bloodBankId}">
                                <button type="submit" class="submit-btn" 
                                        onclick="return confirm('Are you sure you want to delete this blood bank?')"
                                        style="background-color: #e74c3c;">Delete</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty bloodBankList}">
                    <div class="bank-card">
                        <h3>No Blood Banks Found</h3>
                        <p>Please add a new blood bank using the form above.</p>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>