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
            <h2>Blood Bank Information</h2>
            <div class="bank-details">
                <c:forEach var="bank" items="${bloodBankList}">
                    <div class="bank-card">
                        <h3>${bank.bloodBankName}</h3>
                        <p><strong>Address:</strong> ${bank.address}</p>
                        <p><strong>Email:</strong> ${bank.email}</p>
                        <p><strong>Phone:</strong> ${bank.phone}</p>
                    </div>
                </c:forEach>
                <c:if test="${empty bloodBankList}">
                    <div class="bank-card">
                        <h3>BloodLink Nepal Central</h3>
                        <p><strong>Address:</strong> Kathmandu, Nepal</p>
                        <p><strong>Email:</strong> info@bloodlinknepal.org</p>
                        <p><strong>Phone:</strong> +977-1-1234567</p>
                    </div>
                </c:if>
            </div>
            <div class="stock-section">
                <h3>Available Blood Stock</h3>
                <table class="stock-table">
                    <thead>
                        <tr>
                            <th>Blood Group</th>
                            <th>Units Available</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="request" items="${bloodRequestList}">
                            <c:if test="${request.status == 'Pending'}">
                                <tr>
                                    <td>${request.bloodGroup}</td>
                                    <td>${request.unitRequired}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <c:if test="${empty bloodRequestList}">
                            <tr>
                                <td>A+</td>
                                <td>10</td>
                            </tr>
                            <tr>
                                <td>O+</td>
                                <td>15</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>