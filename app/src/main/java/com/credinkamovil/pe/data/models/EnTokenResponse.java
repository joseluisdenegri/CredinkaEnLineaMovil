package com.credinkamovil.pe.data.models;

public class EnTokenResponse {
    private String sToken;
    private String sTokenRefresh;
    private String sMensaje;
    private int iCodigo;
    private String sUsuario;
    private String sId;

    public EnTokenResponse(String sTokenJWT, String sTokenRefresh, String sUID) {
        this.sToken = sTokenJWT;
        this.sTokenRefresh = sTokenRefresh;
        this.sId = sUID;
    }

    public String getsToken() {
        return sToken;
    }

    public void setsToken(String sToken) {
        this.sToken = sToken;
    }

    public String getsTokenRefresh() {
        return sTokenRefresh;
    }

    public void setsTokenRefresh(String sTokenRefresh) {
        this.sTokenRefresh = sTokenRefresh;
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }

    public int getiCodigo() {
        return iCodigo;
    }

    public void setiCodigo(int iCodigo) {
        this.iCodigo = iCodigo;
    }

    public String getsUsuario() {
        return sUsuario;
    }

    public void setsUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }
}
