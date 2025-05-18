<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - Manage Blood Bank</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/managebloodbank.css" />
</head>
<body>
    <header class="admin-header">
        <div class="header-title">
            <span>BloodLink Nepal</span>
        </div>
        <div class="logout">
            <form action="${contextPath}/logout" method="post">
                <button type="submit" class="logout-btn">Logout <span class="logout-icon">👤</span></button>
            </form>
        </div>
    </header>

    <div class="container">
        <div class="sidebar">
            <ul class="nav">
                <li><a href="${contextPath}/dashboard"><span class="icon">🏠</span> Admin Dashboard</a></li>
                <li><a href="${contextPath}/managedonor"><span class="icon">👤</span> Manage Donor</a></li>
                <li><a href="${contextPath}/managerequest"><span class="icon">📋</span> Manage Blood Requests</a></li>
                <li><a href="${contextPath}/manageblood"><span class="icon">🏢</span> Manage Blood</a></li>
            </ul>
        </div>

        <div class="content">
            <!-- Messages -->
            <c:if test="${not empty successMessage}">
                <div class="message success">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="message error">${errorMessage}</div>
            </c:if>

            <h2>Manage Blood Bank</h2>
            <div class="bloodbank-form">
                <h3 id="formTitle">Add New Blood Bank</h3>
                <form id="bloodBankForm" action="${contextPath}/manageblood" method="post">
                    <input type="hidden" name="action" value="add" id="formAction">
                    <input type="hidden" name="bloodBankId" id="bloodBankId">
                    <div class="form-group">
                        <label for="bloodBankName">Blood Bank Name:</label>
                        <input type="text" id="bloodBankName" name="bloodBankName" required>
                    </div>
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <textarea id="address" name="address" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone:</label>
                        <input type="text" id="phone" name="phone" required pattern="[0-9+\-\s]{10,}">
                    </div>
                    <div class="button-group">
                        <button type="submit" class="submit-btn" id="submitBtn">Add Blood Bank</button>
                        <button type="button" class="cancel-btn" id="cancelBtn" style="display: none;" onclick="resetForm()">Cancel</button>
                    </div>
                </form>
            </div>

            <div class="bloodbank-list">
                <h3>Blood Bank List</h3>
                <table class="bloodbank-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bloodBank" items="${bloodBankList}">
                            <tr>
                                <td>${bloodBank.bloodBankId}</td>
                                <td>${bloodBank.bloodBankName}</td>
                                <td>${bloodBank.address}</td>
                                <td>${bloodBank.email}</td>
                                <td>${bloodBank.phone}</td>
                                <td>
                                    <button onclick="editBloodBank(${bloodBank.bloodBankId}, '${bloodBank.bloodBankName}', '${bloodBank.address}', '${bloodBank.email}', '${bloodBank.phone}')" class="action-btn edit-btn">Edit</button>
                                    <a href="${contextPath}/manageblood?action=delete&id=${bloodBank.bloodBankId}" 
                                       class="action-btn delete-btn"
                                       onclick="return confirm('Are you sure you want to delete this blood bank?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        // Function to safely escape HTML to prevent XSS
        function escapeHtml(unsafe) {
            return unsafe
                .replace(/&/g, "&amp;")
                .replace(/</g, "&lt;")
                .replace(/>/g, "&gt;")
                .replace(/"/g, "&quot;")
                .replace(/'/g, "&#039;");
        }

        function editBloodBank(id, name, address, email, phone) {
            // Update form title and action
            document.getElementById('formTitle').textContent = 'Edit Blood Bank';
            document.getElementById('formAction').value = 'update';
            document.getElementById('submitBtn').textContent = 'Update Blood Bank';
            
            // Show cancel button
            document.getElementById('cancelBtn').style.display = 'inline-block';
            
            // Set form values
            document.getElementById('bloodBankId').value = id;
            document.getElementById('bloodBankName').value = decodeURIComponent(name);
            document.getElementById('address').value = decodeURIComponent(address);
            document.getElementById('email').value = decodeURIComponent(email);
            document.getElementById('phone').value = decodeURIComponent(phone);
            
            // Scroll to form
            document.querySelector('.bloodbank-form').scrollIntoView({ behavior: 'smooth' });
        }

        function resetForm() {
            // Reset form title and action
            document.getElementById('formTitle').textContent = 'Add New Blood Bank';
            document.getElementById('formAction').value = 'add';
            document.getElementById('submitBtn').textContent = 'Add Blood Bank';
            
            // Hide cancel button
            document.getElementById('cancelBtn').style.display = 'none';
            
            // Clear form
            document.getElementById('bloodBankForm').reset();
            document.getElementById('bloodBankId').value = '';
        }

        // Auto-hide messages after 5 seconds
        setTimeout(function() {
            var messages = document.getElementsByClassName('message');
            for(var i = 0; i < messages.length; i++) {
                messages[i].style.display = 'none';
            }
        }, 5000);
    </script>
</body>
</html>