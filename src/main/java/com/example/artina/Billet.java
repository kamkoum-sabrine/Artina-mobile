package com.example.artina;

import com.google.gson.annotations.SerializedName;

public class Billet {
    @SerializedName("idBillet")
    private Long id;

    @SerializedName("categorie")
    private String categorie;

    @SerializedName("prix")
    private String prix;

    @SerializedName("idSpec")
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

    public String getPrix() {
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

    public void setPrix(String prix) {
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
}
