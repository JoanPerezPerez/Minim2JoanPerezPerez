package com.example.robacobres_androidclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.robacobres_androidclient.callbacks.ItemCallback;
import com.example.robacobres_androidclient.models.Item;
import com.example.robacobres_androidclient.services.Service;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity implements ItemCallback {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Context context;
    String username;
    Service serviceREST;
    List<Item> obtainedItems;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //VARIABLES MAIN
        this.obtainedItems=new ArrayList<>();

        context=ItemsActivity.this;

        //INSTANCIA Service
        serviceREST=Service.getInstance(context);

        this.username = getIntent().getStringExtra("userName");


        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(context,obtainedItems, username ,ItemsActivity.this);
        recyclerView.setAdapter(mAdapter);
        progressBar = findViewById(R.id.progressBar);

        getAllItems();
    }

    public void getAllItems(){
        serviceREST.getAllItems(this);
    }

    @Override
    public void onItemCallback(List<Item> objects) {
        progressBar.setVisibility(View.VISIBLE);
        // Actualizar la lista de items y notificar al adapter
        obtainedItems.clear();
        obtainedItems.addAll(objects);
        mAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(context,"Error: "+errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseOk(String idItem) {
        Toast.makeText(context,"Objeto "+idItem+" comprado!",Toast.LENGTH_SHORT).show();
    }

    public void onClickBotonRetroceder(View V){
        finish();
    }
}