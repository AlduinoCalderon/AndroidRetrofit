package com.example.androidretrofit.dataaccess;

import com.example.androidretrofit.model.ValoresNormales;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ValoresNormalesAPI {

    @GET("valoresNormales")
    Call<List<ValoresNormales>> getValoresNormales();

    @POST("valoresNormales")
    Call<ValoresNormales> postValoresNormales(@Body ValoresNormales valoresNormales);

    @GET("valoresNormales/{id}")
    Call<ValoresNormales> getValoresNormalesById(@Path("id") String id);

    @PUT("valoresNormales/{id}")
    Call<ValoresNormales> updateValoresNormalesById(
            @Path("id") String id,
            @Body ValoresNormales valoresNormales
    );

    @DELETE("valoresNormales/{id}")
    Call<ValoresNormales> deleteValoresNormalesById(@Path("id") String id);
}
