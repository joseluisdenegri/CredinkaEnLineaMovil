package com.credinkamovil.pe.ui.detalleproducto.creditos;

import com.credinkamovil.pe.data.models.EnCuotasPendientesCredito;
import com.credinkamovil.pe.data.models.EnPrestamo;
import com.credinkamovil.pe.ui.base.MvpView;

import java.io.InputStream;
import java.util.ArrayList;

public interface CreditosMvpView extends MvpView {
    void onCompletarDetalleCredito(EnPrestamo enPrestamo, ArrayList<EnCuotasPendientesCredito> olista);

    void onSinInformacion();

    void onDownloadCronograma(InputStream inputStream);
}
