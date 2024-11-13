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

public class RegisterActivity extends AppCompatActivity implements UserCallback {
    Button btnOpenUpload;
    EditText textUsername;
    EditText textPassword;

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

        serviceREST=new Service();
    }

    public void onClick(View V){
        String userName =textUsername.getText().toString().trim();
        String pass= textPassword.getText().toString().trim();
        serviceREST.registerUser(userName,pass,this);
        this.finish();
    }

    @Override
    public void onLoginCallback(User user) {

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