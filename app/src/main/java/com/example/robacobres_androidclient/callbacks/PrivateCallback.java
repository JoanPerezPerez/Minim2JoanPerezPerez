package com.example.robacobres_androidclient.callbacks;

import com.example.robacobres_androidclient.models.ChatIndividual;
import com.example.robacobres_androidclient.models.Forum;
import com.example.robacobres_androidclient.models.User;

import java.util.List;

public interface PrivateCallback {
    void onPrivateCallbackNames(List<User> lista);
    void onError();
    void onMessage(String errorMessage);
    void onPrivateCallbackMessages(List<ChatIndividual> lista);
}
