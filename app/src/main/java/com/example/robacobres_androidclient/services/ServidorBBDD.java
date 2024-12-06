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

public interface ServidorBBDD {
    //SERVICE USERS
    //register user
    @POST("usersBBDD/register") //OK
    Call<User> registerUser(@Body User user);

    //login user
    @POST("usersBBDD/login") //OK
    Call<Void> loginUser(@Body User user);

    //delete user
    @DELETE("usersBBDD/deleteUser") //OK
    Call<Void> deleteUser();

    //recover password user
    @GET("usersBBDD/RecoverPassword/{UserName}")
    Call<Void> RecoverPassword(@Path("UserName") String username);

    //change password user
    @PUT("usersBBDD/ChangePassword")
    Call<Void> UserChangePassword(@Body ChangePassword passwords);

    //SERVICE ITEMS
    //get all items
    @GET("itemsBBDD")
    Call<List<Item>> getItems();

    @GET("storeBBDD/Items/{NameUser}")
    Call<List<Item>> getMyItems(@Path("NameUser") String NameUser);

    //get a specific item
    @GET("itemsBBDD/{ItemName}") //ARREGLAR
    Call<Item> getItem(@Path("ItemName") String ItemName);

    @GET("usersBBDD/sessionCheck")
    Call<Void> getSession();

    @GET("usersBBDD/sessionOut")
    Call<Void> quitSession();


    @POST("storeBBDD/buyItem/{itemName}")
    Call<List<Item>> userBuysItem(@Path("itemName") String itemName);

    @POST("storeBBDD/buyCharacters/{CharacterName}")
    Call<List<GameCharacter>> userBuysCharacter(@Path("CharacterName") String CharacterName);

    @GET("storeBBDD/ItemsUserCanBuy/{NameUser}") //ARREGLAR
    Call<List<Item>> getItemssUserCanBuy(@Path("NameUser") String NameUser);

    @GET("storeBBDD/CharactersUserCanBuy/{NameUser}") //ARREGLAR
    Call<List<GameCharacter>> getCharactersUserCanBuy(@Path("NameUser") String NameUser);
    /*
    //DELETE
    @DELETE("tracks/{id}")
    Call<Void> deleteTrack(@Path("id") String id);


    //PUT
    @PUT("tracks")
    Call<Void> putTrack(@Body Track track);

    //POST
    @POST("tracks")
    Call<Track> postTrack(@Body Track track);
     */
}
