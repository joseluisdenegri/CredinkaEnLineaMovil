package com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc;

import com.credinkamovil.pe.data.models.EnPeriodo;
import com.credinkamovil.pe.ui.base.MvpView;

import java.io.InputStream;
import java.util.ArrayList;

public interface EstadoCuentaMvpView extends MvpView {
    void showFragmentDetalleCuentas();

    void onCompletarDatosPeriodo(ArrayList<EnPeriodo> listaPeriodo, String sNumeroCuenta);

    void onDownloadEstadoCuenta(InputStream inputStream);
}
