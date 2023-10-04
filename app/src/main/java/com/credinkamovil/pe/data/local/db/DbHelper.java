package com.credinkamovil.pe.data.local.db;

import com.credinkamovil.pe.data.models.EnTipoDocumento;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DbHelper {
    Observable<Long> registrarTipoDocumento(EnTipoDocumento oEnTipoDocumento);

    Observable<List<EnTipoDocumento>> listarTipoDocumento();

    Completable limpiarTipoDocumento();
}
