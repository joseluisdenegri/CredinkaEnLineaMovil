package com.credinkamovil.pe.data.models;

public class EnCuotasPendientesCredito {
    private int iNroCuota;
    private String sFechaVencimiento;
    private double nPagoTotal;

    public EnCuotasPendientesCredito(int iNroCuota, String sFechaVenc, double nPagTotal) {
        this.iNroCuota = iNroCuota;
        this.sFechaVencimiento = sFechaVenc;
        this.nPagoTotal = nPagTotal;
    }

    public int getiNroCuota() {
        return iNroCuota;
    }

    public void setiNroCuota(int iNroCuota) {
        this.iNroCuota = iNroCuota;
    }

    public String getsFechaVencimiento() {
        return sFechaVencimiento;
    }

    public void setsFechaVencimiento(String sFechaVencimiento) {
        this.sFechaVencimiento = sFechaVencimiento;
    }

    public double getnPagoTotal() {
        return nPagoTotal;
    }

    public void setnPagoTotal(double nPagoTotal) {
        this.nPagoTotal = nPagoTotal;
    }
}
