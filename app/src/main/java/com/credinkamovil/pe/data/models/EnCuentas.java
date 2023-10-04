package com.credinkamovil.pe.data.models;

public class EnCuentas {
    private long nCodigoCliente;
    private long nProducto;
    private long nNumeroCuenta;
    private String sSignoMoneda;
    private double nSaldDisp;
    private double nSaldCont;
    private String sFamilia;
    private int iJtsOid;
    private int iSubFam;
    private int iFam;
    private String sNombreProducto;
    private int iCodigoEstado;

    public EnCuentas(long nCodCliente, String sNombProducto, String sFamilia, long nNumCuenta, double nSaldDisp, double nSaldCont, long nProducto, String sMoneda, int iJtsOid, int iSubFam, int iFamilia, int iCodigoEstado) {
        this.nCodigoCliente = nCodCliente;
        this.sNombreProducto = sNombProducto;
        this.sFamilia = sFamilia;
        this.nNumeroCuenta = nNumCuenta;
        this.nSaldDisp = nSaldDisp;
        this.nSaldCont = nSaldCont;
        this.nProducto = nProducto;
        this.sSignoMoneda = sMoneda;
        this.iJtsOid = iJtsOid;
        this.iSubFam = iSubFam;
        this.iFam = iFamilia;
        this.iCodigoEstado = iCodigoEstado;
    }

    public EnCuentas() {

    }

    public long getnCodigoCliente() {
        return nCodigoCliente;
    }

    public void setnCodigoCliente(long nCodigoCliente) {
        this.nCodigoCliente = nCodigoCliente;
    }

    public long getnProducto() {
        return nProducto;
    }

    public void setnProducto(long nProducto) {
        this.nProducto = nProducto;
    }

    public long getnNumeroCuenta() {
        return nNumeroCuenta;
    }

    public void setnNumeroCuenta(long nNumeroCuenta) {
        this.nNumeroCuenta = nNumeroCuenta;
    }

    public String getsSignoMoneda() {
        return sSignoMoneda;
    }

    public void setsSignoMoneda(String sSignoMoneda) {
        this.sSignoMoneda = sSignoMoneda;
    }

    public double getnSaldDisp() {
        return nSaldDisp;
    }

    public void setnSaldDisp(double nSaldDisp) {
        this.nSaldDisp = nSaldDisp;
    }

    public double getnSaldCont() {
        return nSaldCont;
    }

    public void setnSaldCont(double nSaldCont) {
        this.nSaldCont = nSaldCont;
    }

    public String getsFamilia() {
        return sFamilia;
    }

    public void setsFamilia(String sFamilia) {
        this.sFamilia = sFamilia;
    }

    public int getiJtsOid() {
        return iJtsOid;
    }

    public void setiJtsOid(int iJtsOid) {
        this.iJtsOid = iJtsOid;
    }

    public int getiSubFam() {
        return iSubFam;
    }

    public void setiSubFam(int iSubFam) {
        this.iSubFam = iSubFam;
    }

    public int getiFam() {
        return iFam;
    }

    public void setiFam(int iFam) {
        this.iFam = iFam;
    }

    public String getsNombreProducto() {
        return sNombreProducto;
    }

    public void setsNombreProducto(String sNombreProducto) {
        this.sNombreProducto = sNombreProducto;
    }
    public int getiCodigoEstado() { return iCodigoEstado; }
}
