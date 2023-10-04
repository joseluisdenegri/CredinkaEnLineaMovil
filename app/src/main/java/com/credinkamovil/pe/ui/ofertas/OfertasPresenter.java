package com.credinkamovil.pe.ui.ofertas;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnBase;
import com.credinkamovil.pe.data.models.EnOfertaComercial;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.AppMessages;
import com.credinkamovil.pe.utils.StatusService;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class OfertasPresenter<V extends OfertasMvpView> extends BasePresenter<V> implements OfertasMvpPresenter<V> {
    private AlertDialog mAlertDialog;
    private APIServiceHolder mApiServiceHolder = null;

    @Inject
    public OfertasPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        mApiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }

    @Override
    public void onObtenerOfertaComercialCredito() {
        try {
            getMvpView().showLoading("Cargando");
            String sDocumento = getDataManager().getStringValue(PreferenceKeys.PREF_VALUE_DOC, null);
            String sUID = getDataManager().getStringValue(PreferenceKeys.PREF_UISID,null);
            EnBase oEnBase = new EnBase();
            oEnBase.setsId(sUID);
            oEnBase.setsParams(sDocumento);
            getCompositeDisposable().add(getDataManager()
                    .obtenerCampanaComercial(oEnBase)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<EnResultService>() {
                        @Override
                        public void accept(EnResultService resultService) throws Exception {
                            if (resultService.getiResultado() == StatusService.OK && resultService.getbResultado()) {
                                EnOfertaComercial ofertaComercial = parsearResultadoOfertaCredito(resultService.getObContent());
                                if (ofertaComercial.getiNumeroRespuesta() == 1 && ofertaComercial.isbEstado()) {
                                    getMvpView().onMostrarOfertaCreditos(ofertaComercial);
                                    getMvpView().hideLoading();
                                    mApiServiceHolder.LogoutMobileApp(2);
                                } else {
                                    getMvpView().hideLoading();
                                    getMvpView().showDialogCustom(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_101));
                                }
                            } else {
                                getMvpView().hideLoading();
                                getMvpView().showDialogCustom(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_101));
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.str_service_not_response);
                        }
                    })
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private EnOfertaComercial parsearResultadoOfertaCredito(Object pObject) {
        EnOfertaComercial oEnOfertaComercial = new EnOfertaComercial();
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            JsonElement jsonElement = gson.toJsonTree(pObject);
            JsonObject jsonSesion = jsonElement.getAsJsonObject();
            oEnOfertaComercial.setsNombreCliente(jsonSesion.get("sNombreCliente").getAsString());
            oEnOfertaComercial.setnMonto(jsonSesion.get("nMonto").getAsDouble());
            oEnOfertaComercial.setsMoneda(jsonSesion.get("sMoneda").getAsString());
            oEnOfertaComercial.setiNumeroRespuesta(jsonSesion.get("iNumeroRespuesta").getAsInt());
            oEnOfertaComercial.setbEstado(jsonSesion.get("bEstado").getAsBoolean());
            oEnOfertaComercial.setsMensaje(jsonSesion.get("sMensaje").getAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return oEnOfertaComercial;
    }
}
