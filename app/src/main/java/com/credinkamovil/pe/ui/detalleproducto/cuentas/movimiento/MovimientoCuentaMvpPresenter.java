package com.credinkamovil.pe.ui.detalleproducto.cuentas.movimiento;

import com.credinkamovil.pe.ui.base.MvpPresenter;

public interface MovimientoCuentaMvpPresenter<V extends MovimientoCuentaMvpView> extends MvpPresenter<V> {
    void onObtenerDatosMovimientoDetalle();
}
