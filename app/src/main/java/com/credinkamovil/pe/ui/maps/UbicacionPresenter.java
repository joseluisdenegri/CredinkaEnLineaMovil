package com.credinkamovil.pe.ui.maps;

import android.util.Log;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnCoordinadas;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.ui.base.BasePresenter;
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

public class UbicacionPresenter<V extends UbicacionMvpView> extends BasePresenter<V> implements UbicacionMvpPresenter<V> {
    @Inject
    public UbicacionPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onInitLoaderData() {
        getListaCoordinadas();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
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
                            Log.i("ProdPresenter", "Error en getListaCoordinadas" + throwable.getMessage());
                            getMvpView().onError(R.string.str_error_general_servicio);
                        }
                    }));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("ProdPresenter", "Error en getListaCoordinadas" + ex.getMessage());
        }
    }
}
