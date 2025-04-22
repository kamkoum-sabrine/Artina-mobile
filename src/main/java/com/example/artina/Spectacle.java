package com.example.artina;
import com.google.gson.annotations.SerializedName;

public class Spectacle {


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

    private Lieu idLieu;

    public static class Lieu {
        private Long id;
        private String nom;
        private String adresse;

        public String getNom() {
            return nom;
        }

        public Long getId() {
            return id;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }
    }

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
}