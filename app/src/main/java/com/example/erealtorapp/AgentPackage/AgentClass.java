package com.example.erealtorapp.AgentPackage;

import java.util.ArrayList;

public class AgentClass {
    String AgentID;
    String Name;

    public AgentClass(String agentID, String name, String profilePicture, String email) {
        AgentID = agentID;
        Name = name;
        ProfilePicture = profilePicture;
        this.email = email;
    }

    String phone;
    String ProfilePicture;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String AssignedProperty;

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    String RequestStatus;

    public String getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getAgentID() {
        return AgentID;
    }

    public void setAgentID(String agentID) {
        AgentID = agentID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAssignedProperty() {
        return AssignedProperty;
    }

    public void setAssignedProperty(String assignedProperty) {
        AssignedProperty = assignedProperty;
    }
}
