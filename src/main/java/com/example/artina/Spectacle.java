package com.example.artina;
import com.google.gson.annotations.SerializedName;

public class Spectacle {


    @SerializedName("id")
    private Long id;

    @SerializedName("titre")
    private String titre;

    @SerializedName("date")
    private String date; // ou utilisez LocalDate avec un adaptateur personnalisé

    @SerializedName("imagePath")
    private String imagePath;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}