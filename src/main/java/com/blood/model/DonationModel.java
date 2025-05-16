package com.blood.model;

import java.time.LocalDate;

public class DonationModel {
	
	private int donationId;
    private int donorId;
    private LocalDate donationDate;
    private String bloodGroup;
    private int unitDonated;

    public DonationModel() {
    }

    public DonationModel(int donorId, LocalDate donationDate, String bloodGroup, int unitDonated) {
        this.donorId = donorId;
        this.donationDate = donationDate;
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

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
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


