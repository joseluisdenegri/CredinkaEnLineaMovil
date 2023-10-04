package com.credinkamovil.pe.ui.ofertas;

import com.credinkamovil.pe.data.models.EnOfertaComercial;
import com.credinkamovil.pe.ui.base.MvpView;

public interface OfertasMvpView extends MvpView {
    void onMostrarOfertaCreditos(EnOfertaComercial enOfertaComercial);
}
