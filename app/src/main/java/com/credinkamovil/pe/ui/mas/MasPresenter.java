package com.credinkamovil.pe.ui.mas;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.AppMessages;
import com.credinkamovil.pe.utils.StatusService;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MasPresenter<V extends MasMvpView> extends BasePresenter<V> implements MasMvpPresenter<V> {
    private APIServiceHolder mApiServiceHolder = null;
    private AlertDialog mAlertDialog;

    @Inject
    public MasPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        mApiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }
    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        obtenerTipoCambio();
    }
    @Override
    public void onClicCerrarSesion() {
        mAlertDialog = getMvpView().showConfirmDialog("Confimación", AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_0),
                new onClicSi(), "No", "Sí");
    }

    @Override
    public void onSolicitarPermisos() {
        mAlertDialog = getMvpView().showConfirmDialog("Solicitar Permiso", "No esta habilitada la localización,\n ¿Desea habilitarla?",
                new onCliAceptar(), "Cancelar", "Aceptar");
    }
    private void obtenerTipoCambio(){
        try {
            getMvpView().showLoading("Cargando");
            String sUId = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, null);
            getCompositeDisposable().add(getDataManager()
                    .obtenerTipoCambio(sUId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<EnResultService>() {
                                   @Override
                                   public void accept(EnResultService resultService) throws Exception {
                                       if (!isViewAttached())
                                           return;
                                       if (resultService.getiResultado() == StatusService.OK && resultService.getbResultado()) {
                                           JsonElement resultSesion = new Gson().toJsonTree(resultService.getObContent());
                                           JsonObject jsonObject = resultSesion.getAsJsonObject();
                                           String sCompra = String.valueOf(jsonObject.get("nTipoCompra").getAsDouble());
                                           String sVenta = String.valueOf(jsonObject.get("nTipoVenta").getAsDouble());
                                           int iEstado = jsonObject.get("iEstado").getAsInt();
                                           getMvpView().onCompletarDatosTipoCambio(sCompra, sVenta, iEstado);
                                           getMvpView().hideLoading();
                                           mApiServiceHolder.LogoutMobileApp(2);
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
                                       getMvpView().onError(R.string.str_error_service_response_code);
                                   }
                               }

                    )
            );
        } catch (Exception ex) {
            getMvpView().hideLoading();
            ex.getMessage();
            getMvpView().onError(R.string.str_error_service_response_code);
        }
    }
    class onCliAceptar implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mAlertDialog.dismiss();
            getMvpView().onHabilitarGPS(true);
        }
    }
    class onClicSi implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mApiServiceHolder.RegistrarBitacora("Cerrando Sesión", "End");
            mApiServiceHolder.LogoutMobileApp(1);
            //mApiServiceHolder.onFinishAPI();
            getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN);
            getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN_REFRESH);
            getDataManager().removeKey(PreferenceKeys.PREF_UISID);
            mAlertDialog.dismiss();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    getMvpView().onCerrarSesionApp();
                }
            }, 200);
        }
    }
}
