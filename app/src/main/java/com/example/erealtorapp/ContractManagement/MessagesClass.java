package com.example.erealtorapp.ContractManagement;

public class MessagesClass {
    String Sender;
    String Message;

    public MessagesClass(String sender, String message) {
        Sender = sender;
        Message = message;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
