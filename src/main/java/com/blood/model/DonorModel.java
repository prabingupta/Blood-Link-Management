package com.blood.model;

public class DonorModel {
    private int donorId;
    private String donorName;
    private String bloodGroup;
    private String phone;
    private String email;
    private String address;
    private String gender;

    public DonorModel() {
    }

    public DonorModel(String donorName, String bloodGroup, String phone, String email, String address, String gender) {
        this.donorName = donorName;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
    }

    public int getDonorId() {
        return donorId; 
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId; 
    }

    public String getDonorName() {
        return donorName; 
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName; 
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}