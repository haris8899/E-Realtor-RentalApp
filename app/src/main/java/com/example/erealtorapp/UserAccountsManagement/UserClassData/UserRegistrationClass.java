package com.example.erealtorapp.UserAccountsManagement.UserClassData;

public class UserRegistrationClass {
    String Username;
    String Email;
    String Password;
    String Phone;
    String Type;
    String ProfileURI;

    public String getProfileURI() {
        return ProfileURI;
    }

    public void setProfileURI(String profileURI) {
        ProfileURI = profileURI;
    }

    private static UserRegistrationClass sign;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUsername() {
        return Username;
    }

    public UserRegistrationClass(String username, String email, String password, String phone, String type, String imageuri) {
        Username = username;
        Email = email;
        Password = password;
        Phone = phone;
        Type = type;
        ProfileURI = imageuri;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public UserRegistrationClass() {
    }

    public static UserRegistrationClass getInstance(String userName, String email, String password, String phone, String type, String imageuri)
    {

        if(sign==null){
            sign=new UserRegistrationClass(userName,email,password,phone, type, imageuri);
        }
        return sign;
    }
    public static UserRegistrationClass getInstance(){

        if(sign==null){
            sign=new UserRegistrationClass();
        }
        return sign;
    }
}
