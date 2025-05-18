package com.blood.model;

import java.util.Date;

public class DonationModel {
    private int donationId;
    private int donorId;
    private Date donationDate;
    private int bloodBankId;
    private String bloodGroup;
    private int unitDonated;

    public DonationModel() {
    }

    public DonationModel(int donorId, Date donationDate, int bloodBankId, String bloodGroup, int unitDonated) {
        this.donorId = donorId;
        this.donationDate = donationDate;
        this.bloodBankId = bloodBankId;
        this.bloodGroup = bloodGroup;
        this.unitDonated = unitDonated;
    }

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public int getBloodBankId() {
        return bloodBankId;
    }

    public void setBloodBankId(int bloodBankId) {
        this.bloodBankId = bloodBankId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getUnitDonated() {
        return unitDonated;
    }

    public void setUnitDonated(int unitDonated) {
        this.unitDonated = unitDonated;
    }
}