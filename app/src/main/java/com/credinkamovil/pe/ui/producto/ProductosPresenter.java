package com.credinkamovil.pe.ui.producto;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnBase;
import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnPrestamo;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.data.models.EnTokenResponse;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.AppMessages;
import com.credinkamovil.pe.utils.EncryptionUtils;
import com.credinkamovil.pe.utils.NetworkUtils;
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

public class ProductosPresenter<V extends ProductosMvpView> extends BasePresenter<V> implements ProductosMvpPresenter<V> {
    private static ArrayList<EnCuentas> oListaMisCuentas;
    private static ArrayList<EnPrestamo> oListaMisCreditos;
    private AlertDialog mAlertDialog;
    @Inject
    EncryptionUtils mEncryptionUtils;
    private final APIServiceHolder mApiServiceHolder;

    @Inject
    public ProductosPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        mApiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }

    @Override
    public void onValidateTokenExpired() {
        try {
            getMvpView().showLoading("Cargando");
            String sToken = getDataManager().getStringValue(PreferenceKeys.PREF_KEY_TOKEN, "");
            String sUID = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, "");
            String[] splitToken = sToken.split(":");
            String sTokenJWT = splitToken[0];
            String sActorJWT = splitToken[1];
            String sTokenRefresh = getDataManager().getStringValue(PreferenceKeys.PREF_KEY_TOKEN_REFRESH, "");
            EnTokenResponse oEnTokenResponse = new EnTokenResponse(sTokenJWT, sTokenRefresh, sUID);
            getCompositeDisposable().add(getDataManager()
                    .validateTokenExpired(oEnTokenResponse)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<EnResultService>() {
                        @Override
                        public void accept(EnResultService resultService) throws Exception {
                            if (resultService.getbResultado() && resultService.getiResultado() == StatusService.OK) {
                                if (resultService.isbToken()) {
                                    getDataManager().setValue(PreferenceKeys.PREF_KEY_TOKEN, resultService.getsToken() + ":" + sActorJWT);
                                    getDataManager().setValue(PreferenceKeys.PREF_KEY_TOKEN_REFRESH, resultService.getsTokenRefresh());
                                }
                                onObtenerMisCuentas();
                            } else {
                                if (!isViewAttached())
                                    return;
                                getMvpView().hideLoading();
                                mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_999),
                                        new onClicOK(), false);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_999),
                                    new onClicOK(), false);
                        }
                    }));
        } catch (Exception ex) {
            getMvpView().hideLoading();
            getMvpView().onError(R.string.str_error_service_response_code);
            Log.i("TAG1", "===========Error en: <validateTokenExpired> " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void onOnClicListenerMisCuentas(int position) {
        try {
            String sNumeroCuenta = String.valueOf(oListaMisCuentas.get(position).getnNumeroCuenta());
            String sEncrypNroCuenta = mEncryptionUtils.BM_EncryptValue(sNumeroCuenta);
            NetworkUtils.observableListCuenta(oListaMisCuentas, Long.parseLong(sNumeroCuenta), getDataManager());
            getMvpView().showFragmentDetalleMisCuentas(sEncrypNroCuenta);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onOnClicListenerMisCreditos(int position) {
        try {
            String sNumeroCredito = String.valueOf(oListaMisCreditos.get(position).getnNumeroCredito());
            NetworkUtils.observableListCredito(oListaMisCreditos, Long.parseLong(sNumeroCredito), getDataManager());
            getMvpView().showFragmentDetalleMisCreditos(mEncryptionUtils.BM_EncryptValue(sNumeroCredito));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void onObtenerMisCuentas() {
        try {
            oListaMisCuentas = new ArrayList<>();
            String sUId = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, null);
            String sSesion = getDataManager().obtenerSesionLogin();
            Gson gson = new Gson();
            EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
            EnBase oEnBase = new EnBase();
            oEnBase.setsId(sUId);
            oEnBase.setsParams(oEnSesion.getsValor8());
            getCompositeDisposable().add(getDataManager()
                    .obtenerCuentasPasivo(oEnBase)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<EnResultService>() {
                        @Override
                        public void accept(EnResultService enResultService) throws Exception {
                            if (enResultService.getiResultado() == StatusService.OK && enResultService.getbResultado()) {
                                Gson gson = new GsonBuilder().create();
                                JsonElement jsonElement = gson.toJsonTree(enResultService.getObContent());
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                for (int cta = 0; cta < jsonArray.size(); cta++) {
                                    JsonObject jsonObject = jsonArray.get(cta).getAsJsonObject();
                                    int iCodigoEstado = jsonObject.get("iCodigoEstado").getAsInt();
                                    if (iCodigoEstado == 0) {
                                        long nCodigoCliente = jsonObject.get("nCodigoCliente").getAsLong();
                                        String sNomProducto = jsonObject.get("sNombreProducto").getAsString();
                                        String sFamilia = jsonObject.get("sFamilia").getAsString();
                                        String sNumCta = jsonObject.get("sValor1").getAsString();
                                        long nNumeroCuenta = 0;
                                        try {
                                            nNumeroCuenta = Long.parseLong(mEncryptionUtils.BM_DecryptValue(sNumCta));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        double nSaldDisp = jsonObject.get("nSaldDisp").getAsDouble();
                                        double nSaldCont = jsonObject.get("nSaldCont").getAsDouble();
                                        long nProducto = jsonObject.get("nProducto").getAsLong();
                                        String sMoneda = jsonObject.get("sSignoMoneda").getAsString();
                                        int iJtsOid = jsonObject.get("iJtsOid").getAsInt();
                                        int iSubFam = jsonObject.get("iSubFam").getAsInt();
                                        int iFam = jsonObject.get("iFam").getAsInt();
                                        EnCuentas cuentas = new EnCuentas(nCodigoCliente, sNomProducto, sFamilia, nNumeroCuenta, nSaldDisp, nSaldCont, nProducto, sMoneda, iJtsOid, iSubFam, iFam, iCodigoEstado);
                                        oListaMisCuentas.add(cuentas);
                                    }
                                }
                                mApiServiceHolder.LogoutMobileApp(2);
                            }
                            onObtenerMisCreditos(oEnSesion, sUId);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            Log.i("ProdPresenter", "Error en ObtenerMisCuentas" + throwable.getMessage());
                            mApiServiceHolder.LogoutMobileApp(1);
                            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_999),
                                    new onClicOK(), false);
                        }
                    })
            );
        } catch (Exception ex) {
            getMvpView().hideLoading();
            getMvpView().onError(R.string.str_error_service_response_code);
            Log.i("ProdPresenter", "Error en ObtenerMisCuentas" + ex.getMessage());
        }
    }

    class onClicOK implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN);
            getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN_REFRESH);
            getDataManager().removeKey(PreferenceKeys.PREF_SESION);
            mAlertDialog.dismiss();
            getMvpView().intentLoginActivity();
        }
    }

    private void onObtenerMisCreditos(EnSesion poEnSesion, String sUId) {
        try {
            oListaMisCreditos = new ArrayList<>();
            EnBase oEnBase = new EnBase();
            oEnBase.setsId(sUId);
            oEnBase.setsParams(poEnSesion.getsValor8());
            getCompositeDisposable().add(getDataManager()
                    .obtenerCuentasActivo(oEnBase)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<EnResultService>() {
                        @Override
                        public void accept(EnResultService enResultService) throws Exception {
                            if (enResultService.getiResultado() == StatusService.OK && enResultService.getbResultado()) {
                                Gson gson = new GsonBuilder().create();
                                JsonElement jsonElement = gson.toJsonTree(enResultService.getObContent());
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                for (int x = 0; x < jsonArray.size(); x++) {
                                    JsonObject jsonObject = jsonArray.get(x).getAsJsonObject();
                                    String sNumCre = jsonObject.get("sValor1").getAsString();
                                    long nNumCredito = 0;
                                    try {
                                        nNumCredito = Long.parseLong(mEncryptionUtils.BM_DecryptValue(sNumCre));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    String sNomProducto = jsonObject.get("sNombreProducto").getAsString();
                                    double nSaldCap = jsonObject.get("nSaldoCapital").getAsDouble();
                                    String sEstado = jsonObject.get("sEstado").getAsString();
                                    double nMontPrest = jsonObject.get("nMontoPrestamo").getAsDouble();
                                    int iTotCuota = jsonObject.get("iTotalCuotas").getAsInt();
                                    int iCuotasPag = jsonObject.get("iCuotasPagadas").getAsInt();
                                    String sSignoMoneda = jsonObject.get("sSignoMoneda").getAsString();
                                    long nCodProducto = jsonObject.get("nProducto").getAsLong();
                                    String sTipoCred = jsonObject.get("sTipoCredito").getAsString();
                                    double nTasaInteres = jsonObject.get("nTasaInters").getAsDouble();
                                    int iNroDiasAtr = jsonObject.get("iNroDiasAtrasado").getAsInt();
                                    int iJtsOid = jsonObject.get("iJtsOid").getAsInt();
                                    String sFechaDesembol = jsonObject.get("sFechaDesem").getAsString();

                                    EnPrestamo prestamo = new EnPrestamo(nNumCredito, sNomProducto, nSaldCap, sEstado, nMontPrest, iTotCuota, iCuotasPag, sSignoMoneda, nCodProducto, sTipoCred, nTasaInteres, iNroDiasAtr, iJtsOid, sFechaDesembol);
                                    oListaMisCreditos.add(prestamo);
                                }
                            }
                            getMvpView().onCompletarDatosLista(oListaMisCuentas, oListaMisCreditos);
                            getMvpView().hideLoading();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            mApiServiceHolder.LogoutMobileApp(1);
                            Log.i("ProdPresenter", "Error en ObtenerMisCuentas" + throwable.getMessage());
                            getMvpView().onError(R.string.str_error_general_servicio);
                        }
                    })
            );
            onConsultarCampanaComercial(poEnSesion, sUId);
        } catch (Exception ex) {
            getMvpView().hideLoading();
            getMvpView().onError(R.string.str_error_service_response_code);
            Log.i("ProdPresenter", "Error en onObtenerMisCreditos" + ex.getMessage());
        }
    }

    private void onConsultarCampanaComercial(EnSesion oEnSesion, String sUId) {
        try {
            String sValid = getDataManager().getStringValue(PreferenceKeys.PREF_VALID_CAMPANA, "");
            if (sValid.equals("1")) {
                String sNroDoc = mEncryptionUtils.BM_EncryptValue(oEnSesion.getsValor7());
                EnBase oEnBase = new EnBase();
                oEnBase.setsId(sUId);
                oEnBase.setsParams(sNroDoc);
                getCompositeDisposable().add(getDataManager()
                        .obtenerCampanaComercial(oEnBase)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<EnResultService>() {
                            @Override
                            public void accept(EnResultService enResultService) throws Exception {
                                if (!isViewAttached())
                                    return;
                                if (enResultService.getbResultado()) {
                                    JsonElement resultSesion = new Gson().toJsonTree(enResultService.getObContent());
                                    JsonObject jsonObject = resultSesion.getAsJsonObject();
                                    int iRespuesta = jsonObject.get("iNumeroRespuesta").getAsInt();
                                    String sNombreCliente = jsonObject.get("sNombreCliente").getAsString();
                                    String sMoneda = jsonObject.get("sMoneda").getAsString();
                                    double nMonto = jsonObject.get("nMonto").getAsDouble();
                                    boolean bEstado = jsonObject.get("bEstado").getAsBoolean();
                                    String sMensaje = jsonObject.get("sMensaje").getAsString();
                                    if (iRespuesta == 1 && bEstado) {
                                        mAlertDialog = getMvpView().showDialogOnboarding("Oferta Comercial", "FINANCIERA CREDINKA \n\n" + sMensaje,
                                                new onConfirmarCampana(), new onCancelCampana(), "Cancelar", "Sí");
                                    }
                                }
                            }
                        })
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class onConfirmarCampana implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            try {
                mAlertDialog.dismiss();
                String sSesion = getDataManager().obtenerSesionLogin();
                Gson gson = new Gson();
                EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
                String sUId = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, null);
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
                                        getDataManager().setValue(PreferenceKeys.PREF_VALID_CAMPANA, "1");
                                    }
                                } else {
                                    getDataManager().setValue(PreferenceKeys.PREF_VALID_CAMPANA, "");
                                }
                            }
                        })
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class onCancelCampana implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            getDataManager().setValue(PreferenceKeys.PREF_VALID_CAMPANA, "1");
            mAlertDialog.dismiss();
        }
    }
}
