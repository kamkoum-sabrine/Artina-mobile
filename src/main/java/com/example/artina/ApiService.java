package com.example.artina;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/spectacles")
    Call<List<Spectacle>> getAllSpectacles();

    @GET("api/spectacles/{id}")
    Call<Spectacle> getSpectacleById(@Path("id") Long id);

    // Ajoutez d'autres endpoints au besoin

    @GET("api/billets/by-spectacle/{spectacleId}")
    Call<List<Billet>> getBuilletsBySpectacleId(@Path("spectacleId") Long id);
}