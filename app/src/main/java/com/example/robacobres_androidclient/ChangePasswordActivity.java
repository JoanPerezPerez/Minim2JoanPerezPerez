package com.example.robacobres_androidclient;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.models.ChangePassword;
import com.example.robacobres_androidclient.models.User;
import com.example.robacobres_androidclient.services.Service;
import com.example.robacobres_androidclient.services.ServiceBBDD;

public class ChangePasswordActivity extends AppCompatActivity implements UserCallback {
    EditText actualPassword;
    EditText newPassword1;
    EditText newPassword2;
    private Context context;
    Service service;
    ServiceBBDD serviceBBDD;
    boolean isFromDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        actualPassword=findViewById(R.id.ActualPasswordText);
        newPassword1=findViewById(R.id.NewPassword1Text);
        newPassword2=findViewById(R.id.NewPassword2Text);
        context=ChangePasswordActivity.this;
        service = Service.getInstance(context);
        serviceBBDD = ServiceBBDD.getInstance(context);
        isFromDatabase = getIntent().getBooleanExtra("isFromDatabase", false);
    }

    public void onClickBotonRetroceder(View V){
        finish();
    }

    public void onClickChange(View V){
        String actualp=actualPassword.getText().toString().trim();
        String new1p=newPassword1.getText().toString().trim();
        String new2p=newPassword2.getText().toString().trim();
        if(actualp.isEmpty()||new1p.isEmpty()||new2p.isEmpty()){
            Toast.makeText(ChangePasswordActivity.this, "RElLENA TODAS LAS CASILLAS", Toast.LENGTH_SHORT).show();
        }else{
            if(new1p.equals(new2p)){
                ChangePassword passwords = new ChangePassword(actualp,new1p);
                if(isFromDatabase){
                    this.serviceBBDD.UserChangePassword(passwords,this);
                }
                else{
                    this.service.UserChangePassword(passwords,this);
                }
            }else{
                Toast.makeText(ChangePasswordActivity.this, "LAS CONTRASEÑAS NUEVAS NO COINCIDEN", Toast.LENGTH_SHORT).show();
            }
        }
        actualPassword.setText("");
        newPassword1.setText("");
        newPassword2.setText("");

    }
    @Override
    public void onLoginOK(User user) {}

    @Override
    public void onLoginERROR() {}

    @Override
    public void onMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteUser() {}

    @Override
    public void onCorrectProcess() {this.finish();}
}