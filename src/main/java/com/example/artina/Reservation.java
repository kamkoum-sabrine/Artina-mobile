package com.example.artina;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Reservation {
    private Spectacle spectacle;
    private Client client;
    private Billet billet;

    @SerializedName("QUANTITE_BILLET")
    private int quantiteBillet;

    public void setQuantiteBillet(int quantiteBillet) {
        this.quantiteBillet = quantiteBillet;
    }

    public int getQuantiteBillet() {
        return quantiteBillet;
    }


    public Reservation() {
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

    public Client getClient() {
        return client;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public Billet getBillet() {
        return billet;
    }
}

