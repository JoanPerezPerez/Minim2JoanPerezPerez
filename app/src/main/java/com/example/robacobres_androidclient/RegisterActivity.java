package com.example.robacobres_androidclient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.Service;

public class RegisterActivity extends AppCompatActivity implements UserCallback {
    Button btnOpenUpload;
    EditText textUsername;
    EditText textPassword;
    EditText textPassword2;

    Service serviceREST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnOpenUpload = findViewById(R.id.btn_register);
        textUsername=findViewById(R.id.usernameText);
        textPassword=findViewById(R.id.passwordText);
        textPassword2 = findViewById(R.id.passwordText2);

        serviceREST = Service.getInstance();
    }

    public void onClick(View V){
        String userName = textUsername.getText().toString().trim();
        String pass = textPassword.getText().toString().trim();
        String pass2 = textPassword2.getText().toString().trim();

        // Validamos que el nombre de usuario no esté vacío
        if (userName.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "El nombre de usuario no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validamos que las contraseñas coincidan
        if (!pass.equals(pass2)) {
            Toast.makeText(RegisterActivity.this, "Error, las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        serviceREST.registerUser(userName, pass,  this );
    }

    @Override
    public void onLoginOK(User user) {
        this.finish();
    }

    @Override
    public void onLoginERROR() {

    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SecondaryActivity", "Activity destroyed");
    }


}