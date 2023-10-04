package com.credinkamovil.pe.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = EnTipoDocumento.TABLE_TIPO_DOCUMENTO)
public class EnTipoDocumento {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;
    @SerializedName("iTipoDocumento")
    @ColumnInfo(name = "tipo_documento")
    public int iTipoDocumento;
    @SerializedName("sNombreDocumento")
    @ColumnInfo(name = "nombre_documento")
    public String sNombreDocumento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getiTipoDocumento() {
        return iTipoDocumento;
    }

    public void setiTipoDocumento(int iTipoDocumento) {
        this.iTipoDocumento = iTipoDocumento;
    }

    public String getsNombreDocumento() {
        return sNombreDocumento;
    }

    public void setsNombreDocumento(String sNombreDocumento) {
        this.sNombreDocumento = sNombreDocumento;
    }

    public static final String TABLE_TIPO_DOCUMENTO = "tblTipoDocumento";
}
