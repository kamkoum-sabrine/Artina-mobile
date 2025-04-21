package com.example.artina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 4000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      // TextView logoText = findViewById(R.id.appName);
        ImageView logoImage = findViewById(R.id.appLogo);
        // Animation clignotante (fade in/out)
        AlphaAnimation blink = new AlphaAnimation(0.3f, 1.0f);
        blink.setDuration(1000);
        blink.setRepeatMode(AlphaAnimation.REVERSE);
        blink.setRepeatCount(AlphaAnimation.INFINITE);
        logoImage.startAnimation(blink);
      //  logoText.startAnimation(blink);
        // Aller à l'activité principale après 3 secondes
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, SPLASH_DURATION);
    }
}