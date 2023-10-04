package com.credinkamovil.pe.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.credinkamovil.pe.di.scope.ApplicationContext;
import com.credinkamovil.pe.di.scope.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferenceHelper implements PreferenceHelper {
    public SharedPreferences mSharedPreferences;

    @Inject
    public AppPreferenceHelper(@ApplicationContext Context context, @PreferenceInfo String prefName) {
        mSharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    @Override
    public void setValue(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public String getStringValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void removeKey(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    @Override
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    @Override
    public String getObtenerToken() {
        return mSharedPreferences.getString(PreferenceKeys.PREF_KEY_TOKEN, null);
    }

    @Override
    public void setRegistrarToken(String sValor) {
        mSharedPreferences.edit().putString(PreferenceKeys.PREF_KEY_TOKEN, sValor).apply();
    }

    @Override
    public void registrarSesionLogin(String sValor) {
        mSharedPreferences.edit().putString(PreferenceKeys.PREF_SESION, sValor).apply();
    }

    @Override
    public String obtenerSesionLogin() {
        return mSharedPreferences.getString(PreferenceKeys.PREF_SESION, null);
    }
}
