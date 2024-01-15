package com.example.androidretrofit.dataaccess;

import com.example.androidretrofit.model.VistaResultados;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VistaResultadosAPI {

    @GET("vistaResultados/{id}")
    Call<ArrayList<VistaResultados>> getVistaResultadosById(@Path("id") String id);


}
