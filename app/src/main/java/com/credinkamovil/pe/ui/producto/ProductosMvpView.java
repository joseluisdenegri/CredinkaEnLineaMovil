package com.credinkamovil.pe.ui.producto;

import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnPrestamo;
import com.credinkamovil.pe.ui.base.MvpView;

import java.util.ArrayList;

public interface ProductosMvpView extends MvpView {
    void onCompletarDatosLista(ArrayList<EnCuentas> listCuentas, ArrayList<EnPrestamo> listaCreditos);

    void showFragmentDetalleMisCuentas(String sNumeroCuenta);

    void showFragmentDetalleMisCreditos(String sNumeroCredito);

    void intentLoginActivity();

    void onConfirmarCampanaComercial();
}
