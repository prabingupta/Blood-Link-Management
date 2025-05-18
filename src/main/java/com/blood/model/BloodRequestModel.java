package com.blood.model;

import java.util.Date;

public class BloodRequestModel {
    private int requestId;
    private int patientId;
    private int bloodBankId;
    private Date requestDate;
    private String bloodGroup;
    private int unitRequested;
    private String status;

    public BloodRequestModel() {
    }

    public BloodRequestModel(int patientId, int bloodBankId, Date requestDate, String bloodGroup, int unitRequested, String status) {
        this.patientId = patientId;
        this.bloodBankId = bloodBankId;
        this.requestDate = requestDate;
        this.bloodGroup = bloodGroup;
        this.unitRequested = unitRequested;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getBloodBankId() {
        return bloodBankId;
    }

    public void setBloodBankId(int bloodBankId) {
        this.bloodBankId = bloodBankId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getUnitRequested() {
        return unitRequested;
    }

    public void setUnitRequested(int unitRequested) {
        this.unitRequested = unitRequested;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}