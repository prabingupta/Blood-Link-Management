package com.blood.model;

import java.time.LocalDate;

public class PatientModel {

	 private int patientId;
	    private String patientName;
	    private String bloodGroup;
	    private String gender;
	    private LocalDate dateOfBirth;
	    private String requestDate;
	    private int unitRequired;

	    public PatientModel() {
	    }

	    public PatientModel(String patientName, String bloodGroup, String gender, LocalDate dateOfBirth, String requestDate, int unitRequired) {
	        this.patientName = patientName;
	        this.bloodGroup = bloodGroup;
	        this.gender = gender;
	        this.dateOfBirth = dateOfBirth;
	        this.requestDate = requestDate;
	        this.unitRequired = unitRequired;
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

	    public LocalDate getDateOfBirth() {
	        return dateOfBirth;
	    }

	    public void setDateOfBirth(LocalDate dateOfBirth) {
	        this.dateOfBirth = dateOfBirth;
	    }

	    public String getRequestDate() {
	        return requestDate;
	    }

	    public void setRequestDate(String requestDate) {
	        this.requestDate = requestDate;
	    }

	    public int getUnitRequired() {
	        return unitRequired;
	    }

	    public void setUnitRequired(int unitRequired) {
	        this.unitRequired = unitRequired;
	    }
	}