package com.example.robacobres_androidclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        mAdapter = new MyAdapter(ItemsActivity.this,obtainedItems, position -> {
            String selectedItem = obtainedItems.get(position).getId();

            // Aquí defines la acción de "comprar"
            //Toast.makeText(this, "Compraste: " + selectedItem, Toast.LENGTH_SHORT).show();

            // Puedes agregar lógica adicional, como:
            // - Enviar el item al carrito
            // - Actualizar el backend
            // - Reducir el inventario, etc.
        }, serviceREST, username );
        recyclerView.setAdapter(mAdapter);

        getAllItems();
    }

    public void getAllItems(){
        serviceREST.getAllItems(this);
    }

    @Override
    public void onItemCallback(List<Item> objects) {
        // Actualizar la lista de tracks y notificar al adapter
        obtainedItems.clear();
        obtainedItems.addAll(objects);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMessage) {

    }
    public void onClickBotonRetroceder(View V){
        finish();
    }
}