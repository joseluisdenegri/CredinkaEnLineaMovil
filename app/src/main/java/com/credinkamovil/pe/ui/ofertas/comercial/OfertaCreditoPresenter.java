package com.credinkamovil.pe.ui.ofertas.comercial;

import android.util.Log;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnBase;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.utils.EncryptionUtils;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class OfertaCreditoPresenter<V extends OfertaCreditoMvpView> extends BasePresenter<V> implements OfertaCreditoMvpPresenter<V> {
    @Inject
    EncryptionUtils mEncryptionUtils;

    @Inject
    public OfertaCreditoPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onOfertaCredito() {
        getMvpView().showLoading("Cargando");
        getMvpView().onCompletarDatosOferta();
        getMvpView().hideLoading();
    }

    @Override
    public void onConfirmarCampanaComercial() {
        try {
            getMvpView().showLoading("Cargando");
            String sUId = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, null);
            String sSesion = getDataManager().obtenerSesionLogin();
            Gson gson = new Gson();
            EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
            String sNroDoc = mEncryptionUtils.BM_EncryptValue(oEnSesion.getsValor7());
            EnBase oEnBase = new EnBase();
            oEnBase.setsId(sUId);
            oEnBase.setsParams(sNroDoc);
            getCompositeDisposable().add(getDataManager()
                    .confirmarCampanaComercial(oEnBase)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<EnResultService>() {
                        @Override
                        public void accept(EnResultService enResultService) throws Exception {
                            if (!isViewAttached())
                                return;
                            if (enResultService.getbResultado()) {
                                int iRespuesta = (int) enResultService.getObContent();
                                if (iRespuesta == 1) {
                                    getMvpView().onConfirmarCampanaComercial();
                                }
                            }
                            getMvpView().hideLoading();
                        }
                    })
            );
        } catch (Exception ex) {
            getMvpView().hideLoading();
            getMvpView().onError(R.string.str_error_service_response_code);
            Log.i("TAG1", "===========Error en: <onConfirmarCampanaComercial> " + ex.getLocalizedMessage());
        }
    }
}