package com.credinkamovil.pe.data.remote.ingreso;

import com.credinkamovil.pe.data.models.EnBitacora;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.data.models.EnTokenResponse;
import com.credinkamovil.pe.data.models.EnUsuario;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiIngreso {
    @GET("account/ping")
    Observable<EnResultService> PingService();

    @POST("account/logout-mobile-bank")
    Observable<EnResultService> LogoutMobileBank(@Body EnSesion sesion);

    @POST("ingreso/ingreso-banca-movil")
    Observable<EnResultService> IngresoBancaMovil(@Body EnUsuario usuario);

    @GET("ingreso/listar-contactos/{sId}")
    Observable<EnResultService> ObtenerListaContactos(@Path("sId") String sId);

    @GET("ingreso/listar-coordinadas-agencia/{sId}")
    Observable<EnResultService> ObtenerListaCoordinadas(@Path("sId") String sId);

    @POST("ingreso/registrar-bitacora")
    Observable<EnResultService> RegistrarBitacora(@Body EnBitacora bitacora);

    @POST("account/validate-token-expired")
    Observable<EnResultService> ValidateTokenExpired(@Body EnTokenResponse tokenResponse);

    @GET("ingreso/obtener-tipo-cambio/{sId}")
    Observable<EnResultService> ObtenerTipoCambio(@Path("sId") String sId);

    @GET("ingreso/listar-periodos/{sId}")
    Observable<EnResultService> ObtenerListaPeriodo(@Path("sId") String sId);
}
