package com.example.artina;

// DTO pour la requête
public class EmailRequest {
    private String recipient;
    private double amount;
    private String eventName;
    private String reference;

    public String getRecipient() {
        return recipient;
    }

    public double getAmount() {
        return amount;
    }

    public String getReference() {
        return reference;
    }

    public String getEventName() {
        return eventName;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}