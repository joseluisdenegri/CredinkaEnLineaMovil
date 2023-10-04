package com.credinkamovil.pe.data.models;

import com.google.gson.annotations.SerializedName;

public class EnUsuario {
    @SerializedName("sId")
    private String sId;
    @SerializedName("nCodigoCliente")
    private long nCodigoCliente;
    @SerializedName("sCodigoCliente")
    private String sCodigoCliente;
    @SerializedName("iTipoDocumento")
    private int iTipoDocumento;
    @SerializedName("sNumeroDocumento")
    private String sNumeroDocumento;
    @SerializedName("sPing")
    private String sPing;
    @SerializedName("sCrdNum")
    private String sCrdNum;
    @SerializedName("sIp")
    private String sIp;
    @SerializedName("sMac")
    private String sMac;
    @SerializedName("iTipoResultado")
    private int iTipoResultado;
    @SerializedName("iTimeout")
    private int iTimeout;

    public EnUsuario() {
    }

    public EnUsuario(int iTipoDoc, String sEncryptCard, String sEncryptNroDoc, String sEncryptPing, String sUiSId) {
        this.iTipoDocumento = iTipoDoc;
        this.sCrdNum = sEncryptCard;
        this.sNumeroDocumento = sEncryptNroDoc;
        this.sPing = sEncryptPing;
        this.sId=sUiSId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public long getnCodigoCliente() {
        return nCodigoCliente;
    }

    public void setnCodigoCliente(long nCodigoCliente) {
        this.nCodigoCliente = nCodigoCliente;
    }

    public String getsCodigoCliente() {
        return sCodigoCliente;
    }

    public void setsCodigoCliente(String sCodigoCliente) {
        this.sCodigoCliente = sCodigoCliente;
    }

    public int getiTipoDocumento() {
        return iTipoDocumento;
    }

    public void setiTipoDocumento(int iTipoDocumento) {
        this.iTipoDocumento = iTipoDocumento;
    }

    public String getsNumeroDocumento() {
        return sNumeroDocumento;
    }

    public void setsNumeroDocumento(String sNumeroDocumento) {
        this.sNumeroDocumento = sNumeroDocumento;
    }

    public String getsPing() {
        return sPing;
    }

    public void setsPing(String sPing) {
        this.sPing = sPing;
    }

    public String getsCrdNum() {
        return sCrdNum;
    }

    public void setsCrdNum(String sCrdNum) {
        this.sCrdNum = sCrdNum;
    }

    public String getsIp() {
        return sIp;
    }

    public void setsIp(String sIp) {
        this.sIp = sIp;
    }

    public String getsMac() {
        return sMac;
    }

    public void setsMac(String sMac) {
        this.sMac = sMac;
    }

    public int getiTipoResultado() {
        return iTipoResultado;
    }

    public void setiTipoResultado(int iTipoResultado) {
        this.iTipoResultado = iTipoResultado;
    }

    public int getiTimeout() {
        return iTimeout;
    }

    public void setiTimeout(int iTimeout) {
        this.iTimeout = iTimeout;
    }
}
