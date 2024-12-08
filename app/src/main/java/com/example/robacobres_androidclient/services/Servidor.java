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

    //GetUser
    @POST("usersBBDD/stats") //OK
    Call<User> getUser();

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

    @GET("users/GetCode")
    Call<Void> getCode();

    //change correo user
    @PUT("users/ChangeMail/{NewCorreo}/{code}")
    Call<Void> UserChangeCorreo(@Path("NewCorreo") String NewCorreo,@Path("code") String code);

    @GET("users/sessionCheck")
    Call<Void> getSession();

    @GET("users/sessionOut")
    Call<Void> quitSession();

    @GET("users/GetMultiplicadorForCobre")
    Call<String> UserGetsMultiplicador();

    @GET("users/stats")
    Call<User> GetStatsUser();

    @POST("users/sellCobre/{KilosCobre}")
    Call<User> UserSellsCobre(@Path("KilosCobre") Double kiloscobre);

    @POST("store/buyItem/{itemName}")
    Call<List<Item>> userBuysItem(@Path("itemName") String itemName);

    @POST("store/buyCharacters/{CharacterName}")
    Call<List<GameCharacter>> userBuysCharacter(@Path("CharacterName") String CharacterName);

    @GET("store/ItemsUserCanBuy")
    Call<List<Item>> getItemssUserCanBuy();

    @GET ("store/CharactersUserCanBuy/{NameUser}")
    Call<List<GameCharacter>> getCharactersUserCanBuy();


}
