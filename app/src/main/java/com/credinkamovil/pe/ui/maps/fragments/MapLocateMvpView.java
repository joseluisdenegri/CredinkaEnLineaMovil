package com.credinkamovil.pe.ui.maps.fragments;

import com.credinkamovil.pe.data.models.EnCoordinadas;
import com.credinkamovil.pe.ui.base.MvpView;

import java.util.ArrayList;

public interface MapLocateMvpView extends MvpView {
    void onCompletarDatosCoordinadas(ArrayList<EnCoordinadas> oLista);
}
