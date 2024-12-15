package com.example.robacobres_androidclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.robacobres_androidclient.adapters.MyForumAdapter;
import com.example.robacobres_androidclient.callbacks.ForumCallback;
import com.example.robacobres_androidclient.models.Forum;
import com.example.robacobres_androidclient.services.ServiceBBDD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity implements ForumCallback {

    private RecyclerView recyclerView;
    private MyForumAdapter adapter;
    private List<Forum> forumMessages;
    private TextView textEnviar;
    private ServiceBBDD service;
    private String userName;
    private Button btnSend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        service = ServiceBBDD.getInstance(this);
        // Inicialitzar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.userName = getIntent().getStringExtra("name");
        this.forumMessages = (List<Forum>) getIntent().getSerializableExtra("forumMessages");

        textEnviar=findViewById(R.id.textAEnviar);
        btnSend=findViewById(R.id.btnSend);

        // Configurar l'adaptador
        adapter = new MyForumAdapter(this, forumMessages, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onForumCallback(List<Forum> lista){
        adapter.setForumMessages(lista);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onError(){

    }
    @Override
    public void onMessage(String message){
    }

    public void onClickSend(View view){
        Forum forum = new Forum(userName, textEnviar.getText().toString());
        textEnviar.setText("");
        service.PostInForum(forum,this);
    }

    public void onClickBotonRetroceder(View view){
        this.finish();
    }
}
