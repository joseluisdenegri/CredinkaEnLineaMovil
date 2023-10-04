package com.credinkamovil.pe.data.local.prefs;

public interface PreferenceHelper {
    void setValue(String key, String value);

    String getStringValue(String key, String defaultValue);

    void removeKey(String key);

    void clear();

    String getObtenerToken();

    void setRegistrarToken(String sValor);

    void registrarSesionLogin(String sValor);

    String obtenerSesionLogin();
}
