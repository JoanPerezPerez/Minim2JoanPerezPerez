package com.example.robacobres_androidclient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.robacobres_androidclient.models.Denunciar;
import com.example.robacobres_androidclient.callbacks.DenunciarCallBack;
import com.example.robacobres_androidclient.services.ServiceBBDD;

public class DenunciarActivity extends AppCompatActivity implements DenunciarCallBack {
    private String userName;
    private EditText Description;
    private EditText Title;
    private Button buttonDenunciar;
    private Button buttonExit;
    private ServiceBBDD serviceREST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        // Inicializar vistas
        this.userName = getIntent().getStringExtra("userName");
        Description = findViewById(R.id.Description);
        Title = findViewById(R.id.Title);
        buttonDenunciar = findViewById(R.id.Denunciar);

        serviceREST = ServiceBBDD.getInstance(this);

        Description.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && Description.getText().toString().equals("Description")) {
                Description.setText("");
            }
        });

        Title.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && Title.getText().toString().equals("Title")) {
                Title.setText("");
            }
        });
        // Configurar el botón
        buttonDenunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDenuncia();
            }
        });
    }
    public void enviarDenuncia(){
        String name = this.userName;
        String title = Title.getText().toString();
        String description = Description.getText().toString();
        if (name == null || name.isEmpty() || description == null || description.isEmpty()|| title.isEmpty()||title==null||title.equals("Title")||description.equals("Description")) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        Denunciar denuncia = new Denunciar(name, title, description);
        // Enviar la denuncia a través del servicio
        serviceREST.postDenuncia(denuncia, new DenunciarCallBack() {
            @Override
            public void onError() {
                Toast.makeText(DenunciarActivity.this, "Error al enviar la denuncia", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMessage(String Message) {
                Log.d("CALLBACK", "Mensaje recibido en el callback: " + Message);
                Toast.makeText(DenunciarActivity.this, Message, Toast.LENGTH_SHORT).show();
                Title.setText("Title");
                Description.setText("Description");
            }
        });
    }
    @Override
    public void onError() {
        // Manejo de errores
    }

    @Override
    public void onMessage(String errorMessage) {
        // Manejo de mensajes de error o éxito
    }

    public void onClickBotonExit(View view){
        this.finish();
    }
}
