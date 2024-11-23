package com.example.robacobres_androidclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.Service;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class SplashScreenActivity extends AppCompatActivity implements UserCallback {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;  // Duración de la splash screen (en milisegundos)
    private Service service;
    private ImageView hiloCobreImageView;  // Imagen del hilo de cobre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        service = Service.getInstance();

        hiloCobreImageView = findViewById(R.id.hilo_cobre);

        rotateHiloCobre();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void rotateHiloCobre() {
        Animation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(Animation.INFINITE);
        hiloCobreImageView.startAnimation(rotate);
    }

    private void checkLoginStatus() {
        SharedPreferences sharedPref = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);


        String username = sharedPref.getString("username", null);
        String password = sharedPref.getString("password", null);

        if (username == null || password == null) {
            Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        } else {
            this.service.loginUser(username, password, SplashScreenActivity.this);
        }
    }

    @Override
    public void onLoginOK(User _user) {

        Intent intent = new Intent(SplashScreenActivity.this, MultiActivity.class);  // Reemplaza por la actividad de inicio

        intent.putExtra("userId", _user.getId());
        intent.putExtra("userName", _user.getName());
        intent.putExtra("password", _user.getPassword());

        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginERROR() {
        Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

