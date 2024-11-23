package com.example.robacobres_androidclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.Service;

public class LogInActivity extends AppCompatActivity implements UserCallback {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;

    Service service;
    User usuario;
    private EditText usernameTextComp;
    private EditText passwordTextComp;

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

        //INSTANCIA TRACKSERVICE
        service=Service.getInstance();

        //CONTEXT (no estic segur si cal ferho aixi directament MainActivity.this)
        //crec que es equivalent pero mes comode
        context= LogInActivity.this;
    }

    public void onClickLogin(View v){
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

    @Override
    public void onLoginOK(User _user){
        usuario=_user;
        // Crear un Intent para abrir la nueva actividad
        Intent intent = new Intent(context, MultiActivity.class);

        // Pasar los datos a la nueva actividad
        intent.putExtra("userId", _user.getId());
        intent.putExtra("userName", _user.getName());
        intent.putExtra("password", _user.getPassword());

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", user);
        editor.putString("password", pass);
        editor.apply(); // Guardar cambios

        // Iniciar la nueva actividad
        context.startActivity(intent);
        this.finish();
    }

    @Override
    public void onLoginERROR() {

    }

    @Override
    public void onMessage(String message){
        Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //getAllTracks();
    }
}