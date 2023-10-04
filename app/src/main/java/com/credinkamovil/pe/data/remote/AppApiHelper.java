package com.credinkamovil.pe.data.remote;

import com.credinkamovil.pe.data.models.EnBase;
import com.credinkamovil.pe.data.models.EnBitacora;
import com.credinkamovil.pe.data.models.EnParams;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.data.models.EnTokenResponse;
import com.credinkamovil.pe.data.models.EnUsuario;
import com.credinkamovil.pe.data.remote.ingreso.ApiIngreso;
import com.credinkamovil.pe.data.remote.producto.ApiCampanas;
import com.credinkamovil.pe.data.remote.producto.ApiCuentas;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

@Singleton
public class AppApiHelper implements ApiHelper {
    private final ApiIngreso mApiIngreso;
    private final ApiCuentas mApiCuentas;
    private final ApiCampanas mApiCampanas;

    @Inject
    AppApiHelper(ApiIngreso apiIngreso, ApiCuentas apiCuentas, ApiCampanas apiCampanas) {
        this.mApiIngreso = apiIngreso;
        this.mApiCuentas = apiCuentas;
        this.mApiCampanas = apiCampanas;
    }

    @Override
    public Observable<EnResultService> pingService() {
        return mApiIngreso.PingService();
    }

    @Override
    public Observable<EnResultService> logoutMobileBank(EnSesion sesion) {
        return mApiIngreso.LogoutMobileBank(sesion);
    }

    @Override
    public Observable<EnResultService> ingreseoBancaMovil(EnUsuario enUsuario) {
        return mApiIngreso.IngresoBancaMovil(enUsuario);
    }

    @Override
    public Observable<EnResultService> obtenerListaContactos(String sId) {
        return mApiIngreso.ObtenerListaContactos(sId);
    }

    @Override
    public Observable<EnResultService> obtenerListaCoordinadas(String sId) {
        return mApiIngreso.ObtenerListaCoordinadas(sId);
    }

    @Override
    public Observable<EnResultService> registrarBitacora(EnBitacora enBitacora) {
        return mApiIngreso.RegistrarBitacora(enBitacora);
    }

    @Override
    public Observable<EnResultService> validateTokenExpired(EnTokenResponse tokenResponse) {
        return mApiIngreso.ValidateTokenExpired(tokenResponse);
    }

    @Override
    public Observable<EnResultService> obtenerTipoCambio(String sId) {
        return mApiIngreso.ObtenerTipoCambio(sId);
    }

    @Override
    public Observable<EnResultService> obtenerListaPeriodo(String sId) {
        return mApiIngreso.ObtenerListaPeriodo(sId);
    }

    @Override
    public Observable<EnResultService> obtenerCuentasPasivo(EnBase enBase) {
        return mApiCuentas.ObtenerCuentasPasivo(enBase);
    }

    @Override
    public Observable<EnResultService> obtenerCuentasActivo(EnBase enBase) {
        return mApiCuentas.ObtenerCuentasActivos(enBase);
    }

    @Override
    public Observable<EnResultService> obtenerCampanaComercial(EnBase enBase) {
        return mApiCampanas.ObtenerCampanaComercial(enBase);
    }

    @Override
    public Observable<EnResultService> confirmarCampanaComercial(EnBase enBase) {
        return mApiCampanas.ConfirmarCampanaComercial(enBase);
    }

    @Override
    public Observable<EnResultService> obtenerDetalleMovimientoPasivo(EnParams enParams) {
        return mApiCuentas.ObtenerDetalleMovimientoPasivo(enParams);
    }

    @Override
    public Observable<EnResultService> obtenerDetalleMovimientoActivo(EnParams enParams) {
        return mApiCuentas.ObtenerDetalleMovimientoActivo(enParams);
    }

    @Override
    public Observable<ResponseBody> downloadCronogramaCred(EnParams enParams) {
        return mApiCuentas.DownloadCronogramaCred(enParams);
    }

    @Override
    public Observable<ResponseBody> downloadEstadoCuentaPasivo(EnParams enParams) {
        return mApiCuentas.DownloadEstadoCuentaPasivo(enParams);
    }


}
