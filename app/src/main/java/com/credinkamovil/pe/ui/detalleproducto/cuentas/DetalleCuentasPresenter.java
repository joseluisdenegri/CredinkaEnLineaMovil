package com.credinkamovil.pe.ui.detalleproducto.cuentas;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnDetalleMovimiento;
import com.credinkamovil.pe.data.models.EnMovimientoCuenta;
import com.credinkamovil.pe.data.models.EnParams;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.data.models.EnTokenResponse;
import com.credinkamovil.pe.data.models.ItemOption;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.AppConstantes;
import com.credinkamovil.pe.utils.AppMessages;
import com.credinkamovil.pe.utils.AppUtils;
import com.credinkamovil.pe.utils.EncryptionUtils;
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

public class DetalleCuentasPresenter<V extends CuentasMvpView> extends BasePresenter<V> implements CuentasMvpPresenter<V> {
    private static ArrayList<EnMovimientoCuenta> oListaDetalleMovimiento;
    @Inject
    EncryptionUtils mEncryptionUtils;
    private APIServiceHolder mApiServiceHolder = null;
    private AlertDialog mAlertDialog;

    @Inject
    public DetalleCuentasPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        mApiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }

    @Override
    public void onObtenerMovimientoCuenta(String sNumeroCuenta) {
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
                            getMovimientoCuenta(sNumeroCuenta, sUID);
                        } else {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            getMvpView().showFragmentProducto();
                        }
                    }, throwable -> {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();
                        getMvpView().showFragmentProducto();
                    }));
        } catch (Exception ex) {
            Log.i("DetCtaresenter", "Error en onObtenerMovimientoCuenta" + ex.getMessage());
            getMvpView().hideLoading();
            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_102),
                    new onClicOK(), false);
            getMvpView().onError(R.string.str_error_service_response_code);
        }
    }

    @Override
    public void onOnClicItemListenerCuentas(int position) {
        EnDetalleMovimiento oDetalle = new EnDetalleMovimiento();
        String sCuenta = getDataManager().getStringValue(PreferenceKeys.PREF_CTAS_AHR, null);
        Gson gsonCta = new Gson();
        EnCuentas oEnCuentas = gsonCta.fromJson(sCuenta, EnCuentas.class);
        oDetalle.setnSaldoContable(oEnCuentas.getnSaldCont());
        oDetalle.setnSaldoDisponible(oEnCuentas.getnSaldDisp());
        oDetalle.setiAsientoTrx(oListaDetalleMovimiento.get(position).getiAsiento());
        oDetalle.setsConcepto(oListaDetalleMovimiento.get(position).getsConcepto());
        oDetalle.setsFechaMovimiento(oListaDetalleMovimiento.get(position).getsFechaProceso());
        oDetalle.setsMoneda(oListaDetalleMovimiento.get(position).getsSignoMoneda());
        oDetalle.setnImporteMov(oListaDetalleMovimiento.get(position).getnImporteMov());
        oDetalle.setsHoraTrx(oListaDetalleMovimiento.get(position).getsHoraTrans());
        oDetalle.setsSigno(oListaDetalleMovimiento.get(position).getsSigno());
        oDetalle.setnSaldoAct(oListaDetalleMovimiento.get(position).getnSaldoActual());
        getMvpView().showFragmentDetalleMovimiento(oDetalle);
    }

    @Override
    public void onClicDialogCuenta(String sNumeroCuenta) {
        try {
            String sCuenta = getDataManager().getStringValue(PreferenceKeys.PREF_CTAS_AHR, null);
            ArrayList<ItemOption> arrayListOptions = new ArrayList<>();
            String sName = getMvpView().getMessages(R.string.str_estado_cuenta);
            arrayListOptions.add(new ItemOption(1, sName, R.drawable.ic_loan_cta, sNumeroCuenta));
            Gson gsonCta = new Gson();
            EnCuentas oEnCuentas = gsonCta.fromJson(sCuenta, EnCuentas.class);
            //arrayListOptions.add(new ItemOption(8, "Transferencia", R.drawable.ic_alcancia));
            getMvpView().showDialogFragmentCuentaOpcion(arrayListOptions, oEnCuentas.getsNombreProducto());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getMovimientoCuenta(String sNumeroCuenta, String sUID) {
        try {
            oListaDetalleMovimiento = new ArrayList<>();
            String sSesion = getDataManager().obtenerSesionLogin();
            Gson gson = new Gson();
            EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
            EnParams oParams = new EnParams();
            oParams.setsValor1(sUID);
            oParams.setsValor2(sNumeroCuenta);
            oParams.setsValor3(oEnSesion.getsValor8());
            getCompositeDisposable().add(getDataManager()
                    .obtenerDetalleMovimientoPasivo(oParams)
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
                                    long nNumeroCuenta = 0;
                                    try {
                                        nNumeroCuenta = Long.parseLong(mEncryptionUtils.BM_DecryptValue(sNumeroCuenta));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    int iAsiento = jsonObject.get("iAsiento").getAsInt();
                                    String sConcepto = jsonObject.get("sConcepto").getAsString();
                                    String sHoraTrx = jsonObject.get("sHoraTrx").getAsString();
                                    double nImporteMov = jsonObject.get("nImporteMov").getAsDouble();
                                    double nSaldoActual = jsonObject.get("nSaldoActual").getAsDouble();
                                    String sFecProceso = jsonObject.get("sFecProceso").getAsString();
                                    String sSignoMoneda = jsonObject.get("sSignoMoneda").getAsString();
                                    String sSigno = jsonObject.get("sSigno").getAsString();
                                    int iJtsOid = jsonObject.get("iJtsOid").getAsInt();
                                    EnMovimientoCuenta oMovimiento = new EnMovimientoCuenta(nNumeroCuenta, iAsiento, sConcepto, sHoraTrx, nImporteMov, nSaldoActual, sFecProceso, sSignoMoneda, sSigno, iJtsOid);
                                    oListaDetalleMovimiento.add(oMovimiento);
                                }
                                String sCuenta = getDataManager().getStringValue(PreferenceKeys.PREF_CTAS_AHR, null);
                                Gson gsonCta = new Gson();
                                EnCuentas oEnCuentas = gsonCta.fromJson(sCuenta, EnCuentas.class);
                                getMvpView().onCompletarDetalleMovimiento(oEnCuentas, oListaDetalleMovimiento);
                                getMvpView().hideLoading();
                                mApiServiceHolder.LogoutMobileApp(2);
                            } else {
                                getMvpView().hideLoading();
                                mApiServiceHolder.LogoutMobileApp(1);
                                mAlertDialog = getMvpView().showAlertDialog(enResultService.getsMensaje(), new onClicOK(), false);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            mApiServiceHolder.LogoutMobileApp(1);
                            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_102),
                                    new onClicOK(), false);
                        }
                    })
            );
        } catch (Exception ex) {
            getMvpView().hideLoading();
            mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_102),
                    new onClicOK(), false);
            getMvpView().onError(R.string.str_error_service_response_code);
            Log.i("DetCtaresenter", "Error en onObtenerMovimientoCuenta" + ex.getMessage());
        }
    }

    class onClicOK implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mAlertDialog.dismiss();
            getMvpView().showFragmentProducto();
        }
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }
}
