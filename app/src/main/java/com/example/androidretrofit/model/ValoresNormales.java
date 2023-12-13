package com.example.androidretrofit.model;

import com.google.gson.annotations.SerializedName;

public class ValoresNormales {
    @SerializedName("id")
    public int id;

    @SerializedName("Nombre")
    public String Nombre;

    @SerializedName("valor_min")
    public double valor_min;

    @SerializedName("valor_max")
    public double valor_max;

    @SerializedName("Unidades")
    public String Unidades;

    @SerializedName("RangoNormal")
    public String RangoNormal;

    @SerializedName("estado")
    public Boolean estado;

    public ValoresNormales(int id, String nombre, double valor_min, double valor_max, String unidades, String rangoNormal, Boolean estado) {
        this.id = id;
        this.Nombre = nombre;
        this.valor_min = valor_min;
        this.valor_max = valor_max;
        this.Unidades = unidades;
        this.RangoNormal = rangoNormal;
        this.estado = estado;
    }

    public ValoresNormales(String nombre, double valor_min, double valor_max, String unidades, String rangoNormal, Boolean estado) {
        this.Nombre = nombre;
        this.valor_min = valor_min;
        this.valor_max = valor_max;
        this.Unidades = unidades;
        this.RangoNormal = rangoNormal;
        this.estado = estado;
    }
}
