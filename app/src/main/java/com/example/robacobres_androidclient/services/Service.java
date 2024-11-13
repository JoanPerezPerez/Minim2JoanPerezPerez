package com.example.robacobres_androidclient.services;

import android.util.Log;

import com.example.robacobres_androidclient.callbacks.ItemCallback;
import com.example.robacobres_androidclient.callbacks.UserCallback;
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
    Retrofit retrofit;
    Servidor serv;

    public Service(){
        // Es un interceptor que facilita el procés de depuració al mostrar els detalles de les peticions y respostes HTTP al logcat
        HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        //RetroFit
        retrofit =
                new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080/RobaCobres/") //aqui posem lo de localhost bla bla bla
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client) //Permet fer que es vegin les peticions HTTP amb detall al logcat
                        .build();
        serv = retrofit.create(Servidor.class);
    }

    public void registerUser(String _username, String _password, final UserCallback callback){
        User body = new User(_username,_password);
        // Make the POST request
        Call<User> call = serv.registerUser(body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    callback.onLoginCallback(u);
                    callback.onMessage("CONGRATULATIONS, "+u.getName()+" YOU ARE REGISTERED");
                    // Handle success
                    Log.d("API_RESPONSE", "POST SUCCESFULL");
                } else {
                    callback.onMessage("YOU COULD NOT BE REGISTERED, TRY ANOTHER USERNAME");
                    // Handle failure
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle error
                Log.e("API_ERROR", "API call failed", t);
                callback.onMessage("ERROR DUE TO CONNECTION");
            }
        });
    }

    public void loginUser(String _username, String _password, final UserCallback callback){
        User body = new User(_username,_password);
        // Make the POST request
        Call<Void> call = serv.loginUser(body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onMessage("CONGRATULATIONS, YOU ARE IN");
                    callback.onLoginCallback(body);
                    // Handle success
                    Log.d("API_RESPONSE", "POST SUCCESFULL");
                } else {
                    callback.onMessage("TRY AGAIN, YOU COULD NOT BE LOGGED IN");
                    // Handle failure
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle error
                Log.e("API_ERROR", "API call failed", t);
                callback.onMessage("ERROR DUE TO CONNECTION");
            }
        });
    }
    public void deleteUser(String _id){
        Call<Void> call = serv.deleteUser(_id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //CALLBACK
                    Log.d("API_RESPONSE", "DELETE SUCCESFUL");
                } else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the error
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }
    public void getAllItems(final ItemCallback callback){
        Call<List<Item>> call = serv.getItems();

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle the response
                    List<Item> items = response.body();
                    callback.onItemCallback(items);
                    for (Item it : items) {
                        Log.d("API_RESPONSE", "Item Name: " + it.getName());
                    }
                } else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                // Handle the error
                Log.e("API_ERROR", "API call failed", t);
                //callback.onError("ERRORR onFAilure");
            }
        });
    }

    public void getItem(String _id){
        Call<Item> call = serv.getItem(_id);

        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Item i = response.body();
                    // Handle the response
                    Log.d("API_RESPONSE", "Item Name: " + i.getName());
                } else {
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                // Handle the error
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }
}
