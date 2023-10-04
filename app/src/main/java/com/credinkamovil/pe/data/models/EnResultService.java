package com.credinkamovil.pe.data.models;

import com.google.gson.annotations.SerializedName;

public class EnResultService {
    @SerializedName("bResultado")
    private Boolean bResultado;
    @SerializedName("iResultado")
    private int iResultado;
    @SerializedName("iCodigoError")
    private int iCodigoError;
    @SerializedName("sMensaje")
    private String sMensaje;
    @SerializedName("sMensajeLargo")
    private String sMensajeLargo;
    @SerializedName("obContent")
    private Object obContent;
    @SerializedName("sToken")
    private String sToken;
    @SerializedName("sTokenRefresh")
    private String sTokenRefresh;
    @SerializedName("bToken")
    private boolean bToken;
    @SerializedName("iTimeOut")
    private int iTimeOut;
    private String byteMap;
    @SerializedName("byteResult")
    private byte[] byteResult;

    public Boolean getbResultado() {
        return bResultado;
    }

    public void setbResultado(Boolean bResultado) {
        this.bResultado = bResultado;
    }

    public int getiResultado() {
        return iResultado;
    }

    public void setiResultado(int iResultado) {
        this.iResultado = iResultado;
    }

    public int getiCodigoError() {
        return iCodigoError;
    }

    public void setiCodigoError(int iCodigoError) {
        this.iCodigoError = iCodigoError;
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }

    public String getsMensajeLargo() {
        return sMensajeLargo;
    }

    public void setsMensajeLargo(String sMensajeLargo) {
        this.sMensajeLargo = sMensajeLargo;
    }

    public Object getObContent() {
        return obContent;
    }

    public void setObContent(Object obContent) {
        this.obContent = obContent;
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

    public boolean isbToken() {
        return bToken;
    }

    public void setbToken(boolean bToken) {
        this.bToken = bToken;
    }

    public int getiTimeOut() {
        return iTimeOut;
    }

    public void setiTimeOut(int iTimeOut) {
        this.iTimeOut = iTimeOut;
    }

    public String getByteMap() {
        return byteMap;
    }

    public void setByteMap(String byteMap) {
        this.byteMap = byteMap;
    }

    public byte[] getByteResult() {
        return byteResult;
    }

    public void setByteResult(byte[] byteResult) {
        this.byteResult = byteResult;
    }

}
