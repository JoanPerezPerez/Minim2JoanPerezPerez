package com.example.robacobres_androidclient;

import java.util.List;

public interface ItemCallback {
    void onItemCallback(List<Item> objects);
    void onError(String errorMessage);

}
