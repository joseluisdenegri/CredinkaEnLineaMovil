package com.credinkamovil.pe.ui.service;

import android.util.Log;

import com.credinkamovil.pe.BuildConfig;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnBitacora;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.utils.AppUtils;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class APIServiceHolder {
    private static final String TAG = APIServiceHolder.class.getCanonicalName();
    private static CompositeDisposable mCompositeDisposable;
    private static SchedulerProvider mSchedulerProvider;
    private static DataManager mDataManager;

    public APIServiceHolder(CompositeDisposable compositeDisposable, SchedulerProvider schedulerProvider, DataManager dataManager) {
        mCompositeDisposable = compositeDisposable;
        mSchedulerProvider = schedulerProvider;
        mDataManager = dataManager;
    }

    public void LogoutMobileAppExecption(int iFiltro, long psValor) {
        try {
            int iTimerSesion = Integer.parseInt(mDataManager.getStringValue(PreferenceKeys.PREF_VALUE_TIMEOUT, null));
            String sUId = mDataManager.getStringValue(PreferenceKeys.PREF_UISID, null);
            EnSesion sesion = new EnSesion();
            sesion.setsId(sUId);
            sesion.setnValor0(psValor);
            sesion.setiValorNumber(iFiltro);
            sesion.setiTimerSesion(iTimerSesion);
            mCompositeDisposable.add(mDataManager.logoutMobileBank(sesion)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(resultService -> Log.i("LogoutAppMobile", "===**********Codigo:" +
                            String.valueOf(resultService.getiResultado()) + " ********** Mensaje: " + resultService.getsMensaje()), new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i("RespBitacora", " ********** Mensaje: " + throwable.getMessage());
                        }
                    }));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void LogoutMobileApp(int iFiltro) {
        try {
            String sSesion = mDataManager.obtenerSesionLogin();
            String sUId = mDataManager.getStringValue(PreferenceKeys.PREF_UISID, null);
            Gson gson = new Gson();
            EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
            int iTimerSesion = Integer.parseInt(mDataManager.getStringValue(PreferenceKeys.PREF_VALUE_TIMEOUT, null));
            EnSesion sesion = new EnSesion();
            sesion.setsId(sUId);
            sesion.setnValor0(oEnSesion.getnValor0());
            sesion.setiValorNumber(iFiltro);
            sesion.setiTimerSesion(iTimerSesion);
            mCompositeDisposable.add(mDataManager.logoutMobileBank(sesion)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(resultService -> Log.i("LogoutAppMobile", "===**********Codigo:" +
                            String.valueOf(resultService.getiResultado()) + " ********** Mensaje: " + resultService.getsMensaje()), new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i("RespBitacora", " ********** Mensaje: " + throwable.getMessage());
                        }
                    }));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void RegistrarBitacora(String sDescripcion, String sOperacion) {
        try {
            String sSesion = mDataManager.obtenerSesionLogin();
            Gson gson = new Gson();
            EnSesion oEnSesion = gson.fromJson(sSesion, EnSesion.class);
            String sNumberCard = mDataManager.getStringValue(PreferenceKeys.PREF_VALUE_CARD, null);
            String sUId = mDataManager.getStringValue(PreferenceKeys.PREF_UISID, null);
            EnBitacora bitacora = new EnBitacora();
            bitacora.setsValor1(oEnSesion.getsValor8());
            bitacora.setsCrdNum(sNumberCard);
            bitacora.setsFechaAcceso(AppUtils.getFechaHora());
            bitacora.setsDescripcion(sDescripcion + "|" + BuildConfig.VERSION_NAME);
            bitacora.setsOperacion(sOperacion);
            bitacora.setsHora(AppUtils.getHora());
            bitacora.setsAudMACCreacion(AppUtils.getMacLocal());
            bitacora.setsAudIPCreacion(AppUtils.getIpLocalAddress());
            bitacora.setsId(sUId);

            mCompositeDisposable.add(mDataManager.registrarBitacora(bitacora)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(resultService -> Log.i("RespBitacora", "===**********Codigo:" +
                            String.valueOf(resultService.getiResultado()) + " ********** Mensaje: " + resultService.getsMensaje()), new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i("RespBitacora", " ********** Mensaje: " + throwable.getMessage());
                        }
                    }));
        } catch (Exception ex) {
            Log.i(TAG, "==========================ERROR SERVICE API =========================" + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }
}
