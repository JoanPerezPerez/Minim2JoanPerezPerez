package com.example.robacobres_androidclient;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Service service;

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
    }

    public void onClickLogin(View v){
        String user=usernameTextComp.getText().toString().trim();
        String pass=passwordTextComp.getText().toString().trim();
        this.service.loginUser(user,pass);
    }
    public void onClickRegister(View v){
        String user=usernameTextComp.getText().toString().trim();
        String pass=passwordTextComp.getText().toString().trim();
        this.service.registerUser(user,pass);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //getAllTracks();
    }
}