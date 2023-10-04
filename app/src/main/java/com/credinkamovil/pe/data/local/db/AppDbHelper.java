package com.credinkamovil.pe.data.local.db;

import com.credinkamovil.pe.data.models.EnTipoDocumento;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

@Singleton
public class AppDbHelper implements DbHelper {
    private AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<Long> registrarTipoDocumento(EnTipoDocumento oEnTipoDocumento) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mAppDatabase.tipoDocumentoDao().insertarTipoDocumento(oEnTipoDocumento);
            }
        });
    }

    @Override
    public Observable<List<EnTipoDocumento>> listarTipoDocumento() {
        return Observable.fromCallable(new Callable<List<EnTipoDocumento>>() {
            @Override
            public List<EnTipoDocumento> call() throws Exception {
                return mAppDatabase.tipoDocumentoDao().listarTipoDocumento();
                //return Observable.fromCallable(() -> mAppDatabase.tipoDocumentoDao().listarTipoDocumento());
            }
        });
    }

    @Override
    public Completable limpiarTipoDocumento() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mAppDatabase.tipoDocumentoDao().eliminaTipoDocumento();
            }
        });
    }
}
