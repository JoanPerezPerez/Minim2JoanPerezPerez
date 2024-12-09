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

import com.example.robacobres_androidclient.callbacks.AuthCallback;
import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.ServiceBBDD;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import java.util.HashSet;

public class SplashScreenActivity extends AppCompatActivity implements AuthCallback {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;  // Duración de la splash screen (en milisegundos)
    private ServiceBBDD service;
    private ImageView hiloCobreImageView;  // Imagen del hilo de cobre

    Context context;

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

        context=SplashScreenActivity.this;
        service = ServiceBBDD.getInstance(context);

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
        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPref.getString("authToken", null);

        if (authToken==null) {
            Log.d("Shared Preferences","No Cookies stored");
            Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        } else {
            this.service.getSession(this);
        }
    }

    @Override
    public void onAuthorized() {
        Log.d("COOKIE AUTH","Authorized");
        Intent intent = new Intent(SplashScreenActivity.this, MultiActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUnauthorized() {
        Log.d("COOKIE AUTH","Unauthorized");
        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("authToken");  // Elimina la cookie almacenada con la clave "authToken"
        editor.apply();
        Log.d("Shared Preferences","Cookies Deleted");
        Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}

