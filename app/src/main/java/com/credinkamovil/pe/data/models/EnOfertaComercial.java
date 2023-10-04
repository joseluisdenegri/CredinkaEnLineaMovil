package com.credinkamovil.pe.data.models;

import java.io.Serializable;

public class EnOfertaComercial implements Serializable {
    private int iNumeroRespuesta;
    private String sNombreCliente;
    private String sMoneda;
    private double nMonto;
    private boolean bEstado;
    private String sMensaje;

    public int getiNumeroRespuesta() {
        return iNumeroRespuesta;
    }

    public void setiNumeroRespuesta(int iNumeroRespuesta) {
        this.iNumeroRespuesta = iNumeroRespuesta;
    }

    public String getsNombreCliente() {
        return sNombreCliente;
    }

    public void setsNombreCliente(String sNombreCliente) {
        this.sNombreCliente = sNombreCliente;
    }

    public String getsMoneda() {
        return sMoneda;
    }

    public void setsMoneda(String sMoneda) {
        this.sMoneda = sMoneda;
    }

    public double getnMonto() {
        return nMonto;
    }

    public void setnMonto(double nMonto) {
        this.nMonto = nMonto;
    }

    public boolean isbEstado() {
        return bEstado;
    }

    public void setbEstado(boolean bEstado) {
        this.bEstado = bEstado;
    }

    public String getsMensaje() {
        return sMensaje;
    }

    public void setsMensaje(String sMensaje) {
        this.sMensaje = sMensaje;
    }
}
