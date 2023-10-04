package com.credinkamovil.pe.data.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ItemOption implements Parcelable {
    private int iIdOption;
    private String sNombreOpcion;
    private int iImageDrawable;
    private String sNumeroCuenta;

    public ItemOption(int iId, String sName, int iImage, String sNroCuenta) {
        this.iIdOption = iId;
        this.sNombreOpcion = sName;
        this.iImageDrawable = iImage;
        this.sNumeroCuenta = sNroCuenta;
    }

    protected ItemOption(Parcel in) {
        iIdOption = in.readInt();
        sNombreOpcion = in.readString();
        iImageDrawable = in.readInt();
        sNumeroCuenta = in.readString();
    }

    public static final Creator<ItemOption> CREATOR = new Creator<ItemOption>() {
        @Override
        public ItemOption createFromParcel(Parcel in) {
            return new ItemOption(in);
        }

        @Override
        public ItemOption[] newArray(int size) {
            return new ItemOption[size];
        }
    };

    public int getiIdOption() {
        return iIdOption;
    }

    public void setiIdOption(int iIdOption) {
        this.iIdOption = iIdOption;
    }

    public String getsNombreOpcion() {
        return sNombreOpcion;
    }

    public void setsNombreOpcion(String sNombreOpcion) {
        this.sNombreOpcion = sNombreOpcion;
    }

    public int getiImageDrawable() {
        return iImageDrawable;
    }

    public void setiImageDrawable(int iImageDrawable) {
        this.iImageDrawable = iImageDrawable;
    }

    public String getsNumeroCuenta() {
        return sNumeroCuenta;
    }

    public void setsNumeroCuenta(String sNumeroCuenta) {
        this.sNumeroCuenta = sNumeroCuenta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(iIdOption);
    }
}
