//package com.example.robacobres_androidclient.services;
//
//import com.example.robacobres_androidclient.models.ChangePassword;
//import com.example.robacobres_androidclient.models.GameCharacter;
//import com.example.robacobres_androidclient.models.Item;
//import com.example.robacobres_androidclient.models.User;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.DELETE;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.PUT;
//import retrofit2.http.Path;
//
//public interface Servidor {
//    //SERVICE USERS
//    //register user
//    @POST("usersBBDD/register")
//    Call<User> registerUser(@Body User user);
//
//    //GetUser
//    @POST("usersBBDD/stats") //OK
//    Call<User> getUser();
//
//    //login user
//    @POST("usersBBDD/login")
//    Call<Void> loginUser(@Body User user);
//
//    //delete user
//    @DELETE("usersBBDD/deleteUser")
//    Call<Void> deleteUser();
//
//    //recover password user
//    @GET("usersBBDD/RecoverPassword/{UserName}")
//    Call<Void> RecoverPassword(@Path("UserName") String username);
//
//    //change password user
//    @PUT("usersBBDD/ChangePassword")
//    Call<Void> UserChangePassword(@Body ChangePassword passwords);
//
//    //SERVICE ITEMS
//    //get all items
//    @GET("itemsBBDD")
//    Call<List<Item>> getItems();
//
//    @GET("storeBBDD/myItems")
//    Call<List<Item>> getMyItems();
//
//    //get a specific item
//    @GET("itemsBBDD/{id}")
//    Call<Item> getItem(@Path("id") String id);
//
//    @GET("usersBBDD/GetCode")
//    Call<Void> getCode();
//
//    //change correo user
//    @PUT("usersBBDD/ChangeMail/{NewCorreo}/{code}")
//    Call<Void> UserChangeCorreo(@Path("NewCorreo") String NewCorreo,@Path("code") String code);
//
//    @GET("usersBBDD/sessionCheck")
//    Call<Void> getSession();
//
//    @GET("usersBBDD/sessionOut")
//    Call<Void> quitSession();
//
//    @GET("usersBBDD/GetMultiplicadorForCobre")
//    Call<String> UserGetsMultiplicador();
//
//    @GET("usersBBDD/stats")
//    Call<User> GetStatsUser();
//
//    @POST("usersBBDD/sellCobre/{KilosCobre}")
//    Call<User> UserSellsCobre(@Path("KilosCobre") Double kiloscobre);
//
//    @POST("storeBBDD/buyItem/{itemName}")
//    Call<List<Item>> userBuysItem(@Path("itemName") String itemName);
//
//    @POST("storeBBDD/buyCharacters/{CharacterName}")
//    Call<List<GameCharacter>> userBuysCharacter(@Path("CharacterName") String CharacterName);
//
//    @GET("storeBBDD/ItemsUserCanBuy")
//    Call<List<Item>> getItemssUserCanBuy();
//
//    @GET ("storeBBDD/CharactersUserCanBuy/{NameUser}")
//    Call<List<GameCharacter>> getCharactersUserCanBuy();
//
//
//}
