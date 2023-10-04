package com.credinkamovil.pe.ui.login.contacto;

import com.credinkamovil.pe.data.models.EnContacto;
import com.credinkamovil.pe.ui.base.MvpView;

import java.util.ArrayList;

public interface ContactoMvpView extends MvpView {
    void onCompletarDatosContacto(ArrayList<EnContacto> lista);

    void onLoginActivity();
}
