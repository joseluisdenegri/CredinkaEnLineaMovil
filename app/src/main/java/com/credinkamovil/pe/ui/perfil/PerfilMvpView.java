package com.credinkamovil.pe.ui.perfil;

import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.ui.base.MvpView;

public interface PerfilMvpView extends MvpView {
    void onCompletarDatosPerfil(EnSesion sesion);

    void onLogout();
}
