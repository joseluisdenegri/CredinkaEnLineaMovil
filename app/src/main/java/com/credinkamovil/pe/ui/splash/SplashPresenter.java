package com.credinkamovil.pe.ui.splash;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.BuildConfig;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.utils.AppMessages;
import com.credinkamovil.pe.utils.StatusService;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {
    private AlertDialog mAlertDialog;

    @Inject
    public SplashPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }


    @Override
    public void onPingService() {
        pingService();
    }

    @Override
    public void onReportAccessDenied() {
        Log.d("AAA", "reportAccessDenied");
        mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_99),
                new onClicOK(), false);
    }

    private void pingService() {
        try {
            boolean bEstado = getMvpView().isNetworkConnected();
            if (bEstado) {
                getCompositeDisposable().add(getDataManager()
                        .pingService()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(resultService -> {
                            if (resultService.getiResultado() == StatusService.OK && resultService.getbResultado()) {
                                JsonElement resultSesion = new Gson().toJsonTree(resultService.getObContent());
                                JsonObject jsonObject = resultSesion.getAsJsonObject();
                                if (!jsonObject.get("sVersion").getAsString().equals(BuildConfig.VERSION_NAME)) {
                                    mAlertDialog = getMvpView().showAlertDialog(jsonObject.get("sMensaje").getAsString(),
                                            new onClicOK(), false);
                                } else {
                                    getDataManager().setValue(PreferenceKeys.PREF_VALUE_TIMEOUT, String.valueOf(resultService.getiTimeOut()));
                                    getDataManager().setValue(PreferenceKeys.PREF_UISID, generateID());
                                    veriricarSesion();
                                }
                            } else {
                                mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_999),
                                        new onClicOK(), false);
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_999),
                                    new onClicOK(), false);
                        }));
            } else {
                mAlertDialog = getMvpView().showAlertDialog("Revisa tu conexión a internet e intentalo nuevamente",
                        new onClicOK(), false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_999),
                    new onClicOK(), false);
        }
    }

    class onClicOK implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mAlertDialog.dismiss();
            getMvpView().finishActivity();
        }
    }

    private void veriricarSesion() {
        try {
            String sSesion = getDataManager().obtenerSesionLogin();
            if (sSesion == null) {
                getMvpView().intentLoginActivity();
            } else {
                Gson gson = new Gson();
                EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
                String sUId = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, null);
                EnSesion sesion = new EnSesion();
                sesion.setnValor0(oEnSesion.getnValor0());
                sesion.setsValor2("3");
                sesion.setsValor3("0");
                sesion.setsId(sUId);
                getCompositeDisposable().add(getDataManager().logoutMobileBank(sesion)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<EnResultService>() {
                            @Override
                            public void accept(EnResultService resultService) throws Exception {
                                if (resultService.getiResultado() == 100) {
                                    getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN);
                                    getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN_REFRESH);
                                    getDataManager().removeKey(PreferenceKeys.PREF_SESION);
                                }
                                getMvpView().intentLoginActivity();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("veriricarSesion", "error ========> " + throwable.getMessage());
                                mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_999),
                                        new onClicOK(), false);
                            }
                        })
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("veriricarSesion", Objects.requireNonNull(ex.getMessage()));
            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_999),
                    new onClicOK(), false);
        }
    }
    @SuppressLint("DefaultLocale")
    private String generateID() {
        Random r = new Random();
        int readNum = r.nextInt(1000000);
        return String.format("%06d", readNum);
    }
}
