package com.example.orderfoodmanagement.Domain;

public class Users {
    private String email;
    private String password;

    public Users() {
        // Empty constructor needed for Firebase
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

