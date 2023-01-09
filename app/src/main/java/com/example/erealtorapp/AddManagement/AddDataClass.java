package com.example.erealtorapp.AddManagement;

import java.util.List;

public class AddDataClass {
    String Title;
    String id;
    String Address;
    int NoofRooms;
    int Rent;
    int plotsize;
    String OwnerID;
    Boolean Status;
    List<String> images;

    public AddDataClass(String title, int rent, List<String> image) {
        Title = title;
        Rent = rent;
        this.images = image;
    }

    public AddDataClass(String id,String title, int rent, List<String> image) {
        this.id = id;
        Title = title;
        Rent = rent;
        this.images = image;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getNoofRooms() {
        return NoofRooms;
    }

    public void setNoofRooms(int noofRooms) {
        NoofRooms = noofRooms;
    }

    public int getRent() {
        return Rent;
    }

    public void setRent(int rent) {
        Rent = rent;
    }

    public int getPlotsize() {
        return plotsize;
    }

    public void setPlotsize(int plotsize) {
        this.plotsize = plotsize;
    }

    public String getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(String ownerID) {
        OwnerID = ownerID;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public AddDataClass(String title, String address, int noofRooms, int rent, int plotsize, String ownerID, Boolean status, List<String> images) {
        Title = title;
        Address = address;
        NoofRooms = noofRooms;
        Rent = rent;
        this.plotsize = plotsize;
        OwnerID = ownerID;
        Status = status;
        this.images = images;
    }
}
