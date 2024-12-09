package com.example.robacobres_androidclient;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.robacobres_androidclient.callbacks.ChargeDataCallback;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.ServiceBBDD;

public class VenderCobreActivity extends AppCompatActivity implements ChargeDataCallback {
    TextView CobreCount;
    TextView CoinCount;
    TextView FactorMulti;
    EditText CobreACambiar;
    TextView DineroObtenido;
    ImageButton BotonTransaccion;
    Button BotonVender;
    private Context context;
    ServiceBBDD service;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vender_cobre);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context=VenderCobreActivity.this;
        service = ServiceBBDD.getInstance(context);
        CobreCount= findViewById(R.id.cobre).findViewById(R.id.cobreTextView);
        CoinCount= findViewById(R.id.coint).findViewById(R.id.coinTextView);
        FactorMulti = findViewById(R.id.FactorMultiplicador);
        View transaccion = findViewById(R.id.transaccion);
        CobreACambiar = transaccion.findViewById(R.id.cobreEditText);
        DineroObtenido = transaccion.findViewById(R.id.coinTextView);
        BotonTransaccion = transaccion.findViewById(R.id.arrowButton);
        BotonVender = findViewById(R.id.btn_vender);
        progressBar = findViewById(R.id.progressBar);


        UpdateMultiplicador();
        UpdateUserInfo();

    }

    public void UpdateMultiplicador(){
        progressBar.setVisibility(View.VISIBLE);
        service.UserGetsMultiplicador(this);
        progressBar.setVisibility(View.GONE);
    }

    public void UpdateUserInfo(){
        progressBar.setVisibility(View.VISIBLE);
        service.GetStatsUser(this);
        progressBar.setVisibility(View.GONE);
    }

    public void onClickVender(View V){
        progressBar.setVisibility(View.VISIBLE);
        try {
            double cobreacambiar = Double.parseDouble(CobreACambiar.getText().toString().trim());
            if(cobreacambiar!=0){
                service.UserSellsCobre(cobreacambiar,this);
            }else{
                Toast.makeText(context, "Por favor introduzca un valor que no sea 0.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Por favor introduzca un valor numérico válido.", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.GONE);
    }

    public void onClickBotonRetroceder(View V){
        finish();
    }

    public void onClickTransaccion(View v) {
        String cobreInput = CobreACambiar.getText().toString().trim();

        if (cobreInput.isEmpty()) {
            Toast.makeText(context, "Por favor introduzca los kg de cobre que quiere vender!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            double kgCobre = Double.parseDouble(cobreInput);
            double factor = Double.parseDouble(FactorMulti.getText().toString());
            double moneyGain = kgCobre * factor;
            DineroObtenido.setText(String.valueOf(moneyGain));
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Por favor introduzca un valor numérico válido.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChargeFactorM(String factorm) {
        FactorMulti.setText(factorm);
    }

    @Override
    public void onChargeUser(User u) {
        CobreCount.setText(String.valueOf(u.getCobre()));
        CoinCount.setText(String.valueOf(u.getMoney()));
    }

    @Override
    public void onPurchasOK() {
        //UpdateUserInfo();
        //CobreACambiar.setText("");
        //DineroObtenido.setText("");
    }

    @Override
    public void onMessage(String message){
        Toast.makeText(VenderCobreActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}