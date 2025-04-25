package com.example.artina;

public class Representation {
    private int idSpec;
    private Spectacle spectacle;
    private String dates;
    private double hDebut;
    private double durees;
    private Spectacle.Lieu lieu;
    private String imagePath;

    // Getters et setters


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
                ", spectacle="+spectacle+
                ", lieu=" + (lieu != null ? lieu.getNom() : "null") +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

}