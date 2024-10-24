package com.example.robacobres_androidclient;

import java.util.List;

public interface ServiceCallback {
    void onServiceCallback(List<Item> tracks);
    void onError(String errorMessage);
}
