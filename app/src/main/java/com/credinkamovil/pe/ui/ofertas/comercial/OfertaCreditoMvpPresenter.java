package com.credinkamovil.pe.ui.ofertas.comercial;

import com.credinkamovil.pe.ui.base.MvpPresenter;

public interface OfertaCreditoMvpPresenter<V extends OfertaCreditoMvpView> extends MvpPresenter<V> {
    void onOfertaCredito();
    void onConfirmarCampanaComercial();
}
