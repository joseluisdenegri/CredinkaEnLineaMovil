package com.credinkamovil.pe.ui.perfil;

import android.util.Log;

import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PerfilPresenter<V extends PerfilMvpView> extends BasePresenter<V> implements PerfilMvpPresenter<V> {
    @Inject
    public PerfilPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onCargarDatosPerfil() {
        try {
            getMvpView().showLoading("Cargando");
            String sSesion = getDataManager().obtenerSesionLogin();
            Gson gson = new Gson();
            EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
            getMvpView().onCompletarDatosPerfil(oEnSesion);
            getMvpView().hideLoading();
        } catch (Exception ex) {
            getMvpView().hideLoading();
            Log.i("DatosPerfil", "=================Error en: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void onCerrarSesion() {
        getDataManager().removeKey(PreferenceKeys.PREF_SESION);
        getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN_REFRESH);
        getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN);
        getDataManager().removeKey(PreferenceKeys.PREF_CTAS_CRE);
        getDataManager().removeKey(PreferenceKeys.PREF_CTAS_AHR);
        getDataManager().removeKey(PreferenceKeys.PREF_UISID);
        getMvpView().onLogout();
    }
}
