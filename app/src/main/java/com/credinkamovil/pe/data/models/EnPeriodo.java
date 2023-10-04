package com.credinkamovil.pe.data.models;

public class EnPeriodo {
    private int iCodigo;
    private int iAnio;
    private String sMes;
    private String sFechaInicio;
    private String sFechaFin;

    public EnPeriodo(int iCodigo, int iAnio, String sMes, String sFechaInicio, String sFechaFin) {
        this.iCodigo = iCodigo;
        this.iAnio = iAnio;
        this.sMes = sMes;
        this.sFechaInicio = sFechaInicio;
        this.sFechaFin = sFechaFin;
    }

    public int getiCodigo() {
        return iCodigo;
    }

    public void setiCodigo(int iCodigo) {
        this.iCodigo = iCodigo;
    }

    public int getiAnio() {
        return iAnio;
    }

    public void setiAnio(int iAnio) {
        this.iAnio = iAnio;
    }

    public String getsMes() {
        return sMes;
    }

    public void setsMes(String sMes) {
        this.sMes = sMes;
    }

    public String getsFechaInicio() {
        return sFechaInicio;
    }

    public void setsFechaInicio(String sFechaInicio) {
        this.sFechaInicio = sFechaInicio;
    }

    public String getsFechaFin() {
        return sFechaFin;
    }

    public void setsFechaFin(String sFechaFin) {
        this.sFechaFin = sFechaFin;
    }
}
