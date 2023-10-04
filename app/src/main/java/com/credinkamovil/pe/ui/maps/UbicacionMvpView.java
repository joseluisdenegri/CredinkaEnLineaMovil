package com.credinkamovil.pe.ui.maps;

import com.credinkamovil.pe.data.models.EnCoordinadas;
import com.credinkamovil.pe.ui.base.MvpView;

import java.util.ArrayList;

public interface UbicacionMvpView extends MvpView {
    void onCompletarDatosCoordinadas(ArrayList<EnCoordinadas> oLista);
}
