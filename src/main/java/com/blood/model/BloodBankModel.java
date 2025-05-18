package com.blood.model;

public class BloodBankModel {
    private int bloodBankId;
    private String bloodBankName;
    private String address;
    private String email;
    private String phone;

    public BloodBankModel() {
    }

    public BloodBankModel(String bloodBankName, String address, String email, String phone) {
        this.bloodBankName = bloodBankName;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public int getBloodBankId() {
        return bloodBankId;
    }

    public void setBloodBankId(int bloodBankId) {
        this.bloodBankId = bloodBankId;
    }

    public String getBloodBankName() {
        return bloodBankName;
    }

    public void setBloodBankName(String bloodBankName) {
        this.bloodBankName = bloodBankName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}