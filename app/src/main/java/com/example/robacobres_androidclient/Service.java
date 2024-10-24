package com.example.robacobres_androidclient;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    Retrofit retrofit;
    Servidor serv;

    public Service(){
        //RetroFit
        retrofit =
                new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080/dsaApp/") //aqui posem lo de localhost bla bla bla
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        serv = retrofit.create(Servidor.class);
    }

    public void registerUser(String _username, String _password){
        User body = new User(_username,_password);
        // Make the POST request
        Call<User> call = serv.registerUser(body);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User u = response.body();
                    //POSAR CALLBACK
                    // Handle success
                    Log.d("API_RESPONSE", "POST SUCCESFULL");
                } else {
                    // Handle failure
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle error
                Log.e("API_ERROR", "API call failed", t);
            }
        });
    }

    public void loginUser(String _username, String _password){
        User body = new User(_username,_password);
        // Make the POST request
        Call<Void> call = serv.loginUser(body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //POSAR CALLBACK
                    // Handle success
                    Log.d("API_RESPONSE", "POST SUCCESFULL");
                } else {
                    // Handle failure
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle error
                Log.e("API_ERROR", "API call failed", t);
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
    public void getAllItems(final ServiceCallback callback){
        Call<List<Item>> call = serv.getItems();

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> items = response.body();
                    //callback.onTracksLoaded(items);
                    // Handle the response
                    for (Item it : items) {
                        Log.d("API_RESPONSE", "Item Name: " + it.name);
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
                    Log.d("API_RESPONSE", "Item Name: " + i.name);
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
