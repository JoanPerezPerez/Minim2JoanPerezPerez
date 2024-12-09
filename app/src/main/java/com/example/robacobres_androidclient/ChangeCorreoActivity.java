package com.example.robacobres_androidclient;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.ChangePassword;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.ServiceBBDD;

public class ChangeCorreoActivity extends AppCompatActivity implements UserCallback {
    EditText actualCorreo;
    EditText newCorreo;
    EditText code;
    private Context context;
    ServiceBBDD service;
    String correoActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_correo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        actualCorreo = findViewById(R.id.ActualCorreoText);
        newCorreo = findViewById(R.id.NewCorreoText);
        code = findViewById(R.id.CodeText);
        context = ChangeCorreoActivity.this;
        service = ServiceBBDD.getInstance(context);
        correoActual = getIntent().getStringExtra("correo");
        actualCorreo.setText(correoActual);
    }

    public void onClickBotonRetroceder(View V) {
        finish();
    }

    public void onClickChange(View V) {
        String newCorreo = this.newCorreo.getText().toString();
        String code = this.code.getText().toString();
        this.service.changeCorreo(newCorreo, code, this);
    }

    public void onClickGetCode(View v) {
        this.service.getCode(this);
    }

    @Override
    public void onLoginOK(User user) {
    }

    @Override
    public void onLoginERROR() {
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        correoActual = newCorreo.getText().toString();
    }

    @Override
    public void onDeleteUser() {
    }

    @Override
    public void onUserLoaded(User u) {
    }

    @Override
    public void onCorrectProcess() {
    }
}