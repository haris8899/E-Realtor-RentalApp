package com.example.erealtorapp.ContractManagement;

import java.util.ArrayList;

public class ContractClass {
    String LandlordID;
    String TenantID;
    String PropertyID;
    String ContractID;
    String RentAmount;

    public String getContractID() {
        return ContractID;
    }

    public void setContractID(String contractID) {
        ContractID = contractID;
    }

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

    public ContractClass(String ContractID, String landlordID, String tenantID, String PropertyID,String Status, String rentAmount) {
        this.ContractID = ContractID;
        LandlordID = landlordID;
        TenantID = tenantID;
        RentAmount = rentAmount;
        this.Status = Status;
        this.PropertyID = PropertyID;
    }

    public ContractClass(String landlordID, String tenantID, String PropertyID,String Status, String rentAmount) {
        LandlordID = landlordID;
        TenantID = tenantID;
        RentAmount = rentAmount;
        this.Status = Status;
        this.PropertyID = PropertyID;
    }

    public ContractClass(String landlordID, String tenantID, String rentAmount, ArrayList<MessagesClass> Messages) {
        LandlordID = landlordID;
        TenantID = tenantID;
        RentAmount = rentAmount;
        this.Messages = Messages;
    }
}
