package com.credinkamovil.pe.ui.splash;

import com.credinkamovil.pe.di.scope.PerActivity;
import com.credinkamovil.pe.ui.base.MvpPresenter;

@PerActivity
public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {
    void onPingService();

    void onReportAccessDenied();
}
