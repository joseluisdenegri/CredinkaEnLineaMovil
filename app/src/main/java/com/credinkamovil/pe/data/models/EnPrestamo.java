package com.credinkamovil.pe.data.models;

public class EnPrestamo {
    private long nNumeroCredito;
    private String sNombreProducto;
    private double nSaldoCapital;
    private String sEstado;
    private double nMontoPrestamo;
    private int iTotalCuotas;
    private int iCuotasPagadas;
    private String sSignoMoneda;
    private long nProducto;
    private String sTipoCredito;
    private double nTasaInters;
    private int iNroDiasAtrasado;
    private int iJtsOid;
    private String sFechaDesem;

    public EnPrestamo(long nNumCredito, String sNomProducto, double nSaldCap, String sEstado, double nMontPrest, int iTotCuota, int iCuotasPag, String sSignoMoneda, long nCodProducto, String sTipoCred, double nTasaInteres, int iNroDiasAtr, int iJtsOid, String sFechaDesembol) {
        this.nNumeroCredito = nNumCredito;
        this.sNombreProducto = sNomProducto;
        this.nSaldoCapital = nSaldCap;
        this.sEstado = sEstado;
        this.nMontoPrestamo = nMontPrest;
        this.iTotalCuotas = iTotCuota;
        this.iCuotasPagadas = iCuotasPag;
        this.sSignoMoneda = sSignoMoneda;
        this.nProducto = nCodProducto;
        this.sTipoCredito = sTipoCred;
        this.nTasaInters = nTasaInteres;
        this.iNroDiasAtrasado = iNroDiasAtr;
        this.iJtsOid = iJtsOid;
        this.sFechaDesem = sFechaDesembol;
    }

    public EnPrestamo() {

    }

    public long getnNumeroCredito() {
        return nNumeroCredito;
    }

    public void setnNumeroCredito(long nNumeroCredito) {
        this.nNumeroCredito = nNumeroCredito;
    }

    public String getsNombreProducto() {
        return sNombreProducto;
    }

    public void setsNombreProducto(String sNombreProducto) {
        this.sNombreProducto = sNombreProducto;
    }

    public double getnSaldoCapital() {
        return nSaldoCapital;
    }

    public void setnSaldoCapital(double nSaldoCapital) {
        this.nSaldoCapital = nSaldoCapital;
    }

    public String getsEstado() {
        return sEstado;
    }

    public void setsEstado(String sEstado) {
        this.sEstado = sEstado;
    }

    public double getnMontoPrestamo() {
        return nMontoPrestamo;
    }

    public void setnMontoPrestamo(double nMontoPrestamo) {
        this.nMontoPrestamo = nMontoPrestamo;
    }

    public int getiTotalCuotas() {
        return iTotalCuotas;
    }

    public void setiTotalCuotas(int iTotalCuotas) {
        this.iTotalCuotas = iTotalCuotas;
    }

    public int getiCuotasPagadas() {
        return iCuotasPagadas;
    }

    public void setiCuotasPagadas(int iCuotasPagadas) {
        this.iCuotasPagadas = iCuotasPagadas;
    }

    public String getsSignoMoneda() {
        return sSignoMoneda;
    }

    public void setsSignoMoneda(String sSignoMoneda) {
        this.sSignoMoneda = sSignoMoneda;
    }

    public long getnProducto() {
        return nProducto;
    }

    public void setnProducto(long nProducto) {
        this.nProducto = nProducto;
    }

    public String getsTipoCredito() {
        return sTipoCredito;
    }

    public void setsTipoCredito(String sTipoCredito) {
        this.sTipoCredito = sTipoCredito;
    }

    public double getnTasaInters() {
        return nTasaInters;
    }

    public void setnTasaInters(double nTasaInters) {
        this.nTasaInters = nTasaInters;
    }

    public int getiNroDiasAtrasado() {
        return iNroDiasAtrasado;
    }

    public void setiNroDiasAtrasado(int iNroDiasAtrasado) {
        this.iNroDiasAtrasado = iNroDiasAtrasado;
    }

    public int getiJtsOid() {
        return iJtsOid;
    }

    public void setiJtsOid(int iJtsOid) {
        this.iJtsOid = iJtsOid;
    }

    public String getsFechaDesem() {
        return sFechaDesem;
    }

    public void setsFechaDesem(String sFechaDesem) {
        this.sFechaDesem = sFechaDesem;
    }
}
