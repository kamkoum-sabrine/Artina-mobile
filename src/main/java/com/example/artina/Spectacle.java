package com.example.artina;

public class Spectacle {
    private String titre;
    private String description;
    private int imageResource;

    public Spectacle(String titre, String description, int imageResource) {
        this.titre = titre;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }
}
