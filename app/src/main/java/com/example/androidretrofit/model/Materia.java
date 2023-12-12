package com.example.androidretrofit.model;

import com.google.gson.annotations.SerializedName;

public class Materia {
    @SerializedName("id")
    public int id;

    @SerializedName("nombreMateria")
    public String nombreMateria;

    @SerializedName("estadoMateria")
    public boolean estadoMateria;

    @SerializedName("semestreMateria")
    public int semestreMateria;

    public Materia(int id,
                   String nombreMateria,
                   boolean estadoMateria,
                   int semestreMateria){
        this.id = id;
        this.nombreMateria = nombreMateria;
        this.estadoMateria = estadoMateria;
        this.semestreMateria = semestreMateria;
    }
    public Materia(String nombreMateria,
                   boolean estadoMateria,
                   int semestreMateria){
        this.nombreMateria = nombreMateria;
        this.estadoMateria = estadoMateria;
        this.semestreMateria = semestreMateria;
    }
}
