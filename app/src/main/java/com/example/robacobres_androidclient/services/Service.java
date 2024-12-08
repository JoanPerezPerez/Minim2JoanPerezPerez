package com.example.robacobres_androidclient.services;

import android.content.Context;
import android.util.Log;

import com.example.robacobres_androidclient.callbacks.AuthCallback;
import com.example.robacobres_androidclient.callbacks.CharacterCallback;
import com.example.robacobres_androidclient.callbacks.ItemCallback;
import com.example.robacobres_androidclient.callbacks.UserCallback;
import com.example.robacobres_androidclient.interceptors.AddCookiesInterceptor;
import com.example.robacobres_androidclient.interceptors.ReceivedCookiesInterceptor;
import com.example.robacobres_androidclient.models.ChangePassword;
import com.example.robacobres_androidclient.models.GameCharacter;
import com.example.robacobres_androidclient.models.Item;
import com.example.robacobres_androidclient.models.User;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    private static Service instance;
    private Retrofit retrofit;
    private Servidor serv;

    // Private constructor to prevent instantiation from other classes
    private Service(Context context) {

        // Interceptor para loggear las peticiones HTTP (útil para depuración)
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Cliente OkHttp con interceptores de cookies
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))       // Interceptor para agregar cookies en las solicitudes
                .addInterceptor(new ReceivedCookiesInterceptor(context))  // Interceptor para recibir y almacenar cookies
                .addInterceptor(loggingInterceptor)                      // Interceptor para log
                .build();

        // Configurar Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/RobaCobres/")
                //.baseUrl("http://147.83.7.204/RobaCobres/") // LOCAL Base URL
                .addConverterFactory(GsonConverterFactory.create())       // Conversor JSON
                .client(client)                                           // Cliente OkHttp
                .build();

        serv = retrofit.create(Servidor.class);
    }

    // Método público para obtener la única instancia del Singleton Service
    public static Service getInstance(Context context) {
        if (instance == null) {
            synchronized (Service.class) {
                if (instance == null) {
                    instance = new Service(context); // Crear la instancia si es nula
                }
            }
        }
        return instance;
    }

    public void registerUser(String _username, String _password, String _mail, final UserCallback callback) {
        User body = new User(_username, _password, _mail);
        Call<User> call = serv.registerUser(body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();
                if (response.code() == 201) {
                    callback.onCorrectProcess();
                    callback.onMessage("CONGRATULATIONS, " + u.getName() + " YOU ARE REGISTERED");
                } else if (response.code() == 501) {
                    callback.onMessage("USERNAME OR EMAIL ALREADY USED");
                    Log.d("API_RESPONSE", "USERNAMEUSED");
                } else {
                    Log.d("API_RESPONSE", "ERROR");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
                callback.onMessage("ERROR DUE TO CONNECTION");
            }
        });
    }

    public void loginUser(String _username, String _password, final UserCallback callback) {
        User body = new User(_username, _password);
        Call<Void> call = serv.loginUser(body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    callback.onLoginOK(body);
                    Log.d("API_RESPONSE", "POST SUCCESSFUL");
                } else if (response.code() == 501 || response.code() == 502) {
                    callback.onMessage("USER OR PASSWORD WRONG");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                } else {
                    callback.onMessage("ERROR");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
                callback.onMessage("ERROR DUE TO CONNECTION");
                callback.onLoginERROR();
            }
        });
    }

    public void deleteUser(final UserCallback callback, final AuthCallback callback1) {
        Call<Void> call = serv.deleteUser();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("API_RESPONSE", "DELETE SUCCESSFUL");
                    callback.onDeleteUser();
                    callback1.onUnauthorized();
                } else {
                    callback.onMessage("No se ha podido borrar la cuenta");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void RecoverPassword(String user, final UserCallback userCallback){
        Call<Void> call = serv.RecoverPassword(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    userCallback.onMessage("CHECK YOUR MAIL");
                    userCallback.onCorrectProcess();
                    Log.d("API_RESPONSE", "POST SUCCESSFUL");
                } else if (response.code() == 500) {
                    userCallback.onMessage("ERROR");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }else if (response.code() == 501) {
                        userCallback.onMessage("USER DOES NOT EXIST");
                        Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                } else {
                    userCallback.onMessage("ERROR SENDING MAIL");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void getCode(final UserCallback userCallback){
        Call<Void> call = serv.getCode();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    userCallback.onMessage("CHECK YOUR MAIL");
                    userCallback.onCorrectProcess();
                    Log.d("API_RESPONSE", "POST SUCCESSFUL");
                } else if (response.code() == 502) {
                    userCallback.onMessage("ERROR");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }else if (response.code() == 506) {
                    userCallback.onMessage("USER NOT LOGGED IN");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void changeCorreo(String correo, String code,final UserCallback userCallback){
        Call<Void> call = serv.UserChangeCorreo(correo,code);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    userCallback.onMessage("EMAIL CHANGED");
                    userCallback.onCorrectProcess();
                    Log.d("API_RESPONSE", "POST SUCCESSFUL");
                } else if (response.code() == 503) {
                    userCallback.onMessage("WRONG CODE");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }else if (response.code() == 506) {
                    userCallback.onMessage("USER NOT LOGGED IN");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
                else {
                    userCallback.onMessage("ERROR");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void UserChangePassword(ChangePassword passwords, final UserCallback userCallback){
        Call<Void> call = serv.UserChangePassword(passwords);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    userCallback.onMessage("PASSWORD CHANGED");
                    userCallback.onCorrectProcess();
                    Log.d("API_RESPONSE", "PUT SUCCESSFUL");
                } else if (response.code() == 500) {
                    userCallback.onMessage("ERROR");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                } else {
                    userCallback.onMessage("ACTUAL PASSWORD INCORRECT");
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void getAllItems(final ItemCallback callback) {
        Call<List<Item>> call = serv.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> items = response.body();
                    callback.onItemCallback(items);
                    for (Item it : items) {
                        Log.d("API_RESPONSE", "Item Name: " + it.getName() + " Item Price: " + it.getCost());
                    }
                } else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void getItemssUserCanBuy(final ItemCallback callback) {
        Call<List<Item>> call = serv.getItemssUserCanBuy();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.code() == 201) {
                    List<Item> items = response.body();
                    callback.onItemCallback(items);
                    for (Item it : items) {
                        Log.d("API_RESPONSE", "Item Name: " + it.getName() + " Item Price: " + it.getCost());
                    }
                }
                else if (response.code() == 500){
                    Log.d("API_RESPONSE", "ERROR ");
                    callback.onError("Error");
                }

                else if (response.code() == 505) {
                    Log.d("API_RESPONSE", "Not more items to buy");
                    callback.onError("Has comprado todos los items de la tienda!");

                }
                else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                    callback.onError("Error "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
                callback.onError("ERROR WITH CONNECTION");
            }
        });
    }

    public void getCharactersUserCanBuy(final CharacterCallback callback) {
        Call<List<GameCharacter>> call = serv.getCharactersUserCanBuy();
        call.enqueue(new Callback<List<GameCharacter>>() {
            @Override
            public void onResponse(Call<List<GameCharacter>> call, Response<List<GameCharacter>> response) {
                if (response.code() == 201) {
                    List<GameCharacter> characters = response.body();
                    callback.onCharacterCallback(characters);
                    for (GameCharacter g : characters) {
                        Log.d("API_RESPONSE", "Item Name: " + g.getName() + " Item Price: " + g.getCost());
                    }
                }
                else if (response.code() == 500){
                    Log.d("API_RESPONSE", "ERROR ");
                    callback.onError("Error");
                }

                else if (response.code() == 505) {
                    Log.d("API_RESPONSE", "Not more characters to buy");
                    callback.onError("Has comprado todos los items de la tienda!");

                }
                else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                    callback.onError("Error "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<GameCharacter>> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
                callback.onError("ERROR WITH CONNECTION");
            }
        });
    }

    public void getMyItems(final ItemCallback callback) {
        Call<List<Item>> call = serv.getMyItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.code() == 201) {
                    List<Item> items = response.body();
                    callback.onItemCallback(items);
                    for (Item it : items) {
                        Log.d("API_RESPONSE", "Item Name: " + it.getName());
                    }
                }
                else if (response.code() == 501){
                    Log.d("API_RESPONSE", "ERROR USER NOT FOUND ");
                    callback.onError("Error User Not Found");
                }

                else if (response.code() == 502) {
                    Log.d("API_RESPONSE", "No Items");
                    callback.onError("No items");

                }
                else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                    callback.onError("Error "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
                callback.onError("ERROR WITH CONNECTION");
            }
        });
    }

    public void getItem(String _id) {
        Call<Item> call = serv.getItem(_id);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Item i = response.body();
                    Log.d("API_RESPONSE", "Item Name: " + i.getName());
                } else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void getSession(final AuthCallback callback) {
        Call<Void> call = serv.getSession();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==201){
                    callback.onAuthorized();
                    Log.d("API_RESPONSE", "AUTHORIZED" );
                } else {
                    Log.d("API_RESPONSE", "Response not successful, code: ");
                    callback.onUnauthorized();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void quitSession(final AuthCallback callback) {
        Call<Void> call = serv.quitSession();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==201){
                    Log.d("API_RESPONSE", "201: COOKIE QUITED" );
                    callback.onUnauthorized();
                } else {
                    Log.d("API_RESPONSE", "Response not successful, code: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void userBuysItem(String _username, String itemName, final ItemCallback callback) {
        Call<List<Item>> call = serv.userBuysItem(itemName);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.code() == 201) {
                    Log.d("API_RESPONSE", "Item Comprado: " + itemName);
                    callback.onPurchaseOk(itemName);
                    //Actualitza la llista amb els objectes que no te comprats
                    List<Item> itemsnotbought = response.body();
                    callback.onItemCallback(itemsnotbought);
                }
                else if (response.code() == 501){
                    Log.d("API_RESPONSE", "User NOT found ");
                }
                else if(response.code() == 502){
                    Log.d("API_RESPONSE", "Item NOT available");
                }
                else if (response.code() == 503) {
                    Log.d("API_RESPONSE", "Not enough money");
                    callback.onError("Ahorra un poco fuckin pobre!");
                }
                else if (response.code() == 505) {
                    Log.d("API_RESPONSE", "Not more items to buy");
                    callback.onError("Has comprado todos los items de la tienda!");

                }
                else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void userBuysCharacter(String _username, String characterName, final CharacterCallback callback) {
        Call<List<GameCharacter>> call = serv.userBuysCharacter(characterName);
        call.enqueue(new Callback<List<GameCharacter>>() {
            @Override
            public void onResponse(Call<List<GameCharacter>> call, Response<List<GameCharacter>> response) {
                if (response.code() == 201) {
                    Log.d("API_RESPONSE", "Item Comprado: " + characterName);
                    callback.onPurchaseOk(characterName);
                    //Actualitza la llista amb els objectes que no te comprats
                    List<GameCharacter> charactersnotbought = response.body();
                    callback.onCharacterCallback(charactersnotbought);
                }
                else if (response.code() == 501){
                    Log.d("API_RESPONSE", "User NOT found ");
                }
                else if(response.code() == 502){
                    Log.d("API_RESPONSE", "Character NOT available");
                }
                else if (response.code() == 503) {
                    Log.d("API_RESPONSE", "Not enough money");
                    callback.onError("Ahorra un poco, pobre!");
                }
                else if (response.code() == 506) {
                    Log.d("API_RESPONSE", "User not logged in yet");
                    callback.onError("LOGUEATE!");

                }
                else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<GameCharacter>> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void getUser(final UserCallback callback){
        Call<User> call = serv.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 201) {
                    callback.onUserLoaded(response.body()); // Retornem l'usuari via el callback
                } else if (response.code() == 506) {
                    callback.onMessage("User Not Yet Logged");
                    Log.d("API_RESPONSE", "USERNAMEUSED");
                } else {
                    Log.d("API_RESPONSE", "ERROR");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("API_ERROR", "API call failed", t);
                callback.onMessage("ERROR DUE TO CONNECTION");
            }
        });
    }

}


