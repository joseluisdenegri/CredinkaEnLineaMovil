package com.credinkamovil.pe.ui.login;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.data.models.EnUsuario;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.AppMessages;
import com.credinkamovil.pe.utils.EncryptionUtils;
import com.credinkamovil.pe.utils.StatusService;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {
    @Inject
    EncryptionUtils mEncryptionUtils;
    private APIServiceHolder apiServiceHolder = null;
    private AlertDialog mAlertDialog;
    private long nCodigoError;

    @Inject
    public LoginPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        apiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }

    @Override
    public void onServerLoginClick(String sNroTarjeta, String sNroDocumento, String sClave) {
        try {
            if (sNroTarjeta.isEmpty() || sNroTarjeta.length() == 0) {
                getMvpView().onError(R.string.str_ingrese_tarjeta);
                return;
            }
            if (sNroDocumento.isEmpty() || sNroDocumento.length() == 0) {
                getMvpView().onError(R.string.str_ingrese_documento);
                return;
            }
            if (sClave.isEmpty() || sClave.length() == 0) {
                getMvpView().onError(R.string.str_ingrese_clave);
                return;
            }
            if (sNroTarjeta.length() < 16) {
                getMvpView().onError(R.string.str_ingrese_td_correcta);
                return;
            }
            if (sNroDocumento.length() < 8) {
                getMvpView().onError(R.string.str_ingrese_documento_valido);
            }
            if (sClave.length() < 6) {
                getMvpView().onError(R.string.str_ingrese_clave_correcta);
                return;
            }
            boolean bEstado = getMvpView().isNetworkConnected();
            if (bEstado) {
                nCodigoError = 0;
                getMvpView().showLoading("Ingresando");
                String sEncryptCard = mEncryptionUtils.BM_EncryptValue(sNroTarjeta);
                String sEncryptNroDoc = mEncryptionUtils.BM_EncryptValue(sNroDocumento);
                int iTipoDoc = 1;
                String sEncryptPing = mEncryptionUtils.BM_EncryptValue(sClave);
                String sUId = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, null);
                EnUsuario oEnUsuario = new EnUsuario(iTipoDoc, sEncryptCard, sEncryptNroDoc, sEncryptPing, sUId);
                getCompositeDisposable().add(getDataManager()
                        .ingreseoBancaMovil(oEnUsuario)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(enResultService -> {
                            if (!isViewAttached())
                                return;
                            if (enResultService.getiResultado() == StatusService.OK && enResultService.getbResultado()) {
                                JsonElement resultSesion = new Gson().toJsonTree(enResultService.getObContent());
                                JsonObject jsonObject = resultSesion.getAsJsonObject();
                                int iTipoResultado = jsonObject.get("iTipoResultado").getAsInt();
                                if (iTipoResultado == 1) {
                                    EnSesion oEnSesion = parsearResultadoLogin(enResultService.getObContent());
                                    Gson gson = new Gson();
                                    String jsonUsuario = gson.toJson(oEnSesion);
                                    getDataManager().registrarSesionLogin(jsonUsuario);
                                    getDataManager().setValue(PreferenceKeys.PREF_KEY_TOKEN, enResultService.getsToken() + ":" + sEncryptNroDoc);
                                    getDataManager().setValue(PreferenceKeys.PREF_KEY_TOKEN_REFRESH, enResultService.getsTokenRefresh());
                                    getMvpView().hideLoading();
                                    apiServiceHolder.RegistrarBitacora("Acceso a banca movil", "Login");
                                    getMvpView().intentMainActivity();
                                }else if (iTipoResultado == 4) {
                                    getMvpView().hideLoading();
                                    getMvpView().onError(R.string.str_error_usuario_bloqueado);
                                } else if (iTipoResultado == 2 || iTipoResultado == 5) {
                                    getMvpView().hideLoading();
                                    getMvpView().onError(R.string.str_error_cuenta_no_existe);
                                } else if (iTipoResultado == 104) {
                                    getMvpView().hideLoading();
                                    getMvpView().onError(R.string.str_sesion_abierta);
                                } else {
                                    getMvpView().hideLoading();
                                    getMvpView().onError(R.string.str_error_clave);
                                }
                            } else {
                                getMvpView().hideLoading();
                                getMvpView().showDialogCustom(enResultService.getsMensaje());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached())
                                    return;
                                apiServiceHolder.LogoutMobileAppExecption(1, nCodigoError);
                                getMvpView().hideLoading();
                                getMvpView().onError(R.string.str_service_not_response);
                            }
                        })
                );
            } else {
                getMvpView().showDialogCustom("Revisa tu conexión a internet e intentalo nuevamente");
            }
        } catch (Exception ex) {
            getMvpView().hideLoading();
            getMvpView().showDialogCustom(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_999));
            ex.printStackTrace();
        }
    }

    @Override
    public void onClicUbicacion() {
        getMvpView().intentUbicacion();
    }

    @Override
    public void onClicBloquearTarjeta() {
        getMvpView().intentBloquearTarjeta();
    }

    @Override
    public void onClicContactos() {
        getMvpView().intentContactos();
    }

    @Override
    public void onClicRecordarTarjeta(String sValor) {
        if (sValor.isEmpty()) {
            getMvpView().onError(R.string.str_ingrese_tarjeta);
            getMvpView().isChecked(1);
        } else {
            if (sValor.length() < 16) {
                getMvpView().onError(R.string.str_ingrese_td_correcta);
                getMvpView().isChecked(1);
            } else {
                getDataManager().setValue(PreferenceKeys.PREF_VALUE_CARD, mEncryptionUtils.BM_EncryptValue(sValor));
            }
        }
    }

    @Override
    public void onClicRecordarDocumento(String sValor) {
        if (sValor.isEmpty()) {
            getMvpView().onError(R.string.str_ingrese_documento);
            getMvpView().isChecked(2);
        } else {
            if (sValor.length() < 8) {
                getMvpView().onError(R.string.str_ingrese_documento_valido);
                getMvpView().isChecked(2);
            } else {
                getDataManager().setValue(PreferenceKeys.PREF_VALUE_DOC, mEncryptionUtils.BM_EncryptValue(sValor));
            }
        }
    }

    @Override
    public void onClicEliminarTarjeta() {
        getDataManager().removeKey(PreferenceKeys.PREF_VALUE_CARD);
    }

    @Override
    public void onClicEliminarDocumento() {
        getDataManager().removeKey(PreferenceKeys.PREF_VALUE_DOC);
    }

    @Override
    public void onClicInitialData() {
        String sNumeroTarjeta = "";
        String sNumeroDocumento = "";
        try {
            sNumeroTarjeta = getDataManager().getStringValue(PreferenceKeys.PREF_VALUE_CARD, null);
            sNumeroDocumento = getDataManager().getStringValue(PreferenceKeys.PREF_VALUE_DOC, null);
            if (sNumeroTarjeta != null) {
                sNumeroTarjeta = mEncryptionUtils.BM_DecryptValue(sNumeroTarjeta);
            } else {
                sNumeroTarjeta = "";
            }
            if (sNumeroDocumento != null) {
                sNumeroDocumento = mEncryptionUtils.BM_DecryptValue(sNumeroDocumento);
            } else {
                sNumeroDocumento = "";
            }
            getMvpView().onCompletarDatosLocal(sNumeroTarjeta, sNumeroDocumento);
        } catch (Exception ex) {
            getMvpView().onCompletarDatosLocal(sNumeroTarjeta, sNumeroDocumento);
            ex.printStackTrace();
        }
    }

    @Override
    public void hideVirtualKeyboard() {
        getMvpView().hideKeyboard();
    }

    @Override
    public void onClicInfoOlvidoClave() {
        getMvpView().onError(R.string.str_info_olvido_clave);
    }

    @Override
    public void onSolicitarPermisos() {
        mAlertDialog = getMvpView().showConfirmDialog("Solicitar Permiso", "No esta habilitada la localización,\n ¿Desea habilitarla?",
                new onCliAceptar(), "Cancelar", "Aceptar");
    }

    @Override
    public void onFinishApplication() {
        getDataManager().removeKey(PreferenceKeys.PREF_SESION);
        getDataManager().removeKey(PreferenceKeys.PREF_VALUE_TIMEOUT);
        getMvpView().onFinishApplication();
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    private EnSesion parsearResultadoLogin(Object pObject) {
        EnSesion oEnSesion = new EnSesion();
        Gson gson = new GsonBuilder().serializeNulls().create();
        JsonElement jsonElement = gson.toJsonTree(pObject);
        JsonObject jsonSesion = jsonElement.getAsJsonObject();
        long nCodigoCliente = jsonSesion.get("nValor0").getAsLong();
        nCodigoError = nCodigoCliente;
        oEnSesion.setnValor0(nCodigoCliente);
        oEnSesion.setsValor1(jsonSesion.get("sValor1").getAsString());
        oEnSesion.setsValor2(jsonSesion.get("sValor2").getAsString());
        oEnSesion.setsValor3(jsonSesion.get("sValor3").getAsString());
        oEnSesion.setsValor4(jsonSesion.get("sValor4").getAsString());
        oEnSesion.setsValor5(jsonSesion.get("sValor5").getAsString());
        oEnSesion.setsValor6(jsonSesion.get("sValor6").getAsString());
        oEnSesion.setsValor7(jsonSesion.get("sValor7").getAsString());
        oEnSesion.setsValor8(mEncryptionUtils.BM_EncryptValue(String.valueOf(nCodigoCliente)));
        oEnSesion.setsValor9(jsonSesion.get("sValor8").getAsString()); //Celular
        oEnSesion.setsValor10(jsonSesion.get("sValor9").getAsString()); //TD
        return oEnSesion;
    }
    class onCliAceptar implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mAlertDialog.dismiss();
            getMvpView().onHabilitarGPS(true);
        }
    }
}
