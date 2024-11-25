package com.example.robacobres_androidclient.interceptors;import okhttp3.Interceptor;
import okhttp3.Response;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import android.content.Context;
import android.content.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.Response;
import java.io.IOException;

public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        // Verifica si el servidor ha enviado cookies en las cabeceras de la respuesta
        if (!response.headers("Set-Cookie").isEmpty()) {
            // Obtén la cookie de la cabecera
            String cookie = response.header("Set-Cookie");

            if (cookie != null) {
                // Guarda la cookie en SharedPreferences
                SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                Log.d("INTERCEPTOR","Cookie "+cookie+" agregada");
                editor.putString("authToken", cookie);  // Almacena la cookie con la clave authToken
                editor.apply();  // Confirma los cambios
            }
        }

        return response;
    }
}

