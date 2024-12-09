package com.example.robacobres_androidclient;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.robacobres_androidclient.adapters.MyAdapter;
import com.example.robacobres_androidclient.callbacks.CharacterCallback;
import com.example.robacobres_androidclient.callbacks.ChargeDataCallback;
import com.example.robacobres_androidclient.callbacks.ItemCallback;
import com.example.robacobres_androidclient.models.GameCharacter;
import com.example.robacobres_androidclient.models.Item;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.ServiceBBDD;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity implements ItemCallback, CharacterCallback, ChargeDataCallback {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Context context;
    String username;
    ServiceBBDD serviceREST;
    List<Item> obtainedItems;
    List<GameCharacter> obtainedCharacters;
    List<Object> combinedList;
    TextView CoinCount;
    private ProgressBar progressBar;
    boolean hayItems;

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
        this.obtainedCharacters=new ArrayList<>();
        this.combinedList=new ArrayList<>();
        hayItems = false;

        context=ItemsActivity.this;

        //INSTANCIA Service
        serviceREST=ServiceBBDD.getInstance(context);

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

        mAdapter = new MyAdapter(context,combinedList, username ,ItemsActivity.this, ItemsActivity.this);
        recyclerView.setAdapter(mAdapter);
        progressBar = findViewById(R.id.progressBar);
        CoinCount= findViewById(R.id.coint).findViewById(R.id.coinTextView);
        this.serviceREST.GetStatsUser(this);
        getAllItemsUserCanBuy();
        getAllCharactersUserCanBuy();
        UpdateMoney();
    }

    public void getAllItemsUserCanBuy(){
        progressBar.setVisibility(View.VISIBLE);
        hayItems = false;
        serviceREST.getItemssUserCanBuy(this);
    }

    public void getAllCharactersUserCanBuy(){
        progressBar.setVisibility(View.VISIBLE);
        serviceREST.getCharactersUserCanBuy(this);
    }

    public void UpdateMoney(){
        progressBar.setVisibility(View.VISIBLE);
        hayItems = false;
        serviceREST.GetStatsUser(this);
    }

    @Override
    public void onItemCallback(List<Item> objects) {
        // Actualizar la lista de items y notificar al adapter
        obtainedItems.clear();
        obtainedItems.addAll(objects);
        combinedList.clear();  // Esborra la llista combinada anterior
        combinedList.addAll(objects);  // Afegeix els items
        combinedList.addAll(obtainedCharacters);
        mAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onCharacterCallback(List<GameCharacter> objects) {
        // Actualizar la lista de items y notificar al adapter
        obtainedCharacters.clear();
        obtainedCharacters.addAll(objects);
        combinedList.clear();  // Esborra la llista combinada anterior
        combinedList.addAll(obtainedItems);
        combinedList.addAll(objects);
        mAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(context,"Error: "+errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseOk(String idItem) {
        UpdateMoney();
        Toast.makeText(context,"Objeto "+idItem+" comprado!",Toast.LENGTH_SHORT).show();
    }

    public void onClickBotonRetroceder(View V){finish();}

    @Override
    public void onChargeFactorM(String factorm) {}

    @Override
    public void onChargeUser(User u) {
        CoinCount.setText(String.valueOf(u.getMoney()));
    }

    @Override
    public void onMessage(String message){}

    @Override
    public void onPurchasOK(){}
}