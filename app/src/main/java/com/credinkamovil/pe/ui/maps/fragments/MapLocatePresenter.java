package com.credinkamovil.pe.ui.maps.fragments;

import android.util.Log;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnCoordinadas;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.NetworkUtils;
import com.credinkamovil.pe.utils.StatusService;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MapLocatePresenter<V extends MapLocateMvpView> extends BasePresenter<V> implements MapLocateMvpPresenter<V> {
    private APIServiceHolder mApiServiceHolder = null;
    @Inject
    public MapLocatePresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        mApiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }

    @Override
    public void onInitLoaderData() {
        getListaCoordinadas();
    }

    @Override
    public void onErrorCallback() {
        mApiServiceHolder.LogoutMobileApp(1);
    }

    private void getListaCoordinadas() {
        try {
            getMvpView().showLoading("Cargando");
            String sId = getDataManager().getStringValue(PreferenceKeys.PREF_UISID,null);
            getCompositeDisposable().add(getDataManager()
                    .obtenerListaCoordinadas(sId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<EnResultService>() {
                        @Override
                        public void accept(EnResultService resultService) throws Exception {
                            if (resultService.getiResultado() == StatusService.OK && resultService.getbResultado()) {
                                Gson gson = new GsonBuilder().create();
                                JsonElement jsonElement = gson.toJsonTree(resultService.getObContent());
                                ArrayList<EnCoordinadas> oListaCoordinadas = NetworkUtils.parsearListaCoordinadas(jsonElement);
                                getMvpView().onCompletarDatosCoordinadas(oListaCoordinadas);
                                mApiServiceHolder.LogoutMobileApp(2);
                                getMvpView().hideLoading();
                            } else {
                                getMvpView().hideLoading();
                                getMvpView().showDialogCustom(resultService.getsMensaje());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            Log.i("ProdPresenter", "Error en ObtenerMisCuentas" + throwable.getMessage());
                            mApiServiceHolder.LogoutMobileApp(1);
                            getMvpView().onError(R.string.str_error_general_servicio);
                        }
                    }));
        } catch (Exception ex) {
            mApiServiceHolder.LogoutMobileApp(1);
            ex.printStackTrace();
        }
    }
}