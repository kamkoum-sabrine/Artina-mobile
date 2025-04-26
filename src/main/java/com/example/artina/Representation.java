package com.example.artina;

import java.io.Serializable;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Representation implements Parcelable {

    @SerializedName("idSpec")
    private Long idSpec;

    @SerializedName("dates")
    private String dates;

    @SerializedName("hDebut")
    private double hDebut;

    @SerializedName("durees")
    private double durees;

    @SerializedName("lieu")
    private Lieu lieu;

    @SerializedName("imagePath")
    private String imagePath;

    @SerializedName("imagePathVertical")
    private String imagePathVertical;

    @SerializedName("titre")
    private String titre;
    // Constructor, getters et setters


    protected Representation(Parcel in) {
        idSpec = in.readLong();
        dates = in.readString();
        hDebut = in.readDouble();
        durees = in.readDouble();
        titre = in.readString();
        lieu = in.readParcelable(Lieu.class.getClassLoader());
        imagePath = in.readString();
        imagePathVertical = in.readString();  // Ajouté pour lire imagePathVertical
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
        dest.writeLong(idSpec);
        dest.writeString(dates);
        dest.writeDouble(hDebut);
        dest.writeDouble(durees);
        dest.writeString(titre);
        dest.writeParcelable(lieu, flags);
        dest.writeString(imagePath);
        dest.writeString(imagePathVertical);  // Ajouté pour écrire imagePathVertical
    }


    // Getters et Setters
   /* public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }*/

    public Long getIdSpec() {
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

    public Lieu getLieu() {
        return lieu;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setIdSpec(Long idSpec) {
        this.idSpec = idSpec;
    }

    public String getImagePathVertical() {
        return imagePathVertical;
    }

    public void setImagePathVertical(String imagePathVertical) {
        this.imagePathVertical = imagePathVertical;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return titre;
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

    public void setLieu(Lieu lieu) {
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
              //  ", spectacle=" + spectacle +
                ", lieu=" + (lieu != null ? lieu.getNom() : "null") +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
