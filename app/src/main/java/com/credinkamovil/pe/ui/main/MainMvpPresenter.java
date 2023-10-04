package com.credinkamovil.pe.ui.main;

import androidx.fragment.app.FragmentTransaction;

import com.credinkamovil.pe.di.scope.PerActivity;
import com.credinkamovil.pe.ui.base.MvpPresenter;
@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void onInitFragment(FragmentTransaction mFragment);

    void onClickFragmentProducto(FragmentTransaction mFragment);

    void onClickFragmentOfrecemos(FragmentTransaction mFragment);

    void onClickFragmentMas(FragmentTransaction mFragment);

    void finishApplication();

    void onBackPressed();
}
