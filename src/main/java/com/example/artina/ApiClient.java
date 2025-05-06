package com.example.artina;

import android.util.Log;

import java.io.File;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.187:8081/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HeaderInterceptor()) // ← ici
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Méthode pour effacer le cache
    public static void clearCache() {
        retrofit = null; // Force la recréation au prochain appel

        // Optionnel : Effacer aussi le cache HTTP si vous en utilisez un
        try {
            File cacheDir = new File(MyApplication.getInstance().getCacheDir(), "http-cache");
            deleteRecursive(cacheDir);
        } catch (Exception e) {
            Log.e("ApiClient", "Erreur nettoyage cache", e);
        }
    }

    // Méthode utilitaire pour supprimer un répertoire récursivement
    private static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }
}