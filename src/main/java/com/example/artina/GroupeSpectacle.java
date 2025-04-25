package com.example.artina;

import java.util.ArrayList;
import java.util.List;

public class GroupeSpectacle {
    private String titre;
    private List<Representation> representations;

    // Getters et setters
    public String getTitre() { return titre; }
    public List<Representation> getRepresentations() { return representations; }

    @Override
    public String toString() {
        return "GroupeSpectacle{" +
                "titre='" + titre + '\'' +
                ", representations=" + representations +
                '}';
    }

    // Image principale du groupe
    public String getImagePrincipale() {
        if (representations != null && !representations.isEmpty()) {
            return representations.get(0).getImagePath(); // prend la première
        }
        return null;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setRepresentations(List<Representation> representations) {
        this.representations = representations;
    }
}
