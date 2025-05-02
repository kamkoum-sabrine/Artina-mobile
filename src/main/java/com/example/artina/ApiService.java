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
    @POST("api/users")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("api/reservations")
    Call<ReservationResponse> createReservation(@Body Reservation reservation);

    @POST("api/email/send-confirmation")
    Call<Void> sendConfirmationEmail(@Body EmailRequest request);

    @GET("api/reservations/user/{userId}")
    Call<List<Reservation>> getReservationsByUser(@Path("userId") Long userId);

}
