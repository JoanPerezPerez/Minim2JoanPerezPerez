package com.example.robacobres_androidclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Context;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.ServiceBBDD;

public class LogInActivity extends AppCompatActivity implements UserCallback {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private SwitchCompat switchBD;

    ServiceBBDD service;
    User usuario;
    private EditText usernameTextComp;
    private EditText passwordTextComp;

    private ProgressBar progressBar;

    String user;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //BUSCAR COMPONENTS
        usernameTextComp=findViewById(R.id.usernameText);
        passwordTextComp=findViewById(R.id.passwordText);

        progressBar = findViewById(R.id.progressBar);

        //CONTEXT (no estic segur si cal ferho aixi directament MainActivity.this)
        //crec que es equivalent pero mes comode
        context= LogInActivity.this;

        //INSTANCIA SERVICE
        service=ServiceBBDD.getInstance(this.context);
    }

    public void onClickLogin(View v){
        progressBar.setVisibility(View.VISIBLE);
        user=usernameTextComp.getText().toString().trim();
        pass=passwordTextComp.getText().toString().trim();
        this.service.loginUser(user,pass,this);
    }
    public void onClickRegister(View v){
        // Crear un Intent para abrir la nueva actividad
        Intent intent = new Intent(context, RegisterActivity.class);
        // Iniciar la nueva actividad
        context.startActivity(intent);
    }

    public void onClickRecovery(View v){
        // Crear un Intent para abrir la nueva actividad
        Intent intent = new Intent(context, PasswordRecovery.class);
        // Iniciar la nueva actividad
        context.startActivity(intent);
    }


    @Override
    public void onLoginOK(User _user){
        usuario=_user;
        // Crear un Intent para abrir la nueva actividad
        Intent intent = new Intent(context, MultiActivity.class);

        progressBar.setVisibility(View.GONE);
        // Iniciar la nueva actividad
        context.startActivity(intent);
        this.finish();
    }

    @Override
    public void onLoginERROR() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMessage(String message){
        progressBar.setVisibility(View.GONE);
        Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //getAllTracks();
    }

    @Override
    public void onDeleteUser() {}

    @Override
    public void onUserLoaded(User u) {}

    @Override
    public void onCorrectProcess() {
        this.finish();
    }
}