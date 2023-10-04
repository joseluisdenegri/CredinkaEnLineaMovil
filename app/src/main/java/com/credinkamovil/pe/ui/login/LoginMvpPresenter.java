package com.credinkamovil.pe.ui.login;

import com.credinkamovil.pe.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {
    void onServerLoginClick(String sNroTarjeta, String sNroDocumento, String sClave);

    void onClicUbicacion();

    void onClicBloquearTarjeta();

    void onClicContactos();

    void onClicRecordarTarjeta(String sValor);

    void onClicRecordarDocumento(String sValor);

    void onClicEliminarTarjeta();

    void onClicEliminarDocumento();

    void onClicInitialData();

    void hideVirtualKeyboard();

    void onClicInfoOlvidoClave();

    void onSolicitarPermisos();

    void onFinishApplication();
}
