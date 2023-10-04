package com.credinkamovil.pe.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnContacto;
import com.credinkamovil.pe.data.models.EnCoordinadas;
import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnPeriodo;
import com.credinkamovil.pe.data.models.EnPrestamo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class NetworkUtils {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean validateEnableGPS(Context context) {
        int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
        final boolean enabled = (mode != Settings.Secure.LOCATION_MODE_OFF);
        return enabled;
    }

    public static ArrayList<EnContacto> httpParsearContacto(JsonElement jsonElement, int iTipo) {
        ArrayList<EnContacto> oListaContacto = new ArrayList<>();
        try {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int coffe = 0; coffe < jsonArray.size(); coffe++) {
                JsonObject jsonObject = jsonArray.get(coffe).getAsJsonObject();
                int iContacto = jsonObject.get("iContacto").getAsInt();
                if (iContacto == iTipo) {
                    int iTipoContacto = jsonObject.get("iTipoContacto").getAsInt();
                    String sDescripcion = jsonObject.get("sDescripcion").getAsString();
                    String sNumeroTelefono = jsonObject.get("sNumeroTelefono").getAsString();
                    int iOrder = jsonObject.get("iOrder").getAsInt();

                    EnContacto oEnContacto = new EnContacto(iContacto, iTipoContacto, sDescripcion, sNumeroTelefono, iOrder);
                    oListaContacto.add(oEnContacto);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return oListaContacto;
    }

    public static ArrayList<EnCoordinadas> parsearListaCoordinadas(JsonElement jsonElement) {
        ArrayList<EnCoordinadas> oListaCoordinadas = new ArrayList<>();
        try {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int coffe = 0; coffe < jsonArray.size(); coffe++) {
                JsonObject jsonObject = jsonArray.get(coffe).getAsJsonObject();
                String sNombreAgencia = jsonObject.get("sNombreAgencia").getAsString();
                String sDireccion = jsonObject.get("sDireccion").getAsString();
                String sLatitud = jsonObject.get("sLatitud").getAsString();
                String sLongitud = jsonObject.get("sLongitud").getAsString();
                double nLatitud = Double.parseDouble(sLatitud);
                double nLongitud = Double.parseDouble(sLongitud);
                EnCoordinadas enCoordinadas = new EnCoordinadas(sNombreAgencia, sDireccion, nLatitud, nLongitud);
                oListaCoordinadas.add(enCoordinadas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return oListaCoordinadas;
        }
        return oListaCoordinadas;
    }

    public static void observableListCuenta(ArrayList<EnCuentas> listaCuenta, long nNroCta, DataManager dataManager) {
        try {
            EnCuentas oCuenta = new EnCuentas();
            Gson gson = new Gson();
            Observable.fromIterable(listaCuenta)
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .filter(cta -> cta.getnNumeroCuenta() == nNroCta)
                    .toList()
                    .subscribe(new SingleObserver<List<EnCuentas>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d("onSubscribe", "Disposable");
                        }

                        @Override
                        public void onSuccess(List<EnCuentas> enCuentas) {
                            oCuenta.setnNumeroCuenta(enCuentas.get(0).getnNumeroCuenta());
                            oCuenta.setsNombreProducto(enCuentas.get(0).getsNombreProducto());
                            oCuenta.setsFamilia(enCuentas.get(0).getsFamilia());
                            oCuenta.setnSaldCont(enCuentas.get(0).getnSaldCont());
                            oCuenta.setnSaldDisp(enCuentas.get(0).getnSaldDisp());
                            oCuenta.setsSignoMoneda(enCuentas.get(0).getsSignoMoneda());
                            oCuenta.setiFam(enCuentas.get(0).getiFam());
                            String jsonCuenta = gson.toJson(oCuenta);
                            dataManager.setValue(PreferenceKeys.PREF_CTAS_AHR, jsonCuenta);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("onError", "*********ERROR*********" + e.getMessage());
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void observableListCredito(ArrayList<EnPrestamo> oListaMisCreditos, long nNroCred, DataManager dataManager) {
        try {
            EnPrestamo oPrestamo = new EnPrestamo();
            Gson gson = new Gson();
            Observable.fromIterable(oListaMisCreditos)
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .filter(cta -> cta.getnNumeroCredito() == nNroCred)
                    .toList()
                    .subscribe(new SingleObserver<List<EnPrestamo>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d("onSubscribe", "Disposable");
                        }

                        @Override
                        public void onSuccess(List<EnPrestamo> enPrestamos) {
                            oPrestamo.setnNumeroCredito(enPrestamos.get(0).getnNumeroCredito());
                            oPrestamo.setsNombreProducto(enPrestamos.get(0).getsNombreProducto());
                            oPrestamo.setiTotalCuotas(enPrestamos.get(0).getiTotalCuotas());
                            oPrestamo.setnSaldoCapital(enPrestamos.get(0).getnSaldoCapital());
                            oPrestamo.setnMontoPrestamo(enPrestamos.get(0).getnMontoPrestamo());
                            oPrestamo.setnTasaInters(enPrestamos.get(0).getnTasaInters());
                            oPrestamo.setsSignoMoneda(enPrestamos.get(0).getsSignoMoneda());
                            oPrestamo.setiNroDiasAtrasado(enPrestamos.get(0).getiNroDiasAtrasado());
                            oPrestamo.setiCuotasPagadas(enPrestamos.get(0).getiCuotasPagadas());
                            String jsonCuenta = gson.toJson(oPrestamo);
                            dataManager.setValue(PreferenceKeys.PREF_CTAS_CRE, jsonCuenta);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("onError", "*********ERROR*********" + e.getMessage());
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<EnPeriodo> httpParsearPeriodo(JsonElement jsonElement) {
        ArrayList<EnPeriodo> oListaPeriodo = new ArrayList<>();
        try {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int coffe = 0; coffe < jsonArray.size(); coffe++) {
                JsonObject jsonObject = jsonArray.get(coffe).getAsJsonObject();
                int iCodigo = jsonObject.get("iCodigo").getAsInt();
                int iAnio = jsonObject.get("iAnio").getAsInt();
                String sMes = jsonObject.get("sMes").getAsString();
                String sFechaInicio = jsonObject.get("sFechaInicio").getAsString();
                String sFechaFin = jsonObject.get("sFechaFin").getAsString();
                EnPeriodo oEnPeriodo = new EnPeriodo(iCodigo, iAnio, sMes, sFechaInicio, sFechaFin);
                oListaPeriodo.add(oEnPeriodo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return oListaPeriodo;
    }
}
