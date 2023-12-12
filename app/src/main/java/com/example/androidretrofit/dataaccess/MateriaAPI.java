package com.example.androidretrofit.dataaccess;

import com.example.androidretrofit.model.Materia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MateriaAPI {

    @GET("materia/")
    Call<List<Materia>> getMaterias();

    @POST("materia/")
    Call<Materia> postMateria(@Body Materia materia);

    @GET("materia/{id}")
    Call<Materia> getMateria(@Path("id") String clave);

    @PUT("materia/{id}")
    Call<Materia> putMateria(
            @Path("id") String id,
            @Body Materia materia
                             );

    @DELETE("materia/{id}")
    Call<Materia> deleteMateria(@Path("id") String id);

}
