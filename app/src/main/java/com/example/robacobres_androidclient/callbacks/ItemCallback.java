package com.example.robacobres_androidclient.callbacks;

import com.example.robacobres_androidclient.models.Item;

import java.util.List;

public interface ItemCallback {
    void onItemCallback(List<Item> objects);
    void onPurchaseOk(String idItem);
    void onError(String errorMessage);

}
