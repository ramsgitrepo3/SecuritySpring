package com.example.SecurirtySpring.Dto;


import java.util.Set;

public class RegisterRequest {

    private String username;
    private String password;
    private Set<String> roles; // e.g. ["ROLE_USER"]

    // ✅ Default constructor
    public RegisterRequest() {
    }

    // ✅ All-args constructor
    public RegisterRequest(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    // ✅ toString (mask password for safety)
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", roles=" + roles +
                '}';
    }
}
