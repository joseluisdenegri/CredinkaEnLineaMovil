package com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc;

import com.credinkamovil.pe.ui.base.MvpPresenter;

public interface EstadoCuentaMvpPresenter<V extends EstadoCuentaMvpView> extends MvpPresenter<V> {
    void onObtenerPeriodo(String sNumeroCuenta);
}
