package com.credinkamovil.pe.data.models;

import com.google.gson.annotations.SerializedName;

public class EnBitacora {
    @SerializedName("sId")
    private String sId;
    @SerializedName("sCrdNum")
    private String sCrdNum;
    @SerializedName("sValor1")
    private String sValor1;
    @SerializedName("sFechaAcceso")
    private String sFechaAcceso;
    @SerializedName("sDescripcion")
    private String sDescripcion;
    @SerializedName("sOperacion")
    private String sOperacion;
    @SerializedName("sHora")
    private String sHora;
    @SerializedName("iTipoAcceso")
    private int iTipoAcceso;
    @SerializedName("sAudMACCreacion")
    private String sAudMACCreacion;
    @SerializedName("sAudIPCreacion")
    private String sAudIPCreacion;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsCrdNum() {
        return sCrdNum;
    }

    public void setsCrdNum(String sCrdNum) {
        this.sCrdNum = sCrdNum;
    }

    public String getsValor1() {
        return sValor1;
    }

    public void setsValor1(String sValor1) {
        this.sValor1 = sValor1;
    }

    public String getsFechaAcceso() {
        return sFechaAcceso;
    }

    public void setsFechaAcceso(String sFechaAcceso) {
        this.sFechaAcceso = sFechaAcceso;
    }

    public String getsDescripcion() {
        return sDescripcion;
    }

    public void setsDescripcion(String sDescripcion) {
        this.sDescripcion = sDescripcion;
    }

    public String getsOperacion() {
        return sOperacion;
    }

    public void setsOperacion(String sOperacion) {
        this.sOperacion = sOperacion;
    }

    public String getsHora() {
        return sHora;
    }

    public void setsHora(String sHora) {
        this.sHora = sHora;
    }

    public int getiTipoAcceso() {
        return iTipoAcceso;
    }

    public void setiTipoAcceso(int iTipoAcceso) {
        this.iTipoAcceso = iTipoAcceso;
    }

    public String getsAudMACCreacion() {
        return sAudMACCreacion;
    }

    public void setsAudMACCreacion(String sAudMACCreacion) {
        this.sAudMACCreacion = sAudMACCreacion;
    }

    public String getsAudIPCreacion() {
        return sAudIPCreacion;
    }

    public void setsAudIPCreacion(String sAudIPCreacion) {
        this.sAudIPCreacion = sAudIPCreacion;
    }
}
