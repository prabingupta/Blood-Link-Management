package com.blood.model;

import java.util.Date;

public class PatientModel {
    private int patientId;
    private String patientName;
    private String bloodGroup;
    private String gender;
    private Date dateOfBirth;
    private Date requestDate;
    private int requiredUnit;

    public PatientModel() {
    }

    public PatientModel(String patientName, String bloodGroup, String gender, Date dateOfBirth, Date requestDate, int requiredUnit) {
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.requestDate = requestDate;
        this.requiredUnit = requiredUnit;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public int getRequiredUnit() {
        return requiredUnit;
    }

    public void setRequiredUnit(int requiredUnit) {
        this.requiredUnit = requiredUnit;
    }
}