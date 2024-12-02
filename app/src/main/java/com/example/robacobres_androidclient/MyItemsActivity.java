package com.example.robacobres_androidclient;

import android.content.Context;
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

import com.example.robacobres_androidclient.adapters.MyItemsAdapter;
import com.example.robacobres_androidclient.callbacks.ItemCallback;
import com.example.robacobres_androidclient.models.Item;
import com.example.robacobres_androidclient.services.Service;

import java.util.ArrayList;
import java.util.List;

public class MyItemsActivity extends AppCompatActivity implements ItemCallback {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Context context;
    Service serviceREST;
    List<Item> obtainedItems;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //VARIABLES MAIN
        this.obtainedItems=new ArrayList<>();

        context=MyItemsActivity.this;

        //INSTANCIA Service
        serviceREST=Service.getInstance(context);

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

        mAdapter = new MyItemsAdapter(context, obtainedItems);
        recyclerView.setAdapter(mAdapter);
        progressBar = findViewById(R.id.progressBar);

        getMyItems();
    }


    public void getMyItems(){
        progressBar.setVisibility(View.VISIBLE);
        serviceREST.getMyItems(this);
    }

    @Override
    public void onPurchaseOk(String idItem) {

    }

    @Override
    public void onItemCallback(List<Item> objects) {
        // Actualizar la lista de items y notificar al adapter
        obtainedItems.clear();
        obtainedItems.addAll(objects);
        mAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onError(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(context,"Error: "+errorMessage,Toast.LENGTH_SHORT).show();
    }

    public void onClickBotonRetroceder(View V){
        finish();
    }
}