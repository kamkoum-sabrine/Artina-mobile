package com.example.artina;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.artina.ApiClient;
import com.example.artina.ApiService;
import com.example.artina.RegisterRequest;
public class RegisterActivity extends AppCompatActivity {

    EditText nomInput, prenomInput, emailInput, passwordInput, telInput;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nomInput = findViewById(R.id.nom_input);
        prenomInput = findViewById(R.id.prenom_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        telInput = findViewById(R.id.tel_input);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> {
            String nom = nomInput.getText().toString().trim();
            String prenom = prenomInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String tel = telInput.getText().toString().trim();

            registerUser(nom,prenom,tel,email,password);
        });

}

    private void registerUser(String nom, String prenom, String tel, String email, String password) {
        RegisterRequest request = new RegisterRequest(nom, prenom, tel, email, password);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<RegisterResponse> call = apiService.registerUser(request);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse user = response.body();
                    Toast.makeText(RegisterActivity.this, "Bienvenue " + user.getPrenom() + " !", Toast.LENGTH_LONG).show();

                    // Redirection vers LoginActivity après quelques secondes (optionnel)
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        int spectacleId = getIntent().getIntExtra("spectacle_id", -1);
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("spectacle_id", spectacleId); // Passer l'ID de spectacle
                        startActivity(intent);
                        finish(); // Pour ne pas revenir à CreateAccountActivity avec le bouton retour
                    }, 1500); // délai en ms pour laisser le temps au toast
                } else {
                    Toast.makeText(RegisterActivity.this, "Erreur " + response.code(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}