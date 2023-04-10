package com.example.garageapp.utility;

public class User {
    private String name;
    private byte[] image_url;
    private String email;
    private String password;
    public User() {
    }

    public User( String email, byte[] image_url, String name, String password) {
        this.name = name;
        this.image_url = image_url;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage_url() {
        return image_url;
    }

    public void setImage_url(byte[] image_url) {
        this.image_url = image_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
