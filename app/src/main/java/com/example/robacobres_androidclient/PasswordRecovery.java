package com.example.robacobres_androidclient;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.ServiceBBDD;

import android.os.Handler;
import android.os.Looper;

public class PasswordRecovery extends AppCompatActivity implements UserCallback{

    EditText textUsername;
    private Context context;
    ServiceBBDD service;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_password_recovery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textUsername=findViewById(R.id.usernameText);
        context= PasswordRecovery.this;
        service=ServiceBBDD.getInstance(this.context);
        progressBar = findViewById(R.id.progressBar);
    }
    public void onClickBotonRetroceder(View V){
        finish();
    }

    public void onClickRecover(View V){
        progressBar.setVisibility(View.VISIBLE);
        String user=textUsername.getText().toString().trim();
        this.service.RecoverPassword(user,this);
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onLoginOK(User user) {}

    @Override
    public void onLoginERROR() {}

    @Override
    public void onMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteUser() {}

    @Override
    public void onUserLoaded(User u) {}

    @Override
    public void onCorrectProcess() {this.finish();}

}