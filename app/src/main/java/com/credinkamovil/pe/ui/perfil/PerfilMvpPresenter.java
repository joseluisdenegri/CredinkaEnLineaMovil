package com.credinkamovil.pe.ui.perfil;

import com.credinkamovil.pe.ui.base.MvpPresenter;

public interface PerfilMvpPresenter<V extends PerfilMvpView> extends MvpPresenter<V> {
    void onCargarDatosPerfil();

    void onCerrarSesion();
}
