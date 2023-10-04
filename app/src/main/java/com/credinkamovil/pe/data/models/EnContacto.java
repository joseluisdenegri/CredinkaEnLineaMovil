package com.credinkamovil.pe.data.models;

import com.google.gson.annotations.SerializedName;

public class EnContacto {
    @SerializedName("iContacto")
    private int iContacto;
    @SerializedName("iTipoContacto")
    private int iTipoContacto;
    @SerializedName("sDescripcion")
    private String sDescripcion;
    @SerializedName("sNumeroTelefono")
    private String sNumeroTelefono;
    @SerializedName("iOrder")
    private int iOrder;
    public EnContacto() {
    }

    public EnContacto(int iContacto, int iTipoContacto, String sDescripcion, String sNumeroTelefono, int iOrder) {
        this.iContacto = iContacto;
        this.iTipoContacto = iTipoContacto;
        this.sDescripcion = sDescripcion;
        this.sNumeroTelefono = sNumeroTelefono;
        this.iOrder = iOrder;
    }
    public int getiContacto() {
        return iContacto;
    }

    public void setiContacto(int iContacto) {
        this.iContacto = iContacto;
    }

    public int getiTipoContacto() {
        return iTipoContacto;
    }

    public void setiTipoContacto(int iTipoContacto) {
        this.iTipoContacto = iTipoContacto;
    }

    public String getsDescripcion() {
        return sDescripcion;
    }

    public void setsDescripcion(String sDescripcion) {
        this.sDescripcion = sDescripcion;
    }

    public String getsNumeroTelefono() {
        return sNumeroTelefono;
    }

    public void setsNumeroTelefono(String sNumeroTelefono) {
        this.sNumeroTelefono = sNumeroTelefono;
    }

    public int getiOrder() {
        return iOrder;
    }

    public void setiOrder(int iOrder) {
        this.iOrder = iOrder;
    }
}
