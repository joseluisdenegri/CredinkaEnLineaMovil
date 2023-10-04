package com.credinkamovil.pe.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.credinkamovil.pe.data.models.EnTipoDocumento;

import java.util.List;

@Dao
public interface TipoDocumentoDao {
    @Insert/*(onConflict = OnConflictStrategy.REPLACE)*/
    Long insertarTipoDocumento(EnTipoDocumento enTipoDocumento);

    @Query("SELECT * FROM " + EnTipoDocumento.TABLE_TIPO_DOCUMENTO)
    List<EnTipoDocumento> listarTipoDocumento();

    @Query("DELETE FROM " + EnTipoDocumento.TABLE_TIPO_DOCUMENTO)
    void eliminaTipoDocumento();
}
