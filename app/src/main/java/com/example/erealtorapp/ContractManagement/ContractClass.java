package com.example.erealtorapp.ContractManagement;

import java.util.ArrayList;

public class ContractClass {
    String LandlordID;
    String TenantID;
    String PropertyID;
    String RentAmount;
    String Duration;
    String Status;

    public String getStatus() {
        return Status;
    }

    public String getPropertyID() {
        return PropertyID;
    }

    public ArrayList<MessagesClass> getMessages() {
        return Messages;
    }

    public void setMessages(ArrayList<MessagesClass> messages) {
        Messages = messages;
    }

    public void setPropertyID(String propertyID) {
        PropertyID = propertyID;
    }

    public void setStatus(String status) {
        Status = status;
    }

    ArrayList<MessagesClass> Messages;

    public String getLandlordID() {
        return LandlordID;
    }

    public void setLandlordID(String landlordID) {
        LandlordID = landlordID;
    }

    public String getTenantID() {
        return TenantID;
    }

    public void setTenantID(String tenantID) {
        TenantID = tenantID;
    }

    public String getRentAmount() {
        return RentAmount;
    }

    public void setRentAmount(String rentAmount) {
        RentAmount = rentAmount;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public ContractClass(String landlordID, String tenantID, String PropertyID, String rentAmount
            , String duration) {
        LandlordID = landlordID;
        TenantID = tenantID;
        RentAmount = rentAmount;
        Duration = duration;
        this.PropertyID = PropertyID;
    }

    public ContractClass(String landlordID, String tenantID, String rentAmount
            , String duration, ArrayList<MessagesClass> Messages) {
        LandlordID = landlordID;
        TenantID = tenantID;
        RentAmount = rentAmount;
        Duration = duration;
        this.Messages = Messages;
    }
}
