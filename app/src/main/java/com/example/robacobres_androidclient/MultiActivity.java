package com.example.robacobres_androidclient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ImageButton;

import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.robacobres_androidclient.callbacks.AuthCallback;
import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.Service;
import com.example.robacobres_androidclient.services.ServiceBBDD;

public class MultiActivity extends AppCompatActivity implements AuthCallback, UserCallback {
    Service serviceREST;
    ServiceBBDD serviceRESTBBDD;
    Button btnPlay;
    Button btnMisObjetos;
    Button btnMisPersonajes;
    ImageButton btnTienda;
    Button btnEliminarCuenta;
    User user;

    private ProgressBar progressBar;

    private Context context;

    String userId;
    String userName;
    String password;

    boolean isFromDatabase;

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

        context = MultiActivity.this;

        serviceREST=Service.getInstance(this);
        serviceRESTBBDD=ServiceBBDD.getInstance(this);

        btnPlay = findViewById(R.id.Button_play);
        btnMisObjetos = findViewById(R.id.Button_misobjetos);
        btnMisPersonajes = findViewById(R.id.Button_Mispersonajes);
        btnTienda = findViewById(R.id.Button_tienda);
        btnEliminarCuenta = findViewById(R.id.Button_eliminarcuenta);
        progressBar = findViewById(R.id.progressBar);
        // Agafar les dades que li passem del usuari registrat
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");
        password = intent.getStringExtra("password");
        isFromDatabase = getIntent().getBooleanExtra("isFromDatabase", false);
        getUser();
    }

    public void onClickTienda(View V){
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(context, ItemsActivity.class);
        // Pasar los datos a la nueva actividad
        intent.putExtra("userId", userId);
        intent.putExtra("userName", userName);
        intent.putExtra("password", password);
        intent.putExtra("isFromDatabase",isFromDatabase);
        progressBar.setVisibility(View.GONE);
        startActivity(intent);
    }

    public void onClickLogout(View V){
        this.serviceREST.quitSession(this);
        this.serviceRESTBBDD.quitSession(this);

        Intent intent = new Intent(context, LogInActivity.class);

        // Iniciar la nueva actividad
        context.startActivity(intent);

        this.finish();
    }

    public void onClickEliminarCuenta(View V){

        Dialog dialog = new Dialog(MultiActivity.this);
        dialog.setContentView(R.layout.confirm_elimination);

        Button buttonCancel = dialog.findViewById(R.id.ButtonCancel);
        Button buttonDelete = dialog.findViewById(R.id.ButtonEliminar);

        // Si realmente no se quiere borrar la cuenta
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Si se confirma que quiere eliminar su cuenta
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFromDatabase){
                    serviceRESTBBDD.deleteUser(MultiActivity.this,MultiActivity.this);
                }else{
                    serviceREST.deleteUser(MultiActivity.this,MultiActivity.this);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onClickSeeMyData(View V){
        Intent intent = new Intent(context, SeeMyDataActivity.class);
        intent.putExtra("isFromDatabase",isFromDatabase);
        intent.putExtra("userInfo",user);
        context.startActivity(intent);
    }

    public void onClickMyItems(View V){
        Intent intent = new Intent(context, MyItemsActivity.class);
        intent.putExtra("isFromDatabase",isFromDatabase);
        intent.putExtra("userName", userName);
        context.startActivity(intent);
    }

    public void onClickMispersonajes(View V){

    }

    public void onClickVenderCobre(View V){
        Intent intent = new Intent(context, VenderCobreActivity.class);
        intent.putExtra("isFromDatabase",isFromDatabase);
        context.startActivity(intent);
    }

    public void onClickPlay(View V){

    }

    public void getUser(){
        if(isFromDatabase){
            this.serviceRESTBBDD.getUser(this);
        }
        else{
            this.serviceREST.getUser(this);
        }
    }

    @Override
    public void onDeleteUser(){
        Intent intent = new Intent(context, LogInActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onAuthorized() {

    }

    @Override
    public void onUnauthorized() {
        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("authToken");  // Elimina la cookie almacenada con la clave "authToken"
        editor.apply();
        Log.d("Shared Preferences","Cookies Deleted");
    }

    @Override
    public void onMessage(String message){
        Toast.makeText(MultiActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginERROR() {}

    @Override
    public void onLoginOK(User _user){}

    @Override
    public void onUserLoaded(User u) {
        user = u;
    }

    @Override
    public void onCorrectProcess() {
        this.finish();
    }
}