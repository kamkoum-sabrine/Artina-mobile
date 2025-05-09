package com.example.artina;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Spectacle implements Parcelable {



    @SerializedName("id")
    private Long id;

    @SerializedName("titre")
    private String titre;

    @SerializedName("date")
    private String date;
    @SerializedName("heureDebut")
    private Double heureDebut;

    @SerializedName("imagePath")
    private String imagePath;

    @SerializedName("imagePathVertical")
    private String imagePathVertical;

    public String getImagePathVertical() {
        return imagePathVertical;
    }

    public void setImagePathVertical(String imagePathVertical) {
        this.imagePathVertical = imagePathVertical;
    }

    private Lieu idLieu;

    public Spectacle(){}


    public void setIdLieu(Lieu idLieu) {
        this.idLieu = idLieu;
    }

    public Lieu getIdLieu() {
        return idLieu;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public Double getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Double heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Spectacle(Long id) {
        this.id = id;
    }

    protected Spectacle(Parcel in) {
        id = in.readLong();
        titre = in.readString();
        date = in.readString();
        heureDebut = in.readDouble();
        imagePath = in.readString();
        idLieu = in.readParcelable(Lieu.class.getClassLoader());
    }

    public static final Creator<Spectacle> CREATOR = new Creator<Spectacle>() {
        @Override
        public Spectacle createFromParcel(Parcel in) {
            return new Spectacle(in);
        }

        @Override
        public Spectacle[] newArray(int size) {
            return new Spectacle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(titre);
        dest.writeString(date);
        dest.writeDouble(heureDebut);
        dest.writeString(imagePath);
        dest.writeParcelable(idLieu, flags);
    }

    @Override
    public String toString() {
        return "Spectacle{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", date='" + date + '\'' +
                ", heureDebut=" + heureDebut +
                ", imagePath='" + imagePath + '\'' +
                ", imagePathVertical='" + imagePathVertical + '\'' +
                ", idLieu=" + idLieu +
                '}';
    }
}