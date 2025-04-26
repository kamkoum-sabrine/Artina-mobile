package com.example.artina;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Lieu implements Parcelable {
    @SerializedName("idLieu")
    private Long id;

    @SerializedName("nomLieu")
    private String nom;

    @SerializedName("adresse")
    private String adresse;

    @SerializedName("capacite")
    private int capacite;

    @SerializedName("ville")
    private String ville;

    @SerializedName("url")
    private String url;

    // Constructeur vide
    public Lieu() {}

    protected Lieu(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        nom = in.readString();
        adresse = in.readString();
        ville = in.readString();
        url = in.readString();
    }

    public static final Creator<Lieu> CREATOR = new Creator<Lieu>() {
        @Override
        public Lieu createFromParcel(Parcel in) {
            return new Lieu(in);
        }

        @Override
        public Lieu[] newArray(int size) {
            return new Lieu[size];
        }
    };

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(nom);
        dest.writeString(adresse);
        dest.writeString(ville);
        dest.writeString(url);
    }
}
