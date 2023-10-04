package com.credinkamovil.pe.data.remote;

import com.credinkamovil.pe.data.models.EnBase;
import com.credinkamovil.pe.data.models.EnBitacora;
import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnParams;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.data.models.EnTokenResponse;
import com.credinkamovil.pe.data.models.EnUsuario;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface ApiHelper {
    Observable<EnResultService> pingService();

    Observable<EnResultService> logoutMobileBank(EnSesion sesion);

    Observable<EnResultService> ingreseoBancaMovil(EnUsuario enUsuario);

    Observable<EnResultService> obtenerListaContactos(String sId);

    Observable<EnResultService> obtenerListaCoordinadas(String sId);

    Observable<EnResultService> registrarBitacora(EnBitacora enBitacora);

    Observable<EnResultService> validateTokenExpired(EnTokenResponse tokenResponse);

    Observable<EnResultService> obtenerTipoCambio(String sId);

    Observable<EnResultService> obtenerListaPeriodo(String sId);

    /*==============API PRODUCTOS================*/
    Observable<EnResultService> obtenerCuentasPasivo(EnBase enBase);

    Observable<EnResultService> obtenerCuentasActivo(EnBase enBase);

    Observable<EnResultService> obtenerCampanaComercial(EnBase enBase);

    Observable<EnResultService> confirmarCampanaComercial(EnBase enBase);

    Observable<EnResultService> obtenerDetalleMovimientoPasivo(EnParams enParams);

    Observable<EnResultService> obtenerDetalleMovimientoActivo(EnParams enParams);

    Observable<ResponseBody> downloadCronogramaCred(EnParams enParams);

    Observable<ResponseBody> downloadEstadoCuentaPasivo(EnParams enParams);
}
