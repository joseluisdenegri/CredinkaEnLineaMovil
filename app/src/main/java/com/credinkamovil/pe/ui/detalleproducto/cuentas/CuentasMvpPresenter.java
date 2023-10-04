package com.credinkamovil.pe.ui.detalleproducto.cuentas;

import com.credinkamovil.pe.ui.base.MvpPresenter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.CuentasMvpView;

public interface CuentasMvpPresenter<V extends CuentasMvpView> extends MvpPresenter<V> {
    void onObtenerMovimientoCuenta(String sNumeroCuenta);

    void onOnClicItemListenerCuentas(int position);

    void onClicDialogCuenta(String sNumeroCuenta);
}
