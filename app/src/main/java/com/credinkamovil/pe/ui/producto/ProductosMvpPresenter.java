package com.credinkamovil.pe.ui.producto;

import com.credinkamovil.pe.di.scope.PerActivity;
import com.credinkamovil.pe.ui.base.MvpPresenter;
@PerActivity
public interface ProductosMvpPresenter<V extends ProductosMvpView> extends MvpPresenter<V> {
    void onValidateTokenExpired();

    void onOnClicListenerMisCuentas(int position);

    void onOnClicListenerMisCreditos(int position);
}
