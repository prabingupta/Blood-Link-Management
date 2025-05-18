package com.blood.model;

import java.util.Date;

public class BloodStockModel {
    private String bloodGroup;
    private int unitsAvailable;
    private Date lastUpdated;

    public BloodStockModel() {
    }

    public BloodStockModel(String bloodGroup, int unitsAvailable, Date lastUpdated) {
        this.bloodGroup = bloodGroup;
        this.unitsAvailable = unitsAvailable;
        this.lastUpdated = lastUpdated;
    }

    public String getBloodGroup() { 
        return bloodGroup; 
    }

    public void setBloodGroup(String bloodGroup) { 
        this.bloodGroup = bloodGroup; 
    }

    public int getUnitsAvailable() { 
        return unitsAvailable; 
    }

    public void setUnitsAvailable(int unitsAvailable) { 
        this.unitsAvailable = unitsAvailable; 
    }

    public Date getLastUpdated() { 
        return lastUpdated; 
    }

    public void setLastUpdated(Date lastUpdated) { 
        this.lastUpdated = lastUpdated; 
    }
} 