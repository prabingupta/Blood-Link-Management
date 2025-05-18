package com.blood.util;

import java.time.LocalDate;

import java.time.Period;
import java.util.regex.Pattern;


public class ValidationUtil {

    // 1. Validate if a field is null or empty
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // 2. Validate if the full name contains only letters and spaces (allowing for first and last names)
    public static boolean isValidFullName(String value) {
        return value != null && value.matches("^[a-zA-Z\\s]+$");
    }

    // 3. Validate if a username starts with a letter and is composed of letters and numbers
    public static boolean isValidUsername(String value) {
        return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]{4,}$");
    }

    // 4. Validate if a string is "Male", "Female", or "Other" (case insensitive)
    public static boolean isValidGender(String value) {
        return value != null && (value.equalsIgnoreCase("Male") || value.equalsIgnoreCase("Female") || value.equalsIgnoreCase("Other"));
    }

    // 5. Validate if a string is a valid email address
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    // 6. Validate if a phone number is of 10 digits and starts with 98 (Nepal mobile number format)
    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.matches("^98\\d{8}$");
    }

    // 7. Validate if a password is at least 8 characters long, with at least 1 capital letter, 1 number, and 1 symbol
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && Pattern.matches(passwordRegex, password);
    }

    // 8. Validate if password and retype password match
    public static boolean doPasswordsMatch(String password, String retypePassword) {
        return password != null && password.equals(retypePassword);
    }

    // 9. Validate if the date of birth is at least 18 years before today (for donors/patients)
    public static boolean isAgeAtLeast18(LocalDate dob) {
        if (dob == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return Period.between(dob, today).getYears() >= 18;
    }

    // 10. Validate if the blood group is one of the valid options (A+, A-, B+, B-, AB+, AB-, O+, O-)
    public static boolean isValidBloodGroup(String bloodGroup) {
        if (bloodGroup == null) {
            return false;
        }
        String[] validBloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String validGroup : validBloodGroups) {
            if (bloodGroup.equalsIgnoreCase(validGroup)) {
                return true;
            }
        }
        return false;
    }

    // 11. Validate if the user type is either "Donor" or "Patient" (case insensitive)
    public static boolean isValidUserType(String userType) {
        return userType != null && (userType.equalsIgnoreCase("Donor") || userType.equalsIgnoreCase("Patient"));
    }

    // 12. Validate if the terms checkbox is checked (value should be "on")
    public static boolean isTermsAccepted(String terms) {
        return "on".equals(terms);
    }
}