package com.credinkamovil.pe.ui.detalleproducto.creditos;

import com.credinkamovil.pe.data.models.EnPrestamo;
import com.credinkamovil.pe.ui.base.MvpPresenter;

public interface CreditosMvpPresenter<V extends CreditosMvpView> extends MvpPresenter<V> {
    void onObtenerDetalleCreditos(String sNumeroCredito);

    void onDescargarCronograma(String sNumeroCredito);
}
