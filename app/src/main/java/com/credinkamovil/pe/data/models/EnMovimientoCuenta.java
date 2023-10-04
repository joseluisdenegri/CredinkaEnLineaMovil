package com.credinkamovil.pe.data.models;

public class EnMovimientoCuenta {
    private long nNumeroCuenta;
    private int iAsiento;
    private String sConcepto;
    private String sHoraTrans;
    private double nImporteMov;
    private double nSaldoActual;
    private String sFechaProceso;
    private int iJtsOid;
    private String sSignoMoneda;
    private String sSigno;

    public EnMovimientoCuenta(long nNumeroCuenta, int iAsiento, String sConcepto, String sHoraTrx, double nImporteMov, double nSaldoActual, String sFecProceso, String sSignoMoneda, String sSigno, int iJtsOid) {
        this.nNumeroCuenta = nNumeroCuenta;
        this.iAsiento = iAsiento;
        this.sConcepto = sConcepto;
        this.sHoraTrans = sHoraTrx;
        this.nImporteMov = nImporteMov;
        this.nSaldoActual = nSaldoActual;
        this.sFechaProceso = sFecProceso;
        this.iJtsOid = iJtsOid;
        this.sSignoMoneda = sSignoMoneda;
        this.sSigno = sSigno;
    }

    public EnMovimientoCuenta() {

    }

    public long getnNumeroCuenta() {
        return nNumeroCuenta;
    }

    public void setnNumeroCuenta(long nNumeroCuenta) {
        this.nNumeroCuenta = nNumeroCuenta;
    }

    public int getiAsiento() {
        return iAsiento;
    }

    public void setiAsiento(int iAsiento) {
        this.iAsiento = iAsiento;
    }

    public String getsConcepto() {
        return sConcepto;
    }

    public void setsConcepto(String sConcepto) {
        this.sConcepto = sConcepto;
    }

    public String getsHoraTrans() {
        return sHoraTrans;
    }

    public void setsHoraTrans(String sHoraTrans) {
        this.sHoraTrans = sHoraTrans;
    }

    public double getnImporteMov() {
        return nImporteMov;
    }

    public void setnImporteMov(double nImporteMov) {
        this.nImporteMov = nImporteMov;
    }

    public double getnSaldoActual() {
        return nSaldoActual;
    }

    public void setnSaldoActual(double nSaldoActual) {
        this.nSaldoActual = nSaldoActual;
    }

    public String getsFechaProceso() {
        return sFechaProceso;
    }

    public void setsFechaProceso(String sFechaProceso) {
        this.sFechaProceso = sFechaProceso;
    }

    public int getiJtsOid() {
        return iJtsOid;
    }

    public void setiJtsOid(int iJtsOid) {
        this.iJtsOid = iJtsOid;
    }

    public String getsSignoMoneda() {
        return sSignoMoneda;
    }

    public void setsSignoMoneda(String sSignoMoneda) {
        this.sSignoMoneda = sSignoMoneda;
    }

    public String getsSigno() {
        return sSigno;
    }

    public void setsSigno(String sSigno) {
        this.sSigno = sSigno;
    }
}
