package com.example.robacobres_androidclient.services;

import com.example.robacobres_androidclient.models.ChangePassword;
import com.example.robacobres_androidclient.models.GameCharacter;
import com.example.robacobres_androidclient.models.Item;
import com.example.robacobres_androidclient.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Servidor {
    //SERVICE USERS
    //register user
    @POST("users/register")
    Call<User> registerUser(@Body User user);

    //login user
    @POST("users/login")
    Call<Void> loginUser(@Body User user);

    //delete user
    @DELETE("users/deleteUser")
    Call<Void> deleteUser();

    //recover password user
    @GET("users/RecoverPassword/{UserName}")
    Call<Void> RecoverPassword(@Path("UserName") String username);

    //change password user
    @PUT("users/ChangePassword")
    Call<Void> UserChangePassword(@Body ChangePassword passwords);

    //SERVICE ITEMS
    //get all items
    @GET("items")
    Call<List<Item>> getItems();

    @GET("store/myItems")
    Call<List<Item>> getMyItems();

    //get a specific item
    @GET("items/{id}")
    Call<Item> getItem(@Path("id") String id);



    @GET("users/sessionCheck")
    Call<Void> getSession();

    @GET("users/sessionOut")
    Call<Void> quitSession();

    @POST("store/buyItem/{itemName}")
    Call<List<Item>> userBuysItem(@Path("itemName") String itemName);

    @POST("store/buyItem/{CharacterName}")
    Call<List<GameCharacter>> userBuysCharacter(@Path("CharacterName") String CharacterName);

    @GET("store/ItemsUserCanBuy")
    Call<List<Item>> getItemssUserCanBuy();


}
