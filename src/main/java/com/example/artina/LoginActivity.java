package com.example.artina;

import android.content.Intent;
import android.content.SharedPreferences;
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
                loginUser(email, password);
             //   if (email.equals("kamkoumsabrine@gmail.com")&&(password.equals("password"))){
                 //   navigateToHome();
               // }
                /*else {
                    showError("Email ou mot de passe incorrect");

                }*/
            }
        });
    }
    private boolean validateInputs(String email, String password) {
        // Validation simple
        return !email.isEmpty() && !password.isEmpty();
    }

    private void loginUser(String email, String password) {
        System.out.println("Mot de passe "+password);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.loginUser(new LoginRequest(email, password));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    // Stockez les informations de l'utilisateur si nécessaire
                    saveUserData(loginResponse);
                    navigateToHome();
                } else {
                    showError("Email ou mot de passe incorrect");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showError("Erreur de connexion: " + t.getMessage());
            }
        });
    }

    private void saveUserData(LoginResponse user) {
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("user_id", user.getId());
        editor.putString("user_nom", user.getNom());
        editor.putString("user_prenom", user.getPrenom());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_tel", user.getTel()); // si vous avez besoin du téléphone
        editor.apply();
    }


   /* private void navigateToHome(Client client) {
        Intent intent = new Intent(this, DetailSpectacleActivity.class);
        intent.putExtra("CLIENT_ID", client.getId());
        startActivity(intent);
        finish();
    }*/
   private void navigateToHome() {
       Long spectacleId = getIntent().getLongExtra("spectacle_id", -1);
       System.out.println("Spectacle ID "+spectacleId);
       if (spectacleId==-1){
           Intent intent = new Intent(this, MainActivity.class);
           intent.putExtra("spectacle_id", spectacleId);
           SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
           intent.putExtra("user_id", sharedPref.getLong("user_id", -1));

           startActivity(intent);
           finish();
       }
       else {
           Intent intent = new Intent(this, ReservationInviteActivity.class);
           intent.putExtra("spectacle_id", spectacleId);
           SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
           intent.putExtra("user_id", sharedPref.getLong("user_id", -1));

           startActivity(intent);
           finish();
       }


   }
  /*  private void navigateToHome() {
        Long spectacleId = getIntent().getLongExtra("spectacle_id", -1);
        System.out.println("pppsstttt "+spectacleId);
        Intent intent = new Intent(this, ReservationInviteActivity.class);
        intent.putExtra("spectacle_id", spectacleId);
        startActivity(intent);
        finish();
        }*/

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}