package com.example.SecurirtySpring.Dto;


public class LoginRequest {

    private String username;
    private String password;

    // ✅ Default constructor
    public LoginRequest() {
    }

    // ✅ All-args constructor
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ✅ Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ✅ toString (useful for debugging, exclude password in real apps)
    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}
