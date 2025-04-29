package com.example.artina;

public class LoginRequest {
    private String email;
    private String motp;

    public LoginRequest(String email, String motp) {
        this.email = email;
        this.motp = motp;
    }

    public String getEmail() {
        return email;
    }

    public String getMotp() {
        return motp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotp(String motp) {
        this.motp = motp;
    }

    // Getters
}
