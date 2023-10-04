package com.credinkamovil.pe.ui.login;

import com.credinkamovil.pe.ui.base.MvpView;

public interface LoginMvpView extends MvpView {
    void intentMainActivity();

    void intentUbicacion();

    void intentBloquearTarjeta();

    void intentContactos();

    void onCompletarDatosLocal(String sValorTD, String sValorDoc);

    void isChecked(int option);

    void onHabilitarGPS(boolean flagGps);

    void onFinishApplication();
}
