package com.example.robacobres_androidclient.callbacks;

import com.example.robacobres_androidclient.models.User;

public interface UserCallback {
    void onLoginOK(User user);
    void onLoginERROR();
    void onMessage(String message);
    void onDeleteUser();
    void onCorrectProcess();
;   void onUserLoaded(User user);
}
