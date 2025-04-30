package com.example.artina;

public class LoginResponse {
    private Client client;
    private String message;

    public Client getClient() {
        return client;
    }

    public String getMessage() {
        return message;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

