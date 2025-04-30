package com.example.artina;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_input);
        passwordEditText = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            System.out.println("Email "+email);
            System.out.println("Password "+password);

            if (validateInputs(email, password)) {
                System.out.println("+++++++++++++++++++++");
                //loginUser(email, password);
                if (email.equals("kamkoumsabrine@gmail.com")&&(password.equals("password"))){
                    navigateToHome();
                }
                else {
                    showError("Email ou mot de passe incorrect");

                }
            }
        });
    }
    private boolean validateInputs(String email, String password) {
        // Validation simple
        return !email.isEmpty() && !password.isEmpty();
    }

    private void loginUser(String email, String password) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.loginUser(new LoginRequest(email, password));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println("response "+response);
                if (response.isSuccessful() && response.body() != null) {
                    // Login réussi
                    Client client = response.body().getClient();
                   // navigateToHome(client);
                } else {
                    // Erreur de login
                    showError("Email ou mot de passe incorrect");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showError("Erreur de connexion: " + t.getMessage());
            }
        });
    }

   /* private void navigateToHome(Client client) {
        Intent intent = new Intent(this, DetailSpectacleActivity.class);
        intent.putExtra("CLIENT_ID", client.getId());
        startActivity(intent);
        finish();
    }*/

    private void navigateToHome() {
        Long spectacleId = getIntent().getLongExtra("spectacle_id", -1);
        System.out.println("pppsstttt "+spectacleId);
        Intent intent = new Intent(this, ReservationInviteActivity.class);
        intent.putExtra("spectacle_id", spectacleId);
        startActivity(intent);
        finish();
        }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}