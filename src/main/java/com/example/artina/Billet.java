package com.example.artina;

import com.google.gson.annotations.SerializedName;

public class Billet {
    @SerializedName("id")
    private Long id;

    @SerializedName("categorie")
    private String categorie;

    @SerializedName("prix")
    private Double prix;

    @SerializedName("spectacle")
    private Spectacle spectacle;

    @SerializedName("vendu")
    private String vendu;

    @SerializedName("nbrerestant")
    private String nbrerestant;

    public Long getId() {
        return id;
    }

    public String getCategorie() {
        return categorie;
    }

    public Double getPrix() {
        return prix;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public String getVendu() {
        return vendu;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }

    public void setVendu(String vendu) {
        this.vendu = vendu;
    }

    public String getNbrerestant() {
        return nbrerestant;
    }

    public void setNbrerestant(String nbrerestant) {
        this.nbrerestant = nbrerestant;
    }

    @Override
    public String toString() {
        return "Billet{" +
                "id=" + id +
                ", categorie='" + categorie + '\'' +
                ", prix='" + prix + '\'' +
                ", spectacle=" + spectacle+
                ", vendu='" + vendu + '\'' +
                ", nbrerestant='" + nbrerestant + '\'' +
                '}';
    }
}
