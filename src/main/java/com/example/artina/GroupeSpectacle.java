package com.example.artina;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupeSpectacle implements Parcelable {

    private String titre;
    private List<Representation> representations;

    // Getters et setters
    public String getTitre() { return titre; }
    public List<Representation> getRepresentations() { return representations; }

    public GroupeSpectacle() {
        this.representations = new ArrayList<>();  // Initialisation de la liste
    }
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

    // Implémentation de Parcelable
    protected GroupeSpectacle(Parcel in) {
        titre = in.readString();
        representations = in.createTypedArrayList(Representation.CREATOR); // On récupère la liste des objets Representation
    }

    public static final Creator<GroupeSpectacle> CREATOR = new Creator<GroupeSpectacle>() {
        @Override
        public GroupeSpectacle createFromParcel(Parcel in) {
            return new GroupeSpectacle(in);
        }

        @Override
        public GroupeSpectacle[] newArray(int size) {
            return new GroupeSpectacle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titre); // Sérialisation de la chaîne 'titre'
        dest.writeTypedList(representations); // Sérialisation de la liste de représentations
    }
}