package com.example.erealtorapp;

public class PDFclass {
    String name;

    public String getName() {
        return name;
    }

    public PDFclass(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;
}
