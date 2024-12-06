package com.example.robacobres_androidclient.callbacks;

import com.example.robacobres_androidclient.models.GameCharacter;
import com.example.robacobres_androidclient.models.Item;

import java.util.List;

public interface CharacterCallback {
    void onCharacterCallback(List<GameCharacter> objects);
    void onPurchaseOk(String idItem);
    void onError(String errorMessage);

}
