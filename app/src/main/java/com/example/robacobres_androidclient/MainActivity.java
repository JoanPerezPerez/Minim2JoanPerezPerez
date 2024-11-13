package com.example.robacobres_androidclient;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements UserCallback {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;

    Service service;
    User usuario;
    private EditText usernameTextComp;
    private EditText passwordTextComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //BUSCAR COMPONENTS
        usernameTextComp=findViewById(R.id.usernameText);
        passwordTextComp=findViewById(R.id.passwordText);

        //INSTANCIA TRACKSERVICE
        service=new Service();

        //CONTEXT (no estic segur si cal ferho aixi directament MainActivity.this)
        //crec que es equivalent pero mes comode
        context=MainActivity.this;
    }

    public void onClickLogin(View v){
        String user=usernameTextComp.getText().toString().trim();
        String pass=passwordTextComp.getText().toString().trim();
        this.service.loginUser(user,pass,this);

    }
    public void onClickRegister(View v){
        // Crear un Intent para abrir la nueva actividad
        Intent intent = new Intent(context, RegisterActivity.class);
        // Iniciar la nueva actividad
        context.startActivity(intent);
    }

    @Override
    public void onLoginCallback(User _user){
        usuario=_user;
        // Crear un Intent para abrir la nueva actividad
        Intent intent = new Intent(context, ItemsActivity.class);

        // Pasar los datos del track seleccionado a la nueva actividad
        intent.putExtra("userId", _user.getId());
        intent.putExtra("userName", _user.getName());
        intent.putExtra("password", _user.getPassword());

        // Iniciar la nueva actividad
        context.startActivity(intent);
    }

    @Override
    public void onMessage(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //getAllTracks();
    }
}