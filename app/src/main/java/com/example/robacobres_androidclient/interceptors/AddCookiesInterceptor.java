package com.example.robacobres_androidclient.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Recupera la cookie de SharedPreferences
        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String authToken = sharedPref.getString("authToken", null);

        // Si existe una cookie guardada, añadirla a la petición
        Request.Builder builder = chain.request().newBuilder();
        if (authToken != null) {
            builder.addHeader("Cookie", authToken);  // Añade la cookie al encabezado de la solicitud
        }
        return chain.proceed(builder.build());
    }
}

