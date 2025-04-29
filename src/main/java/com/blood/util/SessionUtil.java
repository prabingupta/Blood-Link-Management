package com.blood.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for managing HTTP sessions in the Blood Bank Management System.
 * Provides methods to set, get, remove session attributes, and invalidate sessions for user authentication and session tracking.
 */
public class SessionUtil {

    /**
     * Sets an attribute in the session.
     * Used to store user data, such as user ID or role, during authentication in the Blood Bank Management System.
     *
     * @param request the HttpServletRequest from which the session is obtained
     * @param key     the key under which the attribute is stored
     * @param value   the value of the attribute to store in the session
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession(true); // Create session if it doesn't exist
        session.setAttribute(key, value);
    }

    /**
     * Retrieves an attribute from the session.
     * Used to access user data, such as user ID or role, during the session in the Blood Bank Management System.
     *
     * @param request the HttpServletRequest from which the session is obtained
     * @param key     the key of the attribute to retrieve
     * @return the attribute value, or null if the attribute does not exist or the session is invalid
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false); // Don't create a new session
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    /**
     * Removes an attribute from the session.
     * Used to clear specific session data without invalidating the entire session in the Blood Bank Management System.
     *
     * @param request the HttpServletRequest from which the session is obtained
     * @param key     the key of the attribute to remove
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * Invalidates the current session.
     * Used for logging out users by clearing all session data in the Blood Bank Management System.
     *
     * @param request the HttpServletRequest from which the session is obtained
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}