package com.example.robacobres_androidclient.callbacks;

import com.example.robacobres_androidclient.models.User;

public interface UserCallback {
    void onLoginCallback(User user);
    void onMessage(String message);
}
