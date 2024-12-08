package com.example.robacobres_androidclient.callbacks;

import com.example.robacobres_androidclient.models.User;

public interface ChargeDataCallback {
    void onChargeFactorM(String factorm);
    void onChargeUser(User u);
    void onMessage(String message);
    void onPurchasOK();
}
