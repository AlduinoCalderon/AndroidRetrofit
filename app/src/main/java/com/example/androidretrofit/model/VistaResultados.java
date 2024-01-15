package com.example.androidretrofit.model;

import com.google.gson.annotations.SerializedName;

public class VistaResultados {

    @SerializedName("NombrePaciente")
    private String nombrePaciente;

    @SerializedName("FechaRealizacion")
    private String fechaRealizacion;

    @SerializedName("TipoEstudio")
    private String tipoEstudio;

    @SerializedName("ResultadoAnalisis")
    private String resultadoAnalisis;

    @SerializedName("ValorMinimo")
    private double valorMinimo;

    @SerializedName("ValorMaximo")
    private double valorMaximo;

    @SerializedName("RangoNormal")
    private String rangoNormal;

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getTipoEstudio() {
        return tipoEstudio;
    }

    public void setTipoEstudio(String tipoEstudio) {
        this.tipoEstudio = tipoEstudio;
    }

    public String getResultadoAnalisis() {
        return resultadoAnalisis;
    }

    public void setResultadoAnalisis(String resultadoAnalisis) {
        this.resultadoAnalisis = resultadoAnalisis;
    }

    public double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getRangoNormal() {
        return rangoNormal;
    }

    public void setRangoNormal(String rangoNormal) {
        this.rangoNormal = rangoNormal;
    }
}
