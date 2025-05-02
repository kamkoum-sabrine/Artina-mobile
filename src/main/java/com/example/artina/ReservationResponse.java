package com.example.artina;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class ReservationResponse {
    private Long id;
    private Spectacle spectacle;
    private Client client;
    private Billet billet;
    @SerializedName("dateReservation")
    private LocalDate dateReservation;



    public ReservationResponse() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setBillet(Billet billet) {
        this.billet = billet;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Long getId() {
        return id;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public Client getClient() {
        return client;
    }

    public Billet getBillet() {
        return billet;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }
}
