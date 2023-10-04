package com.credinkamovil.pe.data;

import android.content.Context;

import com.credinkamovil.pe.data.local.db.DbHelper;
import com.credinkamovil.pe.data.local.prefs.PreferenceHelper;
import com.credinkamovil.pe.data.models.EnBase;
import com.credinkamovil.pe.data.models.EnBitacora;
import com.credinkamovil.pe.data.models.EnParams;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.data.models.EnTipoDocumento;
import com.credinkamovil.pe.data.models.EnTokenResponse;
import com.credinkamovil.pe.data.models.EnUsuario;
import com.credinkamovil.pe.data.remote.ApiHelper;
import com.credinkamovil.pe.di.scope.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

@Singleton
public class AppDataManager implements DataManager {
    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferenceHelper mPreferenceHelper;
    private final ApiHelper mApiHelper;

    @Inject
    AppDataManager(@ApplicationContext Context context, DbHelper dbHelper, PreferenceHelper preferenceHelper,
                   ApiHelper apiHelper) {
        this.mContext = context;
        this.mDbHelper = dbHelper;
        this.mPreferenceHelper = preferenceHelper;
        this.mApiHelper = apiHelper;
    }

    /*===================INICIO :: DB TIPO DOCUMENTO=========================*/
    @Override
    public Observable<Long> registrarTipoDocumento(EnTipoDocumento oEnTipoDocumento) {
        return mDbHelper.registrarTipoDocumento(oEnTipoDocumento);
    }

    @Override
    public Observable<List<EnTipoDocumento>> listarTipoDocumento() {
        return mDbHelper.listarTipoDocumento();
    }

    @Override
    public Completable limpiarTipoDocumento() {
        return mDbHelper.limpiarTipoDocumento();
    }
    /*===================FIN :: DB TIPO DOCUMENTO=========================*/


    /*=======================================================================================*/
    /*-----------------------------------SHARED PREFERENCES----------------------------------*/

    @Override
    public void setValue(String key, String value) {
        mPreferenceHelper.setValue(key, value);
    }

    @Override
    public String getStringValue(String key, String defaultValue) {
        return mPreferenceHelper.getStringValue(key, defaultValue);
    }

    @Override
    public void removeKey(String key) {
        mPreferenceHelper.removeKey(key);
    }

    @Override
    public void clear() {
        mPreferenceHelper.clear();
    }

    @Override
    public String getObtenerToken() {
        return mPreferenceHelper.getObtenerToken();
    }

    @Override
    public void setRegistrarToken(String sValor) {
        mPreferenceHelper.setRegistrarToken(sValor);
    }

    @Override
    public void registrarSesionLogin(String sValor) {
        mPreferenceHelper.registrarSesionLogin(sValor);
    }

    @Override
    public String obtenerSesionLogin() {
        return mPreferenceHelper.obtenerSesionLogin();
    }


    /*==================================API CALL===========================*/
    @Override
    public Observable<EnResultService> pingService() {
        return mApiHelper.pingService();
    }

    @Override
    public Observable<EnResultService> logoutMobileBank(EnSesion sesion) {
        return mApiHelper.logoutMobileBank(sesion);
    }

    @Override
    public Observable<EnResultService> ingreseoBancaMovil(EnUsuario enUsuario) {
        return mApiHelper.ingreseoBancaMovil(enUsuario);
    }

    @Override
    public Observable<EnResultService> obtenerListaContactos(String sId) {
        return mApiHelper.obtenerListaContactos(sId);
    }

    @Override
    public Observable<EnResultService> obtenerListaCoordinadas(String sId) {
        return mApiHelper.obtenerListaCoordinadas(sId);
    }

    @Override
    public Observable<EnResultService> registrarBitacora(EnBitacora enBitacora) {
        return mApiHelper.registrarBitacora(enBitacora);
    }

    @Override
    public Observable<EnResultService> validateTokenExpired(EnTokenResponse tokenResponse) {
        return mApiHelper.validateTokenExpired(tokenResponse);
    }

    @Override
    public Observable<EnResultService> obtenerTipoCambio(String sId) {
        return mApiHelper.obtenerTipoCambio(sId);
    }

    @Override
    public Observable<EnResultService> obtenerListaPeriodo(String sId) {
        return mApiHelper.obtenerListaPeriodo(sId);
    }

    /*==================================API CALL===========================*/
    @Override
    public Observable<EnResultService> obtenerCuentasPasivo(EnBase enBase) {
        return mApiHelper.obtenerCuentasPasivo(enBase);
    }

    @Override
    public Observable<EnResultService> obtenerCuentasActivo(EnBase enBase) {
        return mApiHelper.obtenerCuentasActivo(enBase);
    }

    @Override
    public Observable<EnResultService> obtenerCampanaComercial(EnBase enBase) {
        return mApiHelper.obtenerCampanaComercial(enBase);
    }

    @Override
    public Observable<EnResultService> confirmarCampanaComercial(EnBase enBase) {
        return mApiHelper.confirmarCampanaComercial(enBase);
    }

    @Override
    public Observable<EnResultService> obtenerDetalleMovimientoPasivo(EnParams enParams) {
        return mApiHelper.obtenerDetalleMovimientoPasivo(enParams);
    }

    @Override
    public Observable<EnResultService> obtenerDetalleMovimientoActivo(EnParams enParams) {
        return mApiHelper.obtenerDetalleMovimientoActivo(enParams);
    }

    @Override
    public Observable<ResponseBody> downloadCronogramaCred(EnParams enParams) {
        return mApiHelper.downloadCronogramaCred(enParams);
    }

    @Override
    public Observable<ResponseBody> downloadEstadoCuentaPasivo(EnParams enParams) {
        return mApiHelper.downloadEstadoCuentaPasivo(enParams);
    }


}
