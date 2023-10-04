package com.credinkamovil.pe.data.remote.producto;

import com.credinkamovil.pe.data.models.EnBase;
import com.credinkamovil.pe.data.models.EnResultService;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiCampanas {
    @POST("campana/obtener-campanas-comercial")
    Observable<EnResultService> ObtenerCampanaComercial(@Body EnBase enBase);

    @POST("campana/confirmar-campana-comercial")
    Observable<EnResultService> ConfirmarCampanaComercial(@Body EnBase enBase);

}
