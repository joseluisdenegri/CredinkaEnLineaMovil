package com.credinkamovil.pe.data.remote.producto;

import com.credinkamovil.pe.data.models.EnBase;
import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnOfertaComercial;
import com.credinkamovil.pe.data.models.EnParams;
import com.credinkamovil.pe.data.models.EnResultService;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiCuentas {
    @POST("cuentas/obtener-cuentas-pasivas")
    Observable<EnResultService> ObtenerCuentasPasivo(@Body EnBase enBase);

    @POST("cuentas/obtener-cuentas-activas")
    Observable<EnResultService> ObtenerCuentasActivos(@Body EnBase enBase);

    @POST("cuentas/obtener-detalle-cuenta-pasivo")
    Observable<EnResultService> ObtenerDetalleMovimientoPasivo(@Body EnParams enParams);

    @POST("cuentas/obtener-detalle-creditos-activos")
    Observable<EnResultService> ObtenerDetalleMovimientoActivo(@Body EnParams enParams);

    @POST("cuentas/obtener-cronograma")
    Observable<ResponseBody> DownloadCronogramaCred(@Body EnParams enParams);

    @POST("cuentas/obtener-estado-cuenta")
    Observable<ResponseBody> DownloadEstadoCuentaPasivo(@Body EnParams enParams);
}
