package com.example.androidretrofit.dataaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConexionRetrofit {

    private static Retrofit retrofit;
    /*
    Nota IP servidor a utilizar (no utilizar localhost)
     GenyMotion: 10.0.3.2
     Emulador android: 10.0.2.2
     Dispositivo real: ip de la computadora
     Otro emulador: buscar en la configuración la ip
     */
    private static final String BASE_URL = "http://10.0.2.2:8001/";


    public static Retrofit getInstanciaRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                    GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
