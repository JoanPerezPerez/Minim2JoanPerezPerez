package com.example.robacobres_androidclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.robacobres_androidclient.services.Service;

public class MultiActivity extends AppCompatActivity {
    Service serviceREST;
    Button btnPlay;
    Button btnMisObjetos;
    Button btnMisPersonajes;
    ImageButton btnTienda;
    Button btnEliminarCuenta;
    private Context context;
    String userId;
    String userName;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        serviceREST=Service.getInstance();
        btnPlay = findViewById(R.id.Button_play);
        btnMisObjetos = findViewById(R.id.Button_misobjetos);
        btnMisPersonajes = findViewById(R.id.Button_Mispersonajes);
        btnTienda = findViewById(R.id.Button_tienda);
        btnEliminarCuenta = findViewById(R.id.Button_eliminarcuenta);
        // Agafar les dades que li passem del usuari registrat
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");
        password = intent.getStringExtra("password");

        context = MultiActivity.this;
    }

    public void onClickTienda(View V){
        Intent intent = new Intent(context, ItemsActivity.class);
        // Pasar los datos a la nueva actividad
        intent.putExtra("userId", userId);
        intent.putExtra("userName", userName);
        intent.putExtra("password", password);
        context.startActivity(intent);
    }

    public void onClickLogout(View V){
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();// "defaultUsername" is returned if no value is found
        editor.remove("username");
        editor.remove("password");
        editor.apply();
        Log.d("Shared Preferences","Username and password deleted");

        Intent intent = new Intent(context, LogInActivity.class);
        // Iniciar la nueva actividad
        context.startActivity(intent);

        this.finish();
    }
}