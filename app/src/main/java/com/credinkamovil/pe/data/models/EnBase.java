package com.credinkamovil.pe.data.models;

import com.google.gson.annotations.SerializedName;

public class EnBase {
    @SerializedName("sId")
    private String sId;
    @SerializedName("sParams")
    private String sParams;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsParams() {
        return sParams;
    }

    public void setsParams(String sParams) {
        this.sParams = sParams;
    }
}
