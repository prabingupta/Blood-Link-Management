package com.blood.model;

import java.time.LocalDate;

public class DonorModel {

    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String gender;
    private String bloodGroup;
    private String email;
    private String phoneNumber;
    private LocalDate lastDonationDate;

    public DonorModel() {
    }

    public DonorModel(int id, String firstName, String lastName, LocalDate dob, String gender, String bloodGroup,
                      String email, String phoneNumber, LocalDate lastDonationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastDonationDate = lastDonationDate;
    }

    public DonorModel(String firstName, String lastName, LocalDate dob, String gender, String bloodGroup,
                      String email, String phoneNumber, LocalDate lastDonationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastDonationDate = lastDonationDate;
    }

    public DonorModel(int id, String firstName, String lastName, String bloodGroup, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bloodGroup = bloodGroup;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public DonorModel(int id, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
}