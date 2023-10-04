package com.credinkamovil.pe.ui.main;

import androidx.fragment.app.FragmentTransaction;

import com.credinkamovil.pe.ui.base.DialogMvpView;
import com.credinkamovil.pe.ui.base.MvpView;

public interface MainMvpView extends MvpView {
    void initFragment(FragmentTransaction mFragment);

    void showFragmentProducto(FragmentTransaction mFragment);

    void showFragmentOfertas(FragmentTransaction mFragment);

    void showFragmentMas(FragmentTransaction mFragment);

    void intentLoginActivity();

    void finishApplication();
}
