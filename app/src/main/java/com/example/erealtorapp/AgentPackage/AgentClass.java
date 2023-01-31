package com.example.erealtorapp.AgentPackage;

import java.util.ArrayList;

public class AgentClass {
    String AgentID;
    String Name;
    String phone;
    String AssignedProperty;
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
