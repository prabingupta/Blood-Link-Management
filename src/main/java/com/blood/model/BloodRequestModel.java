package com.blood.model;

public class BloodRequestModel {

	 private int requestId;
	    private int patientId;
	    private String bloodGroup;
	    private String requestDate;
	    private int unitRequired;
	    private String status;

	    public BloodRequestModel() {
	    }

	    public BloodRequestModel(int patientId, String bloodGroup, String requestDate, int unitRequired, String status) {
	        this.patientId = patientId;
	        this.bloodGroup = bloodGroup;
	        this.requestDate = requestDate;
	        this.unitRequired = unitRequired;
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

	    public String getBloodGroup() {
	        return bloodGroup;
	    }

	    public void setBloodGroup(String bloodGroup) {
	        this.bloodGroup = bloodGroup;
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

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }
	}

