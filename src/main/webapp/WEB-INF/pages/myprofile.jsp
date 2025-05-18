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
            <p>Update your personal information to stay connected with BloodLink Nepal.</p>
        </div>

        <!-- Profile Picture Section -->
        <div class="container-box profile-picture">
            <h2>Profile Picture</h2>
            <p>We only support PNG or JPG pictures.</p>
            <div class="message-container"></div>
            <div class="image-wrapper">
                <div class="image-placeholder hidden">
                    <svg viewBox="0 0 24 24" fill="none" stroke="#666" stroke-width="2">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                </div>
                <img src="${contextPath}/${sessionScope.profilePicture != null ? sessionScope.profilePicture : 'resources/images/pages/default.jpg'}" 
                     alt="Profile Picture" class="profile-img" data-default="${contextPath}/resources/images/pages/default.jpg">
            </div>
            <form id="uploadForm" enctype="multipart/form-data" class="upload-form">
                <input type="hidden" name="userId" value="${sessionScope.userId != null ? sessionScope.userId : '1'}" />
                <div class="form-group file-input-wrapper">
                    <label for="profilePicture" class="file-input-label">Choose File</label>
                    <input type="file" id="profilePicture" name="profilePicture" accept="image/png,image/jpeg" />
                    <span id="file-name" class="file-name">No file chosen</span>
                </div>
                <div class="photo-actions">
                    <button type="submit" class="btn-action upload-btn">Upload Your Photo</button>
                    <button type="button" class="btn-secondary delete-btn" onclick="deleteProfilePicture('${sessionScope.userId != null ? sessionScope.userId : '1'}')">Delete Image</button>
                </div>
            </form>
        </div>

        <!-- Information Update/Edit Section -->
        <div class="container-box info-edit">
            <h2>Update Information</h2>
            <form id="editForm" class="edit-form">
                <input type="hidden" name="userId" value="${sessionScope.userId != null ? sessionScope.userId : '1'}" />
                <div class="form-group">
                    <label for="fullName">Full Name:</label>
                    <input type="text" id="fullName" name="fullName" value="${sessionScope.fullName != null ? sessionScope.fullName : ''}" required />
                    <span class="error" id="fullNameError"></span>
                </div>
                <div class="form-group">
                    <label for="email">Email Address:</label>
                    <input type="email" id="email" name="email" value="${sessionScope.email != null ? sessionScope.email : ''}" required />
                    <span class="error" id="emailError"></span>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="text" id="phone" name="phone" value="${sessionScope.phone != null ? sessionScope.phone : ''}" required />
                    <span class="error" id="phoneError"></span>
                </div>
                <div class="form-group">
                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address" value="${sessionScope.address != null ? sessionScope.address : ''}" required />
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

        // Update file name display and preview image
        document.getElementById('profilePicture').addEventListener('change', function() {
            const fileName = this.files.length ? this.files[0].name : 'No file chosen';
            document.getElementById('file-name').textContent = fileName;
            if (this.files.length) {
                const file = this.files[0];
                const reader = new FileReader();
                reader.onload = () => {
                    imgElement.src = reader.result;
                    toggleImageDisplay(true);
                    console.log('Preview set for image: ' + fileName);
                };
                reader.onerror = () => console.error('Error reading file: ' + fileName);
                reader.readAsDataURL(file);
            }
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
                    body: 'userId=' + encodeURIComponent(userId)
                });

                const text = await response.text();
                console.log('Delete response: ' + text);
                const [status, message] = text.split(':');
                if (status === 'success') {
                    imgElement.src = defaultImage;
                    toggleImageDisplay(false);
                    showMessage(message);
                } else {
                    showMessage(message, true);
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

            showMessage('Uploading...', false);
            const formData = new FormData(document.getElementById('uploadForm'));
            try {
                const response = await fetch('${contextPath}/updateProfile', {
                    method: 'POST',
                    body: formData
                });

                const text = await response.text();
                console.log('Upload response: ' + text);
                const parts = text.split(':');
                const status = parts[0];
                const message = parts[1];
                const fileName = parts[2];

                if (status === 'success' && fileName) {
                    const newSrc = '${contextPath}/' + fileName;
                    imgElement.src = newSrc;
                    toggleImageDisplay(true);
                    showMessage(message);
                    // Verify image load
                    imgElement.onerror = () => {
                        console.error('Failed to load image: ' + newSrc);
                        showMessage('Image uploaded but failed to display.', true);
                        toggleImageDisplay(false);
                    };
                } else {
                    showMessage(message || 'Failed to upload profile picture.', true);
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

                const text = await response.text();
                console.log('Update response: ' + text);
                if (text.startsWith('success:')) {
                    showMessage(text.split(':')[1]);
                } else if (text.startsWith('error:')) {
                    const errorParts = text.substring(6).split(';').filter(e => e);
                    errorParts.forEach(part => {
                        const [field, error] = part.split('=');
                        document.getElementById(`${field}Error`).textContent = error;
                    });
                } else {
                    showMessage('Failed to update profile.', true);
                }
            } catch (error) {
                console.error('Error:', error);
                showMessage('An error occurred while updating the profile.', true);
            }
        });

        // Handle cancel button
        document.getElementById('editForm').addEventListener('reset', (event) => {
            const errors = document.querySelectorAll('.edit-form .error');
            errors.forEach(error => error.textContent = '');
            showMessage('Cancelled');
        });
    </script>
</body>
</html>