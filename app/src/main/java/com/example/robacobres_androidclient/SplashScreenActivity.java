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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.Service;

public class SplashScreenActivity extends AppCompatActivity implements UserCallback {

    private static final int SPLASH_DISPLAY_LENGTH = 2000;
    Service service;

    private ProgressBar loadingSpinner;

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

        //INSTANCIA TRACKSERVICE
        service=Service.getInstance();

        // Inicializar el ProgressBar (spinner)
        loadingSpinner = findViewById(R.id.loadingSpinner);

        // Mostrar el spinner (loading)
        loadingSpinner.setVisibility(View.VISIBLE);

        // Simular una pequeña espera mientras verificamos SharedPreferences
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();  // Método para verificar si el usuario ya está autenticado
            }
        }, SPLASH_DISPLAY_LENGTH);  // El tiempo de espera
    }

    private void checkLoginStatus() {
        // Recuperar las preferencias compartidas
        SharedPreferences sharedPref = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);

        // Comprobar si ya existe un valor de login guardado (por ejemplo, un token o bandera de login)
        String username = sharedPref.getString("username", null);
        String password = sharedPref.getString("password", null);

        if (username==null || password==null) {
            // Si no está logueado, redirigir al LoginActivity
            Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            loadingSpinner.setVisibility(View.GONE);
            Log.d("SHAREDPREFS",username +" "+password);
            this.service.loginUser(username,password,SplashScreenActivity.this);
        }
    }

    @Override
    public void onLoginOK(User _user) {
        // Si ya está logueado, redirigir al MainActivity o HomeActivity
        Intent intent = new Intent(SplashScreenActivity.this, MultiActivity.class); // Reemplaza HomeActivity por la actividad que quieras mostrar después del login
        // Pasar los datos a la nueva actividad
        intent.putExtra("userId", _user.getId());
        intent.putExtra("userName", _user.getName());
        intent.putExtra("password", _user.getPassword());

        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginERROR() {
        // Si no está logueado, redirigir al LoginActivity
        Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}