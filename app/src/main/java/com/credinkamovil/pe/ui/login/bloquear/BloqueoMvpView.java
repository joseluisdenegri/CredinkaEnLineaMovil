package com.credinkamovil.pe.ui.login.bloquear;

import com.credinkamovil.pe.ui.base.MvpView;

public interface BloqueoMvpView extends MvpView {
    void onCompletarDatosContacto(String sNumNacional1, String sNumNacional2, String sNumUsaCanada, String sNumRestoMundo);

    void intentLogin();
}
