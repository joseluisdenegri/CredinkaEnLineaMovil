package com.credinkamovil.pe.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.credinkamovil.pe.data.local.db.dao.TipoDocumentoDao;
import com.credinkamovil.pe.data.models.EnTipoDocumento;

import javax.inject.Singleton;

@Database(entities = {EnTipoDocumento.class}, version = AppDatabase.VERSION_BD, exportSchema = false)
@Singleton
public abstract class AppDatabase extends RoomDatabase {
    static final int VERSION_BD = 1;

    public abstract TipoDocumentoDao tipoDocumentoDao();
}
