package com.credinkamovil.pe.data.models;

import java.io.Serializable;

public class EnDetalleMovimiento implements Serializable {
    private double nSaldoDisponible;
    private double nSaldoContable;
    private String sConcepto;
    private int iAsientoTrx;
    private String sFechaMovimiento;
    private String sMoneda;
    private String sHoraTrx;
    private double nImporteMov;
    private double nSaldoAct;
    private String sSigno;

    public double getnSaldoDisponible() {
        return nSaldoDisponible;
    }

    public void setnSaldoDisponible(double nSaldoDisponible) {
        this.nSaldoDisponible = nSaldoDisponible;
    }

    public double getnSaldoContable() {
        return nSaldoContable;
    }

    public void setnSaldoContable(double nSaldoContable) {
        this.nSaldoContable = nSaldoContable;
    }

    public String getsConcepto() {
        return sConcepto;
    }

    public void setsConcepto(String sConcepto) {
        this.sConcepto = sConcepto;
    }

    public int getiAsientoTrx() {
        return iAsientoTrx;
    }

    public void setiAsientoTrx(int iAsientoTrx) {
        this.iAsientoTrx = iAsientoTrx;
    }

    public String getsFechaMovimiento() {
        return sFechaMovimiento;
    }

    public void setsFechaMovimiento(String sFechaMovimiento) {
        this.sFechaMovimiento = sFechaMovimiento;
    }

    public String getsMoneda() {
        return sMoneda;
    }

    public void setsMoneda(String sMoneda) {
        this.sMoneda = sMoneda;
    }

    public String getsHoraTrx() {
        return sHoraTrx;
    }

    public void setsHoraTrx(String sHoraTrx) {
        this.sHoraTrx = sHoraTrx;
    }

    public double getnImporteMov() {
        return nImporteMov;
    }

    public void setnImporteMov(double nImporteMov) {
        this.nImporteMov = nImporteMov;
    }

    public double getnSaldoAct() {
        return nSaldoAct;
    }

    public void setnSaldoAct(double nSaldoAct) {
        this.nSaldoAct = nSaldoAct;
    }

    public String getsSigno() {
        return sSigno;
    }

    public void setsSigno(String sSigno) {
        this.sSigno = sSigno;
    }
}
