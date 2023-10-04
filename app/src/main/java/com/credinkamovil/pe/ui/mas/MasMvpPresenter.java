package com.credinkamovil.pe.ui.mas;

import com.credinkamovil.pe.di.scope.PerActivity;
import com.credinkamovil.pe.ui.base.MvpPresenter;
@PerActivity
public interface MasMvpPresenter<V extends MasMvpView> extends MvpPresenter<V> {
    void onClicCerrarSesion();
    void onSolicitarPermisos();
}
