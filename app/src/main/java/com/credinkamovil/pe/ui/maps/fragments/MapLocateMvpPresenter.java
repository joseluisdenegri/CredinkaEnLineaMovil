package com.credinkamovil.pe.ui.maps.fragments;

import com.credinkamovil.pe.ui.base.MvpPresenter;

public interface MapLocateMvpPresenter<V extends MapLocateMvpView> extends MvpPresenter<V> {
    void onInitLoaderData();

    void onErrorCallback();
}
