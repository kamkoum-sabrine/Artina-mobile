package com.example.artina;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/spectacles")
    Call<List<Spectacle>> getAllSpectacles();

    @GET("api/spectacles/{id}")
    Call<Spectacle> getSpectacleById(@Path("id") Long id);

    // Ajoutez d'autres endpoints au besoin

    @GET("api/billets/by-spectacle/{spectacleId}")
    Call<List<Billet>> getBuilletsBySpectacleId(@Path("spectacleId") Long id);

    @GET("api/spectacles/grouped")
    Call<List<GroupeSpectacle>> getGroupedSpectacles1();

    @GET("api/spectacles/grouped-by-titre")
    Call<List<GroupeSpectacle>> getGroupedSpectacles();


    @POST("api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);


}
class LoginResponse {
    private String message;
    private Client client;

    public String getMessage() {
        return message;
    }

    public Client getClient() {
        return client;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // Getters & Setters
    class Client {
        private Long id;
        private String nom;
        private String prenom;
        private String email;

        public Long getId() {
            return id;
        }

        public String getNom() {
            return nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public String getEmail() {
            return email;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        // Getters & Setters
    }
}