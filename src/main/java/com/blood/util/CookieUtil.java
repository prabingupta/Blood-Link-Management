package com.blood.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

/**
 * Utility class for managing cookies in the BloodLink Nepal application.
 * Provides methods to add, retrieve, and delete cookies for session management.
 */
public class CookieUtil {

    /**
     * Adds a cookie with the specified name, value, and maximum age.
     * Used for storing user session data, such as roles, in the BloodLink Nepal application.
     *
     * @param response the HttpServletResponse to add the cookie to
     * @param name     the name of the cookie (e.g., "role")
     * @param value    the value of the cookie
     * @param maxAge   the maximum age of the cookie in seconds
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/"); // Make cookie available to the entire application
        cookie.setHttpOnly(true); // Enhance security by preventing client-side access
        response.addCookie(cookie);
    }

    /**
     * Retrieves a cookie by its name from the HttpServletRequest.
     * Used to check for existing user session cookies in the BloodLink Nepal application.
     *
     * @param request the HttpServletRequest to get the cookie from
     * @param name    the name of the cookie to retrieve
     * @return the Cookie object if found, otherwise null
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Deletes a cookie by setting its max age to 0.
     * Used for logging out users or clearing session data in the BloodLink Nepal application.
     *
     * @param response the HttpServletResponse to add the deletion cookie to
     * @param name     the name of the cookie to delete
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/"); // Ensure the cookie is deleted across the application
        cookie.setHttpOnly(true); // Maintain security consistency
        response.addCookie(cookie);
    }
}