package com.credinkamovil.pe.ui.maps;

import com.credinkamovil.pe.ui.base.MvpPresenter;

public interface UbicacionMvpPresenter<V extends UbicacionMvpView> extends MvpPresenter<V> {
    void onInitLoaderData();
}
