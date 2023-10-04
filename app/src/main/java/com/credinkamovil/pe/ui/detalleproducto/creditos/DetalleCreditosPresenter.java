package com.credinkamovil.pe.ui.detalleproducto.creditos;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnCuotasPendientesCredito;
import com.credinkamovil.pe.data.models.EnParams;
import com.credinkamovil.pe.data.models.EnPrestamo;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.data.models.EnTokenResponse;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.AppMessages;
import com.credinkamovil.pe.utils.StatusService;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class DetalleCreditosPresenter<V extends CreditosMvpView> extends BasePresenter<V> implements CreditosMvpPresenter<V> {
    private static ArrayList<EnCuotasPendientesCredito> oListaCuoPendCredito;
    private AlertDialog mAlertDialog;
    private APIServiceHolder mApiServiceHolder = null;

    @Inject
    public DetalleCreditosPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        mApiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }

    @Override
    public void onObtenerDetalleCreditos(String sNumeroCredito) {
        try {
            getMvpView().showLoading("Cargando");
            String sToken = getDataManager().getStringValue(PreferenceKeys.PREF_KEY_TOKEN, "");
            String[] splitToken = sToken.split(":");
            String sTokenJWT = splitToken[0];
            String sActorJWT = splitToken[1];
            String sTokenRefresh = getDataManager().getStringValue(PreferenceKeys.PREF_KEY_TOKEN_REFRESH, "");
            String sUID = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, null);
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
                            getDetalleCreditosCuenta(sNumeroCredito, sUID);
                        } else {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                        }
                    }, throwable -> {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();
                    })
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            getMvpView().hideLoading();
            getMvpView().onError(R.string.str_error_service_response_code);
        }
    }

    @Override
    public void onDescargarCronograma(String sNumeroCredito) {
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
                            downloadCronograma(sNumeroCredito, sUID);
                        } else {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.str_error_general_servicio);
                        }
                    }, throwable -> {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();
                        getMvpView().onError(R.string.str_error_cargar_info);
                    })
            );
        } catch (Exception ex) {
            getMvpView().hideLoading();
            getMvpView().onError(R.string.str_error_service_response_code);
            ex.printStackTrace();
        }
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    private void getDetalleCreditosCuenta(String sNumeroCredito, String sId) {
        try {
            oListaCuoPendCredito = new ArrayList<>();
            String sSesion = getDataManager().obtenerSesionLogin();
            Gson gson = new Gson();
            EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
            EnParams oEnParams = new EnParams();
            oEnParams.setsValor1(sId);
            oEnParams.setsValor2(sNumeroCredito);
            oEnParams.setsValor3(oEnSesion.getsValor8());
            getCompositeDisposable().add(getDataManager()
                    .obtenerDetalleMovimientoActivo(oEnParams)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<EnResultService>() {
                        @Override
                        public void accept(EnResultService enResultService) throws Exception {
                            if (enResultService.getiResultado() == StatusService.OK && enResultService.getbResultado()) {
                                Gson gson = new GsonBuilder().create();
                                JsonElement jsonElement = gson.toJsonTree(enResultService.getObContent());
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                for (int mov = 0; mov < jsonArray.size(); mov++) {
                                    JsonObject jsonObject = jsonArray.get(mov).getAsJsonObject();
                                    int iNroCuota = jsonObject.get("iNroCuota").getAsInt();
                                    String sFechaVenc = jsonObject.get("sFechaVencimiento").getAsString();
                                    double nPagTotal = jsonObject.get("nPagoTotal").getAsDouble();
                                    EnCuotasPendientesCredito oPendientes = new EnCuotasPendientesCredito(iNroCuota, sFechaVenc, nPagTotal);
                                    oListaCuoPendCredito.add(oPendientes);
                                }
                                String sPrestamo = getDataManager().getStringValue(PreferenceKeys.PREF_CTAS_CRE, null);
                                Gson gsonCta = new Gson();
                                EnPrestamo oEnPrestamo = gsonCta.fromJson(sPrestamo, EnPrestamo.class);
                                getMvpView().onCompletarDetalleCredito(oEnPrestamo, oListaCuoPendCredito);
                                getMvpView().hideLoading();
                                mApiServiceHolder.LogoutMobileApp(2);
                            } else {
                                getMvpView().hideLoading();
                                mAlertDialog = getMvpView().showAlertDialog(enResultService.getsMensaje(), new onClicOk(), false);
                            }
                        }
                    }, throwable -> {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();
                        Log.i("DetCredPresenter", "Error en ObtenerDetalleCreditos" + throwable.getMessage());
                        mApiServiceHolder.LogoutMobileApp(1);
                        mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_102),
                                new onClicOk(), false);
                    })
            );
        } catch (Exception ex) {
            Log.i("DetCreditoPre", "Error en getDetalleCreditosCuenta" + ex.getMessage());
            getMvpView().hideLoading();
            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_102),
                    new onClicOk(), false);
        }
    }

    class onClicOk implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mAlertDialog.dismiss();
            getMvpView().onSinInformacion();
        }
    }

    private void downloadCronograma(String sNumeroCredito, String sUID) {
        try {
            getMvpView().showLoading("Cargando");
            String sNroDoc = getDataManager().getStringValue(PreferenceKeys.PREF_VALUE_DOC, null);
            String sSesion = getDataManager().obtenerSesionLogin();
            Gson gson = new Gson();
            EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
            EnParams oEnParams = new EnParams();
            oEnParams.setsValor1(sUID);
            oEnParams.setsValor2(sNumeroCredito);
            oEnParams.setsValor3(oEnSesion.getsValor8());
            oEnParams.setsValor4(sNroDoc);
            oEnParams.setsValor5(oEnSesion.getsValor1() + " " + oEnSesion.getsValor2() + " " + oEnSesion.getsValor3() + " " + oEnSesion.getsValor4());
            getCompositeDisposable().add(getDataManager()
                    .downloadCronogramaCred(oEnParams)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(responseBody -> {
                        getMvpView().onDownloadCronograma(responseBody.byteStream());
                        getMvpView().hideLoading();
                    }, throwable -> {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();
                        Log.i("DetCredPresenter", "Error en ObtenerDetalleCreditos" + throwable.getMessage());
                        mApiServiceHolder.LogoutMobileApp(1);
                        getMvpView().onError(R.string.str_error_general_servicio);
                    })
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
