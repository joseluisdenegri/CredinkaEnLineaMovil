package com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnParams;
import com.credinkamovil.pe.data.models.EnPeriodo;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.data.models.EnTokenResponse;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.adapters.PeriodoAdapter;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.AppMessages;
import com.credinkamovil.pe.utils.EncryptionUtils;
import com.credinkamovil.pe.utils.NetworkUtils;
import com.credinkamovil.pe.utils.StatusService;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.text.MessageFormat;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class EstadoCuentaPresenter<V extends EstadoCuentaMvpView> extends BasePresenter<V>
        implements EstadoCuentaMvpPresenter<V>, PeriodoAdapter.CallbackPeriodo {
    private AlertDialog mAlertDialog;
    private APIServiceHolder mApiServiceHolder = null;
    @Inject
    EncryptionUtils mEncryptionUtils;

    @Inject
    public EstadoCuentaPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        mApiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }

    @Override
    public void onObtenerPeriodo(String sNumeroCuenta) {
        try {
            getMvpView().showLoading("Cargando");
            String sToken = getDataManager().getStringValue(PreferenceKeys.PREF_KEY_TOKEN, "");
            String[] splitToken = sToken.split(":");
            String sTokenJWT = splitToken[0];
            String sActorJWT = splitToken[1];
            String sTokenRefresh = getDataManager().getStringValue(PreferenceKeys.PREF_KEY_TOKEN_REFRESH, "");
            String sUID = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, "");
            EnTokenResponse oEnTokenResponse = new EnTokenResponse(sTokenJWT, sTokenRefresh, sUID);
            getCompositeDisposable().add(getDataManager()
                    .validateTokenExpired(oEnTokenResponse)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(resultService -> {
                        if (resultService.getbResultado() && resultService.getiResultado() == StatusService.OK) {
                            if (resultService.isbToken()) {
                                getDataManager().setValue(PreferenceKeys.PREF_KEY_TOKEN, resultService.getsToken() + ":" + sActorJWT);
                                getDataManager().setValue(PreferenceKeys.PREF_KEY_TOKEN_REFRESH, resultService.getsTokenRefresh());
                            }
                            obtenerListaPeriodo(sUID, sNumeroCuenta);
                        } else {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            getMvpView().showFragmentDetalleCuentas();
                        }
                    }, throwable -> {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();
                        getMvpView().showFragmentDetalleCuentas();
                    }));
        } catch (Exception ex) {
            Log.i("onObtenerPeriodo", "Error en onObtenerPeriodo" + ex.getMessage());
            getMvpView().hideLoading();
            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_102),
                    new onClicOk(), false);
            getMvpView().onError(R.string.str_error_service_response_code);
        }
    }

    private void obtenerListaPeriodo(String sUID, String sNumeroCuenta) {
        try {
            getCompositeDisposable().add(getDataManager()
                    .obtenerListaPeriodo(sUID)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(enResultService -> {
                        if (enResultService.getiResultado() == StatusService.OK && enResultService.getbResultado()) {
                            Gson gson = new GsonBuilder().create();
                            JsonElement jsonElement = gson.toJsonTree(enResultService.getObContent());
                            ArrayList<EnPeriodo> listaPeriodo = NetworkUtils.httpParsearPeriodo(jsonElement);
                            getMvpView().onCompletarDatosPeriodo(listaPeriodo, sNumeroCuenta);
                            getMvpView().hideLoading();
                        } else {
                            getMvpView().hideLoading();
                            mAlertDialog = getMvpView().showAlertDialog(enResultService.getsMensaje(), new onClicOk(), false);
                        }
                    }, throwable -> {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();
                        mAlertDialog = getMvpView().showAlertDialog("Lo sentimos, la información solicitada no se generó. Estamos trabajando para solucionar el inconveniente.",
                                new onClicOk(), false);
                    })
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDownItemClicked(EnPeriodo periodo, String sNroCta) {
        try {
            getMvpView().showLoading("Cargando");
            String sToken = getDataManager().getStringValue(PreferenceKeys.PREF_KEY_TOKEN, "");
            String[] splitToken = sToken.split(":");
            String sTokenJWT = splitToken[0];
            String sActorJWT = splitToken[1];
            String sTokenRefresh = getDataManager().getStringValue(PreferenceKeys.PREF_KEY_TOKEN_REFRESH, "");
            String sUID = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, "");
            EnTokenResponse oEnTokenResponse = new EnTokenResponse(sTokenJWT, sTokenRefresh, sUID);
            getCompositeDisposable().add(getDataManager()
                    .validateTokenExpired(oEnTokenResponse)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(resultService -> {
                        if (resultService.getbResultado() && resultService.getiResultado() == StatusService.OK) {
                            if (resultService.isbToken()) {
                                getDataManager().setValue(PreferenceKeys.PREF_KEY_TOKEN, resultService.getsToken() + ":" + sActorJWT);
                                getDataManager().setValue(PreferenceKeys.PREF_KEY_TOKEN_REFRESH, resultService.getsTokenRefresh());
                            }
                            descargarEstadoCuenta(sUID, periodo, sNroCta);
                        } else {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            getMvpView().showFragmentDetalleCuentas();
                        }
                    }, throwable -> {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();
                        getMvpView().showFragmentDetalleCuentas();
                    }));
        } catch (Exception ex) {
            Log.i("onDownItemClicked", "Error en onDownItemClicked" + ex.getMessage());
            getMvpView().hideLoading();
            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_102),
                    new onClicOk(), false);
            getMvpView().onError(R.string.str_error_service_response_code);
        }
    }

    class onClicOk implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mAlertDialog.dismiss();
            getMvpView().showFragmentDetalleCuentas();
        }
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    private void descargarEstadoCuenta(String sId, EnPeriodo periodo, String sNroCta) {
        try {
            getMvpView().showLoading("Cargando");
            String sSesion = getDataManager().obtenerSesionLogin();
            Gson gson = new Gson();
            EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
            EnParams oParams = new EnParams();
            oParams.setsValor1(oEnSesion.getsValor8());
            oParams.setsValor2(sNroCta);
            oParams.setsValor3(sId);
            oParams.setsValor4(MessageFormat.format("{0} {1} {2} {3}", oEnSesion.getsValor1(), oEnSesion.getsValor2(), oEnSesion.getsValor3(), oEnSesion.getsValor4()));
            oParams.setsValor5(mEncryptionUtils.BM_EncryptValue(oEnSesion.getsValor7()));
            oParams.setsValor6(String.valueOf(periodo.getiCodigo()));
            getCompositeDisposable().add(getDataManager()
                    .downloadEstadoCuentaPasivo(oParams)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(responseBody -> {
                        getMvpView().onDownloadEstadoCuenta(responseBody.byteStream());
                        getMvpView().hideLoading();
                    }, throwable -> {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();
                        Log.i("downEECC_ahr", "Error en descargarEstadoCuenta" + throwable.getMessage());
                        mApiServiceHolder.LogoutMobileApp(1);
                        getMvpView().onError(R.string.str_error_general_servicio);
                    })
            );
        } catch (Exception ex) {
            Log.i("downEECC", "Error en descargarEstadoCuenta" + ex.getMessage());
            getMvpView().hideLoading();
            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_102),
                    new onClicOk(), false);
            getMvpView().onError(R.string.str_error_service_response_code);
        }
    }
}
