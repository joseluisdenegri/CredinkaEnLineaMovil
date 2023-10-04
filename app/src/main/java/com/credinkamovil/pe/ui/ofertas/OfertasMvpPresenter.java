package com.credinkamovil.pe.ui.ofertas;

import com.credinkamovil.pe.di.scope.PerActivity;
import com.credinkamovil.pe.ui.base.MvpPresenter;
@PerActivity
public interface OfertasMvpPresenter<V extends OfertasMvpView> extends MvpPresenter<V> {
    void onObtenerOfertaComercialCredito();
}
