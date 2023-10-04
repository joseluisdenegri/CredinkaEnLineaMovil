package com.credinkamovil.pe.data.models;

public class EnCoordinadas {
    private int iIdAgencia;
    private String sNombreAgencia;
    private double nLatitud;
    private double nLongitud;
    private String sDepartamento;
    private String sProvincia;
    private String sDistrito;
    private String sDireccion;
    public EnCoordinadas(String sNombreAgencia, String sDireccion, double nLatitud, double nLongitud) {
        this.sNombreAgencia = sNombreAgencia;
        this.sDireccion = sDireccion;
        this.nLatitud = nLatitud;
        this.nLongitud = nLongitud;
    }

    public int getiIdAgencia() {
        return iIdAgencia;
    }

    public void setiIdAgencia(int iIdAgencia) {
        this.iIdAgencia = iIdAgencia;
    }

    public String getsNombreAgencia() {
        return sNombreAgencia;
    }

    public void setsNombreAgencia(String sNombreAgencia) {
        this.sNombreAgencia = sNombreAgencia;
    }

    public double getnLatitud() {
        return nLatitud;
    }

    public void setnLatitud(double nLatitud) {
        this.nLatitud = nLatitud;
    }

    public double getnLongitud() {
        return nLongitud;
    }

    public void setnLongitud(double nLongitud) {
        this.nLongitud = nLongitud;
    }

    public String getsDepartamento() {
        return sDepartamento;
    }

    public void setsDepartamento(String sDepartamento) {
        this.sDepartamento = sDepartamento;
    }

    public String getsProvincia() {
        return sProvincia;
    }

    public void setsProvincia(String sProvincia) {
        this.sProvincia = sProvincia;
    }

    public String getsDistrito() {
        return sDistrito;
    }

    public void setsDistrito(String sDistrito) {
        this.sDistrito = sDistrito;
    }

    public String getsDireccion() {
        return sDireccion;
    }

    public void setsDireccion(String sDireccion) {
        this.sDireccion = sDireccion;
    }
}
