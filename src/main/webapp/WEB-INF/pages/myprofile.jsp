<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BloodLink Nepal - My Profile</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/header.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/myprofile.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/footer.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <main class="profile-section">
        <div class="container-box profile-header">
            <h1>My Profile</h1>
            <p>Enter and update your personal information to stay connected with BloodLink Nepal. Your details will be stored securely for future use.</p>
        </div>

        <!-- Profile Picture Section -->
        <div class="container-box profile-picture">
            <h2>Profile Picture</h2>
            <p>We only support PNG or JPG pictures.</p>
            <div class="message-container"></div>
            <div class="image-wrapper">
                <div class="image-placeholder hidden">
                    <svg viewBox="0 0 24 24" fill="none" stroke="#666" stroke-width="2">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                </div>
                <img src="${contextPath}/${user.profilePicture != null && !empty user.profilePicture ? user.profilePicture : 'resources/images/pages/default.jpg'}" alt="Profile Picture" class="profile-img" data-default="${contextPath}/resources/images/pages/default.jpg">
            </div>
            <form id="uploadForm" enctype="multipart/form-data" class="upload-form">
                <input type="hidden" name="userId" value="${user.userId}" />
                <div class="form-group file-input-wrapper">
                    <label for="profilePicture" class="file-input-label">Choose File</label>
                    <input type="file" id="profilePicture" name="profilePicture" accept="image/png,image/jpeg" />
                    <span id="file-name" class="file-name">No file chosen</span>
                </div>
                <div class="photo-actions">
                    <button type="submit" class="btn-action upload-btn">Upload Your Photo</button>
                    <button type="button" class="btn-secondary delete-btn" onclick="deleteProfilePicture(${user.userId})">Delete Image</button>
                </div>
            </form>
        </div>

        <!-- Information Update/Edit Section -->
        <div class="container-box info-edit">
            <h2>Update Information</h2>
            <form id="editForm" class="edit-form">
                <input type="hidden" name="userId" value="${user.userId}" />
                <div class="form-group">
                    <label for="fullName">Full Name:</label>
                    <input type="text" id="fullName" name="fullName" value="${user.fullName}" required />
                    <span class="error" id="fullNameError"></span>
                </div>
                <div class="form-group">
                    <label for="email">Email Address:</label>
                    <input type="email" id="email" name="email" value="${user.email}" required />
                    <span class="error" id="emailError"></span>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="text" id="phone" name="phone" value="${user.phone}" required />
                    <span class="error" id="phoneError"></span>
                </div>
                <div class="form-group">
                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address" value="${user.address}" required />
                    <span class="error" id="addressError"></span>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" placeholder="Enter new password (leave blank to keep current)" />
                    <span class="error" id="passwordError"></span>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn-action">Save Changes</button>
                    <button type="reset" class="btn-secondary">Cancel</button>
                </div>
            </form>
        </div>
    </main>

    <%@ include file="footer.jsp" %>

    <script>
        let isDeleting = false;
        const defaultImage = document.querySelector('.profile-img').dataset.default;
        const imgElement = document.querySelector('.profile-img');
        const placeholderElement = document.querySelector('.image-placeholder');

        // Toggle between image and placeholder
        function toggleImageDisplay(showImage) {
            if (showImage && imgElement.src !== defaultImage) {
                imgElement.classList.remove('hidden');
                placeholderElement.classList.add('hidden');
            } else {
                imgElement.classList.add('hidden');
                placeholderElement.classList.remove('hidden');
            }
        }

        // Initial check
        toggleImageDisplay(imgElement.src !== defaultImage);

        // Show message
        function showMessage(message, isError = false) {
            const container = document.querySelector('.message-container');
            container.innerHTML = `<p class="${isError ? 'error-message' : 'success-message'}">${message}</p>`;
            setTimeout(() => container.innerHTML = '', 5000);
        }

        // Update file name display
        document.getElementById('profilePicture').addEventListener('change', function() {
            const fileName = this.files.length ? this.files[0].name : 'No file chosen';
            document.getElementById('file-name').textContent = fileName;
        });

        // Delete profile picture
        async function deleteProfilePicture(userId) {
            if (isDeleting) return;
            if (!confirm('Are you sure you want to delete your profile picture?')) return;

            isDeleting = true;
            try {
                const response = await fetch('${contextPath}/deleteProfilePicture', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'userId=' + userId
                });

                const data = await response.json();
                if (data.success) {
                    const img = new Image();
                    img.src = defaultImage;
                    img.onload = () => {
                        imgElement.src = defaultImage;
                        toggleImageDisplay(false);
                        showMessage('Profile picture deleted successfully!');
                    };
                } else {
                    showMessage('Failed to delete profile picture: ' + (data.message || 'Unknown error'), true);
                }
            } catch (error) {
                console.error('Error:', error);
                showMessage('An error occurred while deleting the profile picture.', true);
            } finally {
                isDeleting = false;
            }
        }

        // Handle upload form submission
        document.getElementById('uploadForm').addEventListener('submit', async (event) => {
            event.preventDefault();
            const fileInput = document.getElementById('profilePicture');
            if (!fileInput.files.length) {
                showMessage('Please select an image to upload.', true);
                return;
            }

            const formData = new FormData(document.getElementById('uploadForm'));
            try {
                const response = await fetch('${contextPath}/updateProfile', {
                    method: 'POST',
                    body: formData
                });

                const data = await response.json();
                if (data.success) {
                    const img = new Image();
                    img.src = '${contextPath}/' + data.profilePicture;
                    img.onload = () => {
                        imgElement.src = img.src;
                        toggleImageDisplay(true);
                        showMessage('Profile picture uploaded successfully!');
                    };
                } else {
                    showMessage(data.message || 'Failed to upload profile picture.', true);
                }
            } catch (error) {
                console.error('Error:', error);
                showMessage('An error occurred while uploading the profile picture.', true);
            }
        });

        // Handle edit form submission
        document.getElementById('editForm').addEventListener('submit', async (event) => {
            event.preventDefault();
            const formData = new FormData(document.getElementById('editForm'));
            const errors = document.querySelectorAll('.edit-form .error');
            errors.forEach(error => error.textContent = '');

            try {
                const response = await fetch('${contextPath}/updateProfile', {
                    method: 'POST',
                    body: new URLSearchParams(formData).toString(),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                });

                const data = await response.json();
                if (data.success) {
                    showMessage('Profile updated successfully!');
                } else {
                    if (data.errors) {
                        for (const [field, error] of Object.entries(data.errors)) {
                            document.getElementById(`${field}Error`).textContent = error;
                        }
                    } else {
                        showMessage(data.message || 'Failed to update profile.', true);
                    }
                }
            } catch (error) {
                console.error('Error:', error);
                showMessage('An error occurred while updating the profile.', true);
            }
        });
    </script>
</body>
</html>