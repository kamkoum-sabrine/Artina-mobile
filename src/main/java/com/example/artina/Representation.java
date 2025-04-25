package com.example.artina;

import java.io.Serializable;


import android.os.Parcel;
import android.os.Parcelable;

public class Representation implements Parcelable {

    private int idSpec;
    private Spectacle spectacle;
    private String dates;
    private double hDebut;
    private double durees;
    private Spectacle.Lieu lieu;
    private String imagePath;

    // Constructor, getters et setters

    protected Representation(Parcel in) {
        idSpec = in.readInt();
        spectacle = in.readParcelable(Spectacle.class.getClassLoader());
        dates = in.readString();
        hDebut = in.readDouble();
        durees = in.readDouble();
        lieu = in.readParcelable(Spectacle.Lieu.class.getClassLoader());
        imagePath = in.readString();
    }

    public static final Creator<Representation> CREATOR = new Creator<Representation>() {
        @Override
        public Representation createFromParcel(Parcel in) {
            return new Representation(in);
        }

        @Override
        public Representation[] newArray(int size) {
            return new Representation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idSpec);
        dest.writeParcelable(spectacle, flags);
        dest.writeString(dates);
        dest.writeDouble(hDebut);
        dest.writeDouble(durees);
        dest.writeParcelable(lieu, flags);
        dest.writeString(imagePath);
    }

    // Getters et Setters
    public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public int getIdSpec() {
        return idSpec;
    }

    public String getDates() {
        return dates;
    }

    public double getDurees() {
        return durees;
    }

    public double gethDebut() {
        return hDebut;
    }

    public Spectacle.Lieu getLieu() {
        return lieu;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setIdSpec(int idSpec) {
        this.idSpec = idSpec;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public void sethDebut(double hDebut) {
        this.hDebut = hDebut;
    }

    public void setDurees(double durees) {
        this.durees = durees;
    }

    public void setLieu(Spectacle.Lieu lieu) {
        this.lieu = lieu;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Representation{" +
                "idSpec=" + idSpec +
                ", dates='" + dates + '\'' +
                ", hDebut=" + hDebut +
                ", durees=" + durees +
                ", spectacle=" + spectacle +
                ", lieu=" + (lieu != null ? lieu.getNom() : "null") +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
